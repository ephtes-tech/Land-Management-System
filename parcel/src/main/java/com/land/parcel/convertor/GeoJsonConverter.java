package com.land.parcel.convertor;

import com.land.parcel.exception.InvalidGeometryException;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.springframework.stereotype.Component;

@Component
public class GeoJsonConverter {
    private final GeometryFactory geometryFactory =
            new GeometryFactory(new PrecisionModel(), 4326);

    public MultiPolygon toPolygon(String geoJson) {
        try {
            GeoJsonReader reader = new GeoJsonReader(geometryFactory);
            Geometry geometry = reader.read(geoJson);

            if (geometry == null) {
                throw new IllegalArgumentException("Geometry is empty");
            }

            geometry=force2D(geometry);
            geometry.setSRID(4326);

            // Polygon
            if (geometry instanceof Polygon polygon) {
                polygon.setSRID(4326);
                validate(polygon);
                return geometryFactory.createMultiPolygon(new Polygon[]{polygon});
            }


            if (geometry instanceof MultiPolygon multiPolygon) {
                multiPolygon.setSRID(4326);
                validate(multiPolygon);
                return multiPolygon;
            }

            throw new IllegalArgumentException(
                    "Unsupported geometry type: " + geometry.getGeometryType()
            );

        } catch (Exception e) {
            throw new RuntimeException("Invalid GeoJSON -> Polygon conversion", e);
        }
    }
    private Geometry force2D(Geometry geometry) {
        Geometry copy = (Geometry) geometry.copy();
        copy.apply((CoordinateFilter) coord -> coord.setZ(Double.NaN));
        return copy;
    }

    private void validate(Geometry geometry) {
        if (!geometry.isValid()) {
            throw new InvalidGeometryException("Invalid polygon (self-intersection)");
        }
        if (geometry.getArea() <= 0) {
            throw new InvalidGeometryException("Polygon area must be greater than zero");
        }
    }
}

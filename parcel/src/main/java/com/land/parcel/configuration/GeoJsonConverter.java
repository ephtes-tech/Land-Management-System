package com.land.parcel.configuration;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.springframework.stereotype.Component;

@Component
public class GeoJsonConverter {
    private final GeometryFactory geometryFactory =
            new GeometryFactory(new PrecisionModel(), 4326);

    public Polygon toPolygon(String geoJson) {
        try {
            GeoJsonReader reader = new GeoJsonReader(geometryFactory);
            Geometry geometry = reader.read(geoJson);

            if (geometry == null) {
                throw new IllegalArgumentException("Geometry is empty");
            }

            // Polygon
            if (geometry instanceof Polygon polygon) {
                polygon.setSRID(4326);
                validate(polygon);
                return polygon;
            }

            // MultiPolygon → union
            if (geometry instanceof MultiPolygon multiPolygon) {
                Geometry union = multiPolygon.union();
                if (!(union instanceof Polygon)) {
                    throw new IllegalArgumentException("MultiPolygon cannot be merged into a Polygon");
                }
                Polygon polygon = (Polygon) union;
                polygon.setSRID(4326);
                validate(polygon);
                return polygon;
            }

            throw new IllegalArgumentException(
                    "Unsupported geometry type: " + geometry.getGeometryType()
            );

        } catch (Exception e) {
            throw new RuntimeException("Invalid GeoJSON -> Polygon conversion", e);
        }
    }

    private void validate(Polygon polygon) {
        if (!polygon.isValid()) {
            throw new IllegalArgumentException("Invalid polygon (self-intersection)");
        }
        if (polygon.getArea() <= 0) {
            throw new IllegalArgumentException("Polygon area must be greater than zero");
        }
    }
}

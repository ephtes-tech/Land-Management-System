package com.land.parcel.convertor;

import com.land.parcel.exception.InvalidGeometryException;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.springframework.stereotype.Component;


/**
 * Converts GeoJson input into a valid MultiPolygon (SRID 4326).
 * Accepts Polygon or MultiPolygon and guarantees 2D geometry
 */
@Slf4j
@Component
public class GeoJsonConverter {
    /**
     * GeometryFactory defines precision and spatial reference system.
     * SRID 4326 = WGS84 (latitude / longitude)
     */
    private final GeometryFactory geometryFactory =
            new GeometryFactory(new PrecisionModel(), 4326);


    //Converts GeoJSON string into a Multipolygon

    public MultiPolygon toPolygon(String geoJson) {
        log.debug("Starting GeoJSON to MultiPolygon conversion");
        try {
            //Parse GeoJSON using predefined geometry rules
            GeoJsonReader reader = new GeoJsonReader(geometryFactory);
            Geometry geometry = reader.read(geoJson);

            //Reject Empty geometry
            if (geometry == null) {
                log.warn("GeoJson parsing resulted in null geometry");
                throw new InvalidGeometryException("Geometry is empty");
            }

            //Force geometry to 2D (remove Z values)
            geometry=force2D(geometry);
            geometry.setSRID(4326);

            // Polygon -> wrap into MultiPolygon
            if (geometry instanceof Polygon polygon) {
                log.debug("GeoJSON contains Polygon, wrapping into MultiPolygon");
                polygon.setSRID(4326);
                validate(polygon);
                return geometryFactory.createMultiPolygon(new Polygon[]{
                        polygon});
            }


            //MultiPolygon -> return as-is
            if (geometry instanceof MultiPolygon multiPolygon) {
                log.debug("GeoJSON contains MultiPolygon");
                multiPolygon.setSRID(4326);
                validate(multiPolygon);
                return multiPolygon;
            }
            //Unsupported geometry types
            log.error("Unsupported geometry type received: {}",geometry.getGeometryType());

            throw new InvalidGeometryException(
                    "Unsupported geometry type: " + geometry.getGeometryType()
            );

        }catch (InvalidGeometryException e){
            //Business-level exception -> bubble up
            log.warn("Invalid geometry provided: {}",e.getMessage());
            throw e;
        }
        catch (Exception e) {
            //Unexpected technical error
            log.error("Unexpected error during GeoJSON conversion",e);
            throw new InvalidGeometryException("Failed to parse GeoJSON geometry");
        }
    }

    /**
     * Remove Z dimension from geometry coordinates (forces 2D)
     * @param geometry
     * @return
     */
    private Geometry force2D(Geometry geometry) {
        Geometry copy = (Geometry) geometry.copy();
        copy.apply((CoordinateFilter) coord -> coord.setZ(Double.NaN));
        return copy;
    }

    /**
     * Validate geometry topology ana area
     * @param geometry
     */

    private void validate(Geometry geometry) {
        if (!geometry.isValid()) {
            throw new InvalidGeometryException("Invalid polygon (self-intersection)");
        }
        if (geometry.getArea() <= 0) {
            throw new InvalidGeometryException("Polygon area must be greater than zero");
        }
    }
}

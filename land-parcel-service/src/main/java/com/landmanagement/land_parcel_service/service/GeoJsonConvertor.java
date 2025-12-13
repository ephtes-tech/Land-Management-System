package com.landmanagement.land_parcel_service.service;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.springframework.stereotype.Component;


@Component
public class GeoJsonConvertor {
    private final GeometryFactory geometryFactory=new GeometryFactory();

    public Geometry convert(String geoJson){
        try {
            GeoJsonReader reader=new GeoJsonReader(geometryFactory);
            Geometry geom = reader.read(geoJson);
            if (geom == null) throw new IllegalArgumentException("Empty geometry");
            if (!(geom instanceof Polygon)) throw new IllegalArgumentException("GeoJSON is not a Polygon");
            return (Polygon) geom;
        } catch (ParseException e) {
            throw new RuntimeException("Invalid GeoJSON -> Polygon conversion: " + e.getMessage(), e);
        }
    }
}

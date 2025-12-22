package com.land.parcel.repository;

import com.land.parcel.model.Land;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LandRepo extends JpaRepository<Land,Long> {
    @Query(value = """
        SELECT EXISTS (
            SELECT 1
            FROM land l
            WHERE ST_Intersects(l.coordinates, :geometry)
              AND l.deleted = false
        )
        """, nativeQuery = true)
    boolean existsOverlappingLand(@Param("geometry")Geometry geometry);

    @Query(value = """
    SELECT *
    FROM land l
    WHERE ST_Contains(
        l.coordinates,
        ST_SetSRID(ST_Point(:lon, :lat), 4326)
    )
    AND l.deleted = false
    """, nativeQuery = true)
    List<Land> findLandContainingPoint(
            @Param("lon") double lon,
            @Param("lat") double lat
    );

}

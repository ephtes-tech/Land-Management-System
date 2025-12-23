package com.land.parcel.repository;

import com.land.parcel.model.Land;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LandRepo extends JpaRepository<Land,Long> {
    Optional<Land> findByLandIdAndDeletedFalse(Long id);

    List<Land> findAllByDeletedFalse();

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

    @Query(value = """
    SELECT *
    FROM land l
    WHERE ST_Within(l.coordinates, :boundary)
      AND l.deleted = false
    """, nativeQuery = true)
    List<Land> findWithinBoundary(
            @Param("boundary") Geometry boundary
    );

    @Query(value = """
    SELECT *
    FROM land l
    WHERE ST_DWithin(
        l.coordinates::geography,  -- updated column name
        :geometry::geography,
        :distance
    )
    AND l.deleted = false
    """, nativeQuery = true)
    List<Land> findNearbyLands(
            @Param("geometry") Geometry geometry,
            @Param("distance") double distanceMeters
    );



}

package com.landmanagement.land_parcel_service.repository;

import com.landmanagement.land_parcel_service.model.Land;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandRepo extends JpaRepository<Land,Long> {
}

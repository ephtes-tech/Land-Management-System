package com.land.parcel.repository;

import com.land.parcel.model.Land;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandRepo extends JpaRepository<Land,Long> {
}

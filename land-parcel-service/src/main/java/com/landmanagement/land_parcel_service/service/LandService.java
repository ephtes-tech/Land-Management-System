package com.landmanagement.land_parcel_service.service;

import com.landmanagement.land_parcel_service.dto.LandRequest;
import com.landmanagement.land_parcel_service.model.Land;
import com.landmanagement.land_parcel_service.repository.LandRepo;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LandService {
    private final GeoJsonConvertor geoJsonConvertor;
    private final LandRepo landRepo;

    public Land createLand(LandRequest landRequest, String currentUser){
        Polygon polygon=(Polygon) geoJsonConvertor.convert(landRequest.getCoordinates());
        Land land=Land.builder()
                .landType(landRequest.getLandType())
                .region(landRequest.getRegion())
                .zone(landRequest.getZone())
                .kebele(landRequest.getKebele())
                .coordinates(polygon)
                .description(landRequest.getDescription())
                .createdBy(currentUser)
                .updatedBy(currentUser)
                .build();
        return landRepo.save(land);
    }
}

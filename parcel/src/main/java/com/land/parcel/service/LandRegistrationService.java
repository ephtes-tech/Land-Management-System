package com.land.parcel.service;

import com.land.parcel.convertor.GeoJsonConverter;
import com.land.parcel.dto.LandRequestDto;
import com.land.parcel.dto.LandResponseDto;
import com.land.parcel.model.Land;
import com.land.parcel.repository.LandRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LandRegistrationService {
    private final LandRepo landRepo;
    private final GeoJsonConverter geoJsonConverter;

    @Transactional
    public LandResponseDto registerLand(LandRequestDto dto){
        Polygon polygon=geoJsonConverter.toPolygon(dto.getCoordinates().toString());
        Land land=Land.builder()
                .region(dto.getRegion())
                .woreda(dto.getWoreda())
                .kebele(dto.getKebele())
                .zone(dto.getZone())
                .landType(dto.getLandType())
                .description(dto.getDescription())
                .coordinates(polygon)
                .createdBy(dto.getCreatedBy())
                .updatedBy(dto.getUpdatedBy())
                .build();
        Land saved=landRepo.save(land);
        return LandResponseDto.builder().
                landId(saved.getLandId())
                .region(saved.getRegion())
                .zone(saved.getZone())
                .woreda(saved.getWoreda())
                .kebele(land.getKebele())
                .landType(saved.getLandType())
                .description(saved.getDescription())
                .build();
    }
}

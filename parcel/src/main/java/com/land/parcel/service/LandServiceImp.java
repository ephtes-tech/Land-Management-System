package com.land.parcel.service;

import com.land.parcel.convertor.GeoJsonConverter;
import com.land.parcel.dto.LandRequestDto;
import com.land.parcel.dto.LandResponseDto;
import com.land.parcel.exception.InvalidGeometryException;
import com.land.parcel.mapper.LandMapper;
import com.land.parcel.model.Land;
import com.land.parcel.repository.LandRepo;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.MultiPolygon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LandServiceImp  {
    private final LandRepo landRepo;
    private final LandMapper landMapper;
    private final GeoJsonConverter geoJsonConverter;

    @Transactional
    public LandResponseDto registerLand(LandRequestDto dto){
        MultiPolygon polygon=geoJsonConverter.toPolygon(dto.getCoordinates().toString());

        if (landRepo.existsOverlappingLand(polygon)){
            throw new InvalidGeometryException("Land overlaps with an existing parcel");
        }
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
        return landMapper.toDto(saved);
    }
    @Transactional(readOnly = true)
    public List<LandResponseDto> findLandByLocation(
            double longitude, double latitude) {

        return landRepo
                .findLandContainingPoint(longitude, latitude)
                .stream()
                .map(landMapper::toDto)
                .toList();
    }

}

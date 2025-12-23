package com.land.parcel.service;

import com.land.parcel.convertor.GeoJsonConverter;
import com.land.parcel.dto.LandRequestDto;
import com.land.parcel.dto.LandResponseDto;
import com.land.parcel.exception.InvalidGeometryException;
import com.land.parcel.exception.ResourceNotFoundException;
import com.land.parcel.mapper.LandMapper;
import com.land.parcel.model.Land;
import com.land.parcel.repository.LandRepo;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.MultiPolygon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.JsonNode;

import java.util.List;

@Service
@RequiredArgsConstructor

public class LandServiceImp implements LandService  {
    private static final Logger log = LoggerFactory.getLogger(LandServiceImp.class);
    private final LandRepo landRepo;
    private final LandMapper landMapper;
    private final GeoJsonConverter geoJsonConverter;



    @Override
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

    @Override
    public LandResponseDto getLand(Long id) {
        Land land= landRepo.findByLandIdAndDeletedFalse(id).orElseThrow(()->
                new ResourceNotFoundException("Land not found with id: {}"+id));
        return landMapper.toDto(land);
    }

    @Transactional(readOnly = true)
    @Override
    public List<LandResponseDto> getAllLands() {
        return landRepo.findAllByDeletedFalse().stream().map(landMapper::toDto).toList();
    }

    @Override
    public LandResponseDto updateLand(Long id, LandRequestDto dto) {
        Land land=landRepo.findByLandIdAndDeletedFalse(id).orElseThrow(
                ()->new ResourceNotFoundException("Land not found with id: {}"+id)
        );


        if (dto.getCoordinates() != null) {
            MultiPolygon geometry =
                    geoJsonConverter.toPolygon(dto.getCoordinates().toString());

            if (landRepo.existsOverlappingLand(geometry)) {
                throw new InvalidGeometryException("Updated land overlaps with existing parcel");
            }

            land.setCoordinates(geometry);
        }

        land.setRegion(dto.getRegion());
        land.setZone(dto.getZone());
        land.setWoreda(dto.getWoreda());
        land.setKebele(dto.getKebele());
        land.setLandType(dto.getLandType());
        land.setDescription(dto.getDescription());
        land.setUpdatedBy(dto.getUpdatedBy());

        return landMapper.toDto(landRepo.save(land));
    }

    @Override
    public void deleteLand(Long id) {
        Land land=landRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Land not found with id: {}"+id));
        land.setDeleted(true);
        landRepo.save(land);
        log.info("Land soft-deleted with id={}", id);
    }

    @Override
    public List<LandResponseDto> findContainingPoint(double lon, double lat) {
        return List.of();
    }

    @Override
    public List<LandResponseDto> findWithinBoundary(JsonNode boundary) {
        return List.of();
    }

    @Override
    public List<LandResponseDto> findNearby(JsonNode geometry, double distanceMeters) {
        return List.of();
    }


    @Override
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

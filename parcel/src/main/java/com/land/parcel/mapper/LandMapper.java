package com.land.parcel.mapper;

import com.land.parcel.convertor.GeoJsonConverter;
import com.land.parcel.dto.LandRequestDto;
import com.land.parcel.dto.LandResponseDto;
import com.land.parcel.model.Land;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring", uses = GeoJsonConverter.class)
public interface LandMapper {
    /**
     * Converts a Land entity to LandResponseDto, including coordinates as JsonNode.
     *
     * @param land the Land entity to convert
     * @return LandResponseDto including coordinates
     */

    @Mapping(target = "coordinates", expression = "java(geoJsonConverter.toJsonNode(land.getCoordinates()))")
    LandResponseDto toDto(Land land);
}

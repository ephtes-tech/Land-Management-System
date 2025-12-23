package com.land.parcel.mapper;

import com.land.parcel.convertor.GeoJsonConverter;
import com.land.parcel.dto.LandRequestDto;
import com.land.parcel.dto.LandResponseDto;
import com.land.parcel.model.Land;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring", uses = GeoJsonConverter.class)
public interface LandMapper {
    LandResponseDto toDto(Land land);
}

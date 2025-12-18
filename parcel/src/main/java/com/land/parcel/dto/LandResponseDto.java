package com.land.parcel.dto;

import com.land.parcel.enumr.LandType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LandResponseDto {
    private Long landId;
    private String region;
    private String zone;
    private String woreda;
    private String kebele;
    private LandType landType;
    private String description;
}

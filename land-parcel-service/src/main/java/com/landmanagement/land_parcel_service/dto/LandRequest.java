package com.landmanagement.land_parcel_service.dto;

import com.landmanagement.land_parcel_service.enumirator.LandType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LandRequest {
    private LandType landType;
    private String region;
    private String zone;
    private String woreda;
    private String kebele;
    private String coordinates;
    private String description;
}

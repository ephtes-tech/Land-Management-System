package com.land.parcel.dto;

import com.land.parcel.enumr.LandType;
import lombok.Data;
import org.locationtech.jts.geom.Polygon;
import tools.jackson.databind.JsonNode;


@Data
public class LandRequestDto {
    private String region;
    private String zone;
    private String woreda;
    private String kebele;
    private LandType landType;
    private String description;
    private String createdBy;
    private String updatedBy;
    private JsonNode coordinates;
}

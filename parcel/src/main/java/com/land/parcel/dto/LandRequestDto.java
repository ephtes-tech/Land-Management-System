package com.land.parcel.dto;

import com.land.parcel.enumr.LandType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.locationtech.jts.geom.Polygon;
import tools.jackson.databind.JsonNode;


@Data
public class LandRequestDto {
    @NotBlank(message = "Region is required")
    @Size(max = 100, message = "Region must not exceed 100 characters")
    private String region;

    @NotBlank(message = "Zone is required")
    @Size(max = 100, message = "Zone must not exceed 100 characters")
    private String zone;

    @NotBlank(message = "Woreda is required")
    @Size(max = 100, message = "Woreda must not exceed 100 characters")
    private String woreda;


    @NotBlank(message = "Kebele is required")
    @Size(max = 100, message = "Kebele must not exceed 100 characters")
    private String kebele;

    @NotNull(message = "Land type is required")
    private LandType landType;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotBlank(message = "CreatedBy is required")
    @Size(max = 100, message = "CreatedBy must not exceed 100 characters")
    private String createdBy;

    @NotBlank(message = "UpdatedBy is required")
    @Size(max = 100, message = "UpdatedBy must not exceed 100 characters")
    private String updatedBy;

    /**
     * GeoJSON coordinates (Polygon or MultiPolygon)
     */
    @NotNull(message = "Coordinates (GeoJSON) are required")
    private JsonNode coordinates;
}

package com.land.parcel.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ApiResponse {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;
    private String error;
    private String code;
    private Map<String, String> fieldErrors;
}

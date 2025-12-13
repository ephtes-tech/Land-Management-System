package com.landmanagement.land_parcel_service.controller;

import com.landmanagement.land_parcel_service.dto.LandRequest;
import com.landmanagement.land_parcel_service.repository.LandRepo;
import com.landmanagement.land_parcel_service.service.LandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/land")
@RequiredArgsConstructor
public class LandController {
    private final LandService landService;

    @PostMapping("/landRegister")
    public ResponseEntity<?> registerLand(@RequestBody LandRequest landRequest){
        String systemUser="User-land";
        return ResponseEntity.ok(landService.createLand(landRequest,systemUser));
    }
}

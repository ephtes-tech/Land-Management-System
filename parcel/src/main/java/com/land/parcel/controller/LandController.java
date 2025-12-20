package com.land.parcel.controller;

import com.land.parcel.dto.LandRequestDto;
import com.land.parcel.dto.LandResponseDto;
import com.land.parcel.model.Land;
import com.land.parcel.service.LandRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/land")
@RequiredArgsConstructor
public class LandController {
    private final LandRegistrationService landRegistrationService;

    @PostMapping("/registerLand")
    public LandResponseDto register(@Valid @RequestBody LandRequestDto landRequestDto){
        return landRegistrationService.registerLand(landRequestDto);
    }
}

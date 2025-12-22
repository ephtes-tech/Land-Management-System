package com.land.parcel.controller;

import com.land.parcel.dto.LandRequestDto;
import com.land.parcel.dto.LandResponseDto;
import com.land.parcel.service.LandService;
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
    private final LandService landService;

    @PostMapping("/registerLand")
    public LandResponseDto register(@Valid @RequestBody LandRequestDto landRequestDto){
        return landService.registerLand(landRequestDto);
    }
}

package com.land.parcel.service;

import com.land.parcel.dto.LandRequestDto;
import com.land.parcel.dto.LandResponseDto;

import java.util.List;

public interface LandService {
     LandResponseDto registerLand(LandRequestDto dto);
     LandResponseDto getLand(Long id);
     List<LandRequestDto> getAllLands();
     LandResponseDto updateLand(Long id,LandRequestDto landRequestDto);
     void deleteLand(Long id);


}

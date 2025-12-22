package com.land.parcel.service;

import com.land.parcel.dto.LandRequestDto;
import com.land.parcel.dto.LandResponseDto;

public interface LandInterface {
     LandResponseDto registerLand(LandRequestDto dto);

}

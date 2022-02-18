package com.api.parkingcontrol.services;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;

@Service
public interface ParkingSpotService {

    Page<ParkingSpotModel> getAll(Pageable pageable);

    Optional<ParkingSpotModel> findById(UUID id);

    void delete(UUID id);

    ParkingSpotModel create(@Valid ParkingSpotDto parkingSpot);

    ParkingSpotModel update(UUID id, @Valid ParkingSpotDto parkingSpot);

}

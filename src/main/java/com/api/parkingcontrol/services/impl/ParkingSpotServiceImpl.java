package com.api.parkingcontrol.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.exceptions.NegocioException;
import com.api.parkingcontrol.exceptions.ResourceNotFoundException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import com.api.parkingcontrol.services.ParkingSpotService;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService {

    final ParkingSpotRepository repository;

    public ParkingSpotServiceImpl(ParkingSpotRepository repository) {
	super();
	this.repository = repository;
    }

    @Override
    public Page<ParkingSpotModel> getAll(Pageable pageable) {
	return repository.findAll(pageable);
    }

    @Override
    public Optional<ParkingSpotModel> findById(UUID id) {
	return repository.findById(id);
    }

    @Override
    public void delete(UUID id) {
	validParkingSpotExistence(id);
	repository.deleteById(id);

    }

    @Transactional
    public ParkingSpotModel create(ParkingSpotDto parkingSpotDto) {

	validBusinessRuleNewParkingSpot(parkingSpotDto);

	return createParkingSpot(parkingSpotDto);
    }

    @Override
    public ParkingSpotModel update(UUID id, @Valid ParkingSpotDto parkingSpotDto) {

	validParkingSpotExistence(id);

	return updateParkingSpot(id, parkingSpotDto);
    }

    private void validParkingSpotExistence(UUID id) {
	if (!existsParkingSpot(id)) {
	    throw new ResourceNotFoundException("Conflict: Parking Spot Not Exists!");
	}
    }

    private ParkingSpotModel createParkingSpot(ParkingSpotDto parkingSpotDto) {
	ParkingSpotModel parkinSpotModel = new ParkingSpotModel();
	BeanUtils.copyProperties(parkingSpotDto, parkinSpotModel);

	return saveParkingSpot(parkinSpotModel);
    }

    private ParkingSpotModel updateParkingSpot(UUID id, ParkingSpotDto parkingSpotDto) {
	ParkingSpotModel parkinSpotModel = new ParkingSpotModel();
	BeanUtils.copyProperties(parkingSpotDto, parkinSpotModel);
	parkinSpotModel.setId(id);

	return saveParkingSpot(parkinSpotModel);
    }

    private ParkingSpotModel saveParkingSpot(ParkingSpotModel parkinSpotModel) {
	parkinSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

	return repository.save(parkinSpotModel);
    }

    private void validBusinessRuleNewParkingSpot(ParkingSpotDto parkingSpotDto) {

	if (existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
	    throw new NegocioException("Conflict: License Plate Car is already in use!");
	}
	if (existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
	    throw new NegocioException("Conflict: Parking Spot is already in use!");
	}
	if (existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {
	    throw new NegocioException("Conflict: Parking Spot already registered for this apartment/block!");
	}
    }

    private boolean existsByLicensePlateCar(String licensePlateCar) {
	return repository.existsByLicensePlateCar(licensePlateCar);
    }

    private boolean existsByParkingSpotNumber(String parkingSpotNumber) {
	return repository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    private boolean existsByApartmentAndBlock(String apartment, String block) {
	return repository.existsByApartmentAndBlock(apartment, block);
    }

    private boolean existsParkingSpot(UUID id) {
	return repository.existsById(id);
    }

}

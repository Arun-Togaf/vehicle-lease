package com.flexi.lease.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flexi.lease.model.Vehicle;
import com.flexi.lease.repository.VehiclesRepository;

/**
 * 
 * Service implementation class for managing vehicle data
 * 
 */
@Service
public class VehiclesServiceImpl implements VehiclesService {

	@Autowired
	private VehiclesRepository vehiclesRepository;

	/**
	 * Retrieves all vehicle records from the database.
	 * 
	 * @return a list of {@link Vehicle} objects representing all vehicle records in
	 *         the database
	 */
	@Override
	public List<Vehicle> findAll() {
		return vehiclesRepository.findAll();
	}

	/**
	 * Saves the specified Vehicle record in the database.
	 * 
	 * @param vehicle
	 * @return the saved {@link Vehicle} object
	 */
	@Override
	public Vehicle saveVehicle(Vehicle vehicle) {
		return vehiclesRepository.save(vehicle);
	}

	/**
	 * Deletes the vehicle record with the specified vehicle ID from the database.
	 * 
	 * @param vehicleId
	 */
	@Override
	public void deleteByVehicleId(Integer vehicleId) {
		vehiclesRepository.deleteById(vehicleId);
	}

	/**
	 * Retrieves the vehicle record with the specified vehicle ID from the database.
	 * 
	 * @param vehicleId
	 * @return Vehicle
	 */
	@Override
	public Vehicle findByVehicleId(Integer vehicleId) {
		return vehiclesRepository.findById(vehicleId).orElse(null);
	}

}

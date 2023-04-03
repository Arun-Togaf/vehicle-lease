package com.flexi.lease.service;

import java.util.List;

import com.flexi.lease.model.Vehicle;

/**
 * 
 * This interface provides the methods for accessing and manipulating vehicle
 * information.
 */
public interface VehiclesService {
	
	List<Vehicle> findAll();
	
	Vehicle saveVehicle(Vehicle vehicle);
	
	void deleteByVehicleId(Integer vehicleId);
	
	Vehicle findByVehicleId(Integer vehicleId);

}

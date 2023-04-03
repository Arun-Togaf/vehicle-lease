package com.flexi.lease.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.flexi.lease.model.Vehicle;
import com.flexi.lease.service.VehiclesService;

/**
 * Rest controller class to handle CRUD operations for Vehicles
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vehicles")
public class VehiclesController {

	private static final Logger logger = LoggerFactory.getLogger(VehiclesController.class);

	@Autowired
	private VehiclesService vehiclesService;

	/**
	 * Retrieves all Vehicles from the database
	 * 
	 * @return a list of Vehicle objects in JSON format
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Vehicle> getAllVehicles() {
		logger.info("In getAllVehicles(), endpoint : /vehicles");
		return vehiclesService.findAll();
	}

	/**
	 * 
	 * Creates a new Vehicle in the database
	 * 
	 * @param vehicle a Vehicle object to be created
	 * @return a response entity with the created Vehicle object and HTTP status
	 *         code 201
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
		logger.info("In createVehicle(), endpoint : /vehicles");
		try {
			return new ResponseEntity<>(vehiclesService.saveVehicle(vehicle), HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Exception while creating the Vehicle: " + e.getStackTrace());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 
	 * Retrieves a Vehicle by its Id from the database
	 * 
	 * @param vehicleId the Id of the Vehicle to be retrieved
	 * 
	 * @return a response entity with the retrieved Vehicle object and status OK
	 * 
	 *         or a response entity with status 404, if the Vehicle does not exist
	 */
	@RequestMapping(value = "/{vehicleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vehicle> getVehicle(@PathVariable Integer vehicleId) {
		logger.info("In getVehicle(), endpoint : /vehicles" + vehicleId);
		Vehicle vehicle = vehiclesService.findByVehicleId(vehicleId);

		if (vehicle != null) {
			return new ResponseEntity<>(vehicle, HttpStatus.OK);
		} else {
			logger.error("Vehicle does not exist");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 
	 * Updates a Vehicle in the database
	 * 
	 * @param vehicleId      the ID of the Vehicle to be updated
	 * 
	 * @param vehicleRequest a Vehicle object containing the updated data
	 * 
	 * @return a response entity with the updated Vehicle object and status OK
	 * 
	 *         or a response entity with status 404, if the Vehicle does not exist
	 */
	@RequestMapping(value = "/{vehicleId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vehicle> updateVehicle(@PathVariable Integer vehicleId, @RequestBody Vehicle vehicleRequest) {
		logger.info("In updateVehicle(), endpoint : /vehicles/" + vehicleId);
		Vehicle vehicle = vehiclesService.findByVehicleId(vehicleId);

		if (vehicle != null) {
			return new ResponseEntity<>(vehiclesService.saveVehicle(new Vehicle(vehicleId, vehicleRequest.getBrand(),
					vehicleRequest.getModel(), vehicleRequest.getModelYear(), vehicleRequest.getVin(),
					vehicleRequest.getPrice(), vehicleRequest.getLeasingContract())), HttpStatus.OK);
		} else {
			logger.error("Vehicle does not exist");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * 
	 * Deletes a Vehicle from the database
	 * 
	 * @param vehicleId the ID of the Vehicle to be deleted
	 * @return a response entity with status 204 if the deletion is successful or a
	 *         response entity with status 500 if an exception is thrown
	 */
	@RequestMapping(value = "/{vehicleId}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteVehicle(@PathVariable Integer vehicleId) {
		logger.info("In deleteVehicle(), endpoint : /vehicles/" + vehicleId);
		try {
			vehiclesService.deleteByVehicleId(vehicleId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			logger.error("Exception while deleting the vehicle: " + e.getStackTrace());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

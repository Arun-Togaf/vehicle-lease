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

import com.flexi.lease.model.LeasingContract;
import com.flexi.lease.service.LeasingContractsService;

/**
 * This controller handles the CRUD operations for leasing contracts. It exposes
 * endpoints to retrieve all leasing contracts, create a new leasing contract,
 * retrieve a leasing contract by contract number, update an existing leasing
 * contract, and delete a leasing contract by contract number.
 * 
 * CrossOrigin(origins = "*") - Enables Cross-Origin Resource Sharing (CORS) and
 * allows requests from any origin to access this REST endpoint
 * 
 * @RequestMapping - Specifies the base path for all REST endpoints of
 *                 LeasingContracts controller
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/leasing-contracts")
public class LeasingContractsController {

	private static final Logger logger = LoggerFactory.getLogger(LeasingContractsController.class);

	@Autowired
	private LeasingContractsService leasingContractsService;

	/**
	 * 
	 * GET endpoint to retrieve all leasing contracts
	 * 
	 * @return List of all leasing contracts in JSON format and HTTP status code 200
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LeasingContract>> getAllLeasingContracts() {
		logger.info("In getAllLeasingContracts(), endpoint : /leasing-contracts");
		return new ResponseEntity<>(leasingContractsService.findAll(), HttpStatus.OK);
	}

	/**
	 * 
	 * POST endpoint to create a new leasing contract
	 * 
	 * @param leasingContract The new leasing contract to create
	 * @return ResponseEntity with the created leasing contract in JSON format and
	 *         HTTP status code 201 (Created),
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LeasingContract> createLeasingContract(@RequestBody LeasingContract leasingContract) {
		logger.info("In createLeasingContract(), endpoint : /leasing-contracts");
		try {
			return new ResponseEntity<>(leasingContractsService.saveLeasingContract(leasingContract),
					HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Exception while creating the leasing contract: " + e.getStackTrace());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 
	 * GET endpoint to retrieve a leasing contract by contract number
	 * 
	 * @param contractNumber The contract number of the leasing contract to retrieve
	 * @return ResponseEntity with the retrieved leasing contract in JSON format and
	 *         HTTP status code 200 (OK),
	 */
	@RequestMapping(value = "/{contractNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LeasingContract> getLeasingContract(@PathVariable Integer contractNumber) {
		logger.info("In getLeasingContract(), endpoint : /leasing-contracts/" + contractNumber);
		LeasingContract leasingContract = leasingContractsService.findByContractNumber(contractNumber);

		if (leasingContract != null) {
			return new ResponseEntity<>(leasingContract, HttpStatus.OK);
		} else {
			logger.error("Leasing contract does not exist");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * 
	 * PUT endpoint to update an existing leasing contract
	 * 
	 * @param contractNumber         The contract number of the leasing contract to
	 *                               update
	 * @param leasingContractRequest The updated leasing contract information
	 * @return ResponseEntity with the updated leasing contract in JSON format and
	 *         HTTP status code 200 (OK),
	 */
	@RequestMapping(value = "/{contractNumber}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LeasingContract> updateLeasingContract(@PathVariable Integer contractNumber,
			@RequestBody LeasingContract leasingContractRequest) {
		logger.info("In updateLeasingContract(), endpoint : /leasing-contracts/" + contractNumber);
		LeasingContract leasingContract = leasingContractsService.findByContractNumber(contractNumber);

		if (leasingContract != null) {
			return new ResponseEntity<>(
					leasingContractsService.saveLeasingContract(
							new LeasingContract(contractNumber, leasingContractRequest.getMonthlyRate(),
									leasingContractRequest.getStartDate(), leasingContractRequest.getEndDate(),
									leasingContractRequest.getCustomer(), leasingContractRequest.getVehicle())),
					HttpStatus.OK);
		} else {
			logger.error("Leasing contract does not exist");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * 
	 * Deletes a leasing contract with the given contract number.
	 * 
	 * @param contractNumber the contract number of the leasing contract to be
	 *                       deleted
	 * @return a ResponseEntity with HTTP status code 204
	 */
	@RequestMapping(value = "/{contractNumber}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteLeasingContract(@PathVariable Integer contractNumber) {
		logger.info("In deleteLeasingContract(), endpoint : /leasing-contracts/" + contractNumber);
		try {
			leasingContractsService.deleteByContractNumber(contractNumber);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			logger.error("Exception while deleting the leasing contract: " + e.getStackTrace());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

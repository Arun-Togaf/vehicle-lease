package com.flexi.lease.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flexi.lease.model.LeasingContract;
import com.flexi.lease.repository.LeasingContractsRepository;

/**
 * 
 * Service class that implements the LeasingContractsService interface and
 * provides the business logic for leasing contracts.
 */
@Service
public class LeasingContractsServiceImpl implements LeasingContractsService {

	@Autowired
	private LeasingContractsRepository leasingContractsRepository;

	/**
	 * Returns a list of all existing leasing contracts.
	 * 
	 * @return List of LeasingContract objects.
	 */
	@Override
	public List<LeasingContract> findAll() {
		return leasingContractsRepository.findAll();
	}

	/**
	 * Saves the given leasing contract to the database.
	 * 
	 * @param leasingContract 
	 * @return leasingContract
	 */
	@Override
	public LeasingContract saveLeasingContract(LeasingContract leasingContract) {
		return leasingContractsRepository.save(leasingContract);
	}

	/**
	 * Deletes a leasing contract from the database based on the contract number.
	 * 
	 * @param contractNumber 
	 */
	@Override
	public void deleteByContractNumber(Integer contractNumber) {
		leasingContractsRepository.deleteById(contractNumber);

	}

	/**
	 * Returns a leasing contract from the database based on the contract number.
	 * 
	 * @param contractNumber 
	 * @return leasingContract
	 */
	@Override
	public LeasingContract findByContractNumber(Integer contractNumber) {
		return leasingContractsRepository.findById(contractNumber).orElse(null);
	}

}

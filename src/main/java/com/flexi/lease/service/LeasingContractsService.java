package com.flexi.lease.service;

import java.util.List;

import com.flexi.lease.model.LeasingContract;

/**
 * 
 * This interface provides the methods for accessing and manipulating Leasing
 * contract information.
 */
public interface LeasingContractsService {

	List<LeasingContract> findAll();

	LeasingContract saveLeasingContract(LeasingContract leasingContract);

	void deleteByContractNumber(Integer contractNumber);

	LeasingContract findByContractNumber(Integer contractNumber);

}

package com.flexi.lease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flexi.lease.model.LeasingContract;

/**
 * 
 * The LeasingContractsRepository interface provides the necessary CRUD methods
 * for accessing and manipulating LeasingContract objects.
 * 
 * It inherits all the methods from JpaRepository, and additional custom methods
 * can be defined here. This interface is annotated with @Repository, which
 * marks it as a Spring Data repository component. It enables the component
 * scanning and automatic injection of the repository bean into other Spring
 * components.
 * 
 */
@Repository
public interface LeasingContractsRepository extends JpaRepository<LeasingContract, Integer> {

}

package com.flexi.lease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flexi.lease.model.Customer;

/**
 * 
 * This interface defines methods to interact with the Customer entity in the
 * database. and provides a set of generic CRUD methods to interact with the
 * database. 
 *
 */
@Repository
public interface CustomersRepository extends JpaRepository<Customer, Integer> {

}

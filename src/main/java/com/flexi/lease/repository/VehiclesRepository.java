package com.flexi.lease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flexi.lease.model.Vehicle;

/**
 * 
 * The @Repository annotation is used to indicate that the VehiclesRepository
 * interface is a repository interface that extends the JpaRepository interface.
 * This interface provides methods for performing CRUD operations on the Vehicle
 * entity
 * 
 */
@Repository
public interface VehiclesRepository extends JpaRepository<Vehicle, Integer> {

}

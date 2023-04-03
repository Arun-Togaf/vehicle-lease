package com.flexi.lease.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * An entity class representing a vehicle in the system.
 */
@Entity
@Table(name = "vehicles")
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicle_id")
	private Integer vehicleId;

	@NotNull
	private String brand;

	@NotNull
	private String model;

	@NotNull
	@Column(name = "model_year")
	private int modelYear;

	@NotNull
	@Size(min = 10, max = 15)
	private String vin;

	@NotNull
	private double price;

	/**
	 * The leasing contract associated with the vehicle.
	 */
	@OneToOne(mappedBy = "vehicle")
	private LeasingContract leasingContract;

	public Vehicle() {

	}

	/**
	 * Constructor to create a new Vehicle.
	 * 
	 * @param vehicleId        The unique identifier of the vehicle.
	 * @param brand            The brand of the vehicle.
	 * @param model            The model of the vehicle.
	 * @param modelYear        The year of the model.
	 * @param vin              The Vehicle Identification Number (VIN) of the vehicle.
	 * @param price            The price of the vehicle.
	 * @param leasingContract  The leasing contract associated with the vehicle.
	 */
	public Vehicle(Integer vehicleId, String brand, String model, int modelYear, String vin, double price,
			LeasingContract leasingContract) {
		this.vehicleId = vehicleId;
		this.brand = brand;
		this.model = model;
		this.modelYear = modelYear;
		this.vin = vin;
		this.price = price;
		this.leasingContract = leasingContract;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getModelYear() {
		return modelYear;
	}

	public void setModelYear(int modelYear) {
		this.modelYear = modelYear;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LeasingContract getLeasingContract() {
		return leasingContract;
	}

	public void setLeasingContract(LeasingContract leasingContract) {
		this.leasingContract = leasingContract;
	}

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

}

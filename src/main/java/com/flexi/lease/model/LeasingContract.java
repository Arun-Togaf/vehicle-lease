package com.flexi.lease.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class represents a Leasing Contract entity in the system, which has a
 * unique contract number and contains information about the monthly rate, start
 * date, and end date of the leasing contract. It also has a reference to the
 * customer and the vehicle associated with the leasing contract.
 * 
 */
@Entity
@Table(name = "leasing_contracts")
public class LeasingContract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contract_number")
	private Integer contractNumber;

	@NotNull
	@Column(name = "monthly_rate")
	private double monthlyRate;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	/**
	 * @ManyToOne indicates that many LeasingContract entities can be associated
	 *            with one Customer entity.
	 * @JsonIgnore annotation is used to indicate that these fields should be
	 *             ignored during serialization and deserialization.
	 */
	@ManyToOne
	@JoinColumn(name = "customer_id")
	@NotNull
	@JsonIgnore
	private Customer customer;

	/**
	 * @OneToOne indicates that one LeasingContract entity can be associated with
	 *           one Vehicle entity.
	 * @JsonIgnore annotation is used to indicate that these fields should be
	 *             ignored during serialization and deserialization.
	 */
	@OneToOne
	@JoinColumn(name = "vehicle_id")
	@NotNull
	@JsonIgnore
	private Vehicle vehicle;

	public LeasingContract() {

	}

	/**
	 * Constructs a new leasing contract object with the given contract number,
	 * monthly rate, start date, end date, customer, and vehicle.
	 * 
	 * @param contractNumber The unique contract number for the leasing contract.
	 * @param monthlyRate    The monthly rate for the leasing contract.
	 * @param startDate      The start date of the leasing contract.
	 * @param endDate        The end date of the leasing contract.
	 * @param customer       The customer associated with the leasing contract.
	 * @param vehicle        The vehicle associated with the leasing contract.
	 */
	public LeasingContract(Integer contractNumber, double monthlyRate, Date startDate, Date endDate, Customer customer,
			Vehicle vehicle) {
		this.contractNumber = contractNumber;
		this.monthlyRate = monthlyRate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.customer = customer;
		this.vehicle = vehicle;
	}

	public Integer getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(Integer contractNumber) {
		this.contractNumber = contractNumber;
	}

	public double getMonthlyRate() {
		return monthlyRate;
	}

	public void setMonthlyRate(double monthlyRate) {
		this.monthlyRate = monthlyRate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}

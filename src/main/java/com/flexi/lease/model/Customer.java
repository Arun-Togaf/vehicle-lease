package com.flexi.lease.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * This class represents a Customer entity in the database.
 */
@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Integer customerId;

	@NotNull
	@Column(name = "first_name")
	private String firstName;

	@NotNull
	@Column(name = "last_name")
	private String lastName;

	private Date birthdate;

	@Column(name = "contact_number")
	private String contactNumber;

	@Column(name = "email_id")
	private String emailId;

	/**
	 * 
	 * This field represents the leasing contracts that the customer has, this
	 * represents a one-to-many relationship between Customer and LeasingContract
	 * entities.
	 * 
	 * The cascade attribute specifies that any changes made to this field should be
	 * cascaded to the LeasingContract entities associated with this customer.
	 * 
	 */
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<LeasingContract> leasingContracts;

	public Customer() {

	}

	/**
	 * Constructor for Customer entity with all fields.
	 * 
	 * @param customerId       the id of the customer
	 * @param firstName        the first name of the customer
	 * @param lastName         the last name of the customer
	 * @param birthdate        the birth date of the customer
	 * @param contactNumber    the contact number of the customer
	 * @param emailId          the email id of the customer
	 * @param leasingContracts the list of leasing contracts associated with the
	 *                         customer
	 */
	public Customer(Integer customerId, String firstName, String lastName, Date birthdate, String contactNumber,
			String emailId, List<LeasingContract> leasingContracts) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.contactNumber = contactNumber;
		this.emailId = emailId;
		this.leasingContracts = leasingContracts;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public List<LeasingContract> getLeasingContracts() {
		return leasingContracts;
	}

	public void setLeasingContracts(List<LeasingContract> leasingContracts) {
		this.leasingContracts = leasingContracts;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}

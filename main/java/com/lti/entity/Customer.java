package com.lti.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="proj_customer")
public class Customer {
	
	@Id
	@GeneratedValue
	int customerId;
	
	String firstName;
	String middleName;
	String lastName;
	String mobile;
	String email;
	String aadharNo;
	LocalDate dateOfBirth;
	String panCard;
	String panCardFile;
	String aadharCardFile;
	
	@SequenceGenerator(name="password_seq", initialValue=50000, allocationSize=10)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="password_seq")
	int password;
	
	String occupationType;
	double annualIncome;
	
	ApprovalStatus status = ApprovalStatus.APPLIED;
	
	public ApprovalStatus getStatus() {
		return status;
	}

	public void setStatus(ApprovalStatus status) {
		this.status = status;
	}


	@OneToOne(mappedBy="customer",cascade =CascadeType.ALL)
	Address address;
	
	@OneToMany(mappedBy="customer",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	List<Account> account;
	
	@OneToMany(mappedBy="customer",cascade=CascadeType.ALL)
	List<Beneficiary> beneficiary;
	
//	@OneToOne(mappedBy="customer",cascade =CascadeType.ALL)
//	Verification verification;
	
	


	public Customer() {
		super();
	}
	

	public Customer(int customerId, String firstName, String middleName, String lastName, String mobile, String email,
			String aadharNo, LocalDate dateOfBirth, String panCard, int password, String occupationType,
			int annualIncome) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.mobile = mobile;
		this.email = email;
		this.aadharNo = aadharNo;
		this.dateOfBirth = dateOfBirth;
		this.panCard = panCard;
		this.password = password;
		this.occupationType = occupationType;
		this.annualIncome = annualIncome;
	}


	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPanCard() {
		return panCard;
	}

	public void setPanCard(String panCard) {
		this.panCard = panCard;
	}

	

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public String getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(String occupationType) {
		this.occupationType = occupationType;
	}

	public double getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(double d) {
		this.annualIncome = d;
	}

	@JsonIgnore
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}


	@JsonIgnore
	public List<Account> getAccount() {
		return account;
	}

	public void setAccount(List<Account> account) {
		this.account = account;
	}


	@JsonIgnore
	public List<Beneficiary> getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(List<Beneficiary> beneficiary) {
		this.beneficiary = beneficiary;
	}
	
	public String getPanCardFile() {
		return panCardFile;
	}

	public void setPanCardFile(String panCardFile) {
		this.panCardFile = panCardFile;
	}

	public String getAadharCardFile() {
		return aadharCardFile;
	}

	public void setAadharCardFile(String aadharCardFile) {
		this.aadharCardFile = aadharCardFile;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", mobile=" + mobile + ", email=" + email + ", aadharNo=" + aadharNo
				+ ", dateOfBirth=" + dateOfBirth + ", panCard=" + panCard + ", password=" + password
				+ ", occupationType=" + occupationType + ", annualIncome=" + annualIncome + ", address=" + address
				+ "]";
	}
	

}

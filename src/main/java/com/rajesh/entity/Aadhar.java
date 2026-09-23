package com.rajesh.entity;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Aadhar implements Comparable<Aadhar>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private long aadharCardNumber;
    private Date dateOfBirth;
    private String address;
    
    @OneToOne
    @JoinColumn(name="forgin_key_person")
    private Person person;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getAadharCardNumber() {
		return aadharCardNumber;
	}

	public void setAadharCardNumber(long aadharCardNumber) {
		this.aadharCardNumber = aadharCardNumber;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Aadhar() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Aadhar(long aadharCardNumber, Date dateOfBirth, String address, Person person) {
		super();
		this.aadharCardNumber = aadharCardNumber;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.person = person;
	}
	public int compareTo(Aadhar aadhar)
	{
		if(this.id>aadhar.getId())
		{
			return 1;
		}else if(this.id<aadhar.getId())
		{
			return -1;
		}else
		{
		return 0;
		}
	}
    
    
    
    
    
    
}

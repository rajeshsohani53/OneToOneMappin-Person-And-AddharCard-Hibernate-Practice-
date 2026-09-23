package com.rajesh.main;

import java.util.Date;

import com.rajesh.dbutil.PersonDao;
import com.rajesh.entity.Aadhar;
import com.rajesh.entity.Person;

public class Main {
public static void main(String[] args) {
	//we have person and person has a Addhar Card
	
	Person person=new Person();
	Aadhar aadhar=new Aadhar();
	
	person.setName("Rajesh Ravi Sohani");
	aadhar.setAadharCardNumber(123456789);
	aadhar.setAddress("CSN");
	aadhar.setDateOfBirth(new Date());
	aadhar.setPerson(person);
	person.setAadharCard(aadhar);
	
	PersonDao.insert(person, aadhar);
	
	
}
}

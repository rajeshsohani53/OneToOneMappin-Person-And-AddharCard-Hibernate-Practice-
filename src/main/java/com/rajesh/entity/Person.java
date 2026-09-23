package com.rajesh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Person implements Comparable<Person>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
  private int id;
  private String name;
  
  @OneToOne(mappedBy = "person")
  private Aadhar aadharCard;

  public int getId() {
	return id;
  }

  public void setId(int id) {
	this.id = id;
  }

  public String getName() {
	return name;
  }

  public void setName(String name) {
	this.name = name;
  }

  public Aadhar getAadharCard() {
	return aadharCard;
  }

  public void setAadharCard(Aadhar aadharCard) {
	this.aadharCard = aadharCard;
  }

  public Person(String name, Aadhar aadharCard) {
	super();
	this.name = name;
	this.aadharCard = aadharCard;
  }

  public Person() {
	super();
	// TODO Auto-generated constructor stub
  }

  @Override
  public int compareTo(Person person) {
	if(this.id>person.getId())
	{
		return 1;
	}else if(this.id<person.getId())
	{
		return -1;
	}else
	{
	return 0;
	}
  
  }
  
}

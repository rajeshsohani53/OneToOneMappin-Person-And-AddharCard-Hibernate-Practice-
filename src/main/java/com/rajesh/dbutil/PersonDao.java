package com.rajesh.dbutil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.rajesh.entity.Aadhar;
import com.rajesh.entity.Person;
import com.rajesh.utility.FactoryProvider;

public class PersonDao {
  static SessionFactory f;
  public static void insert(Person person,Aadhar aadhar)
  {
	  f=FactoryProvider.getSessionFactory();
	  Session s=f.openSession();
	  Transaction tx=s.beginTransaction();
	  s.persist(person);
	  s.persist(aadhar);
	  tx.commit();
	  s.close();
  }
}

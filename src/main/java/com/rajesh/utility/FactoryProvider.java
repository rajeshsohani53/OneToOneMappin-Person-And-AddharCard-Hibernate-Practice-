package com.rajesh.utility;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryProvider {
  public static SessionFactory factory;
  public static SessionFactory getSessionFactory()
  {
	  if(factory==null)
	  {
		  Configuration cfg=new Configuration();
		  cfg.configure();
		  factory= cfg.buildSessionFactory();
		  
		  return factory;
	  }
	  return factory;
  }
  
}

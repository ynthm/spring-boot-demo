package com.ynthm.spring.jpa.demo.user.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.ynthm.spring.jpa.demo.Auditable_ {

	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SingularAttribute<User, String> emailAddress;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Boolean> enable;
	public static volatile ListAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> username;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String EMAIL_ADDRESS = "emailAddress";
	public static final String PASSWORD = "password";
	public static final String ENABLE = "enable";
	public static final String ROLES = "roles";
	public static final String ID = "id";
	public static final String USERNAME = "username";

}


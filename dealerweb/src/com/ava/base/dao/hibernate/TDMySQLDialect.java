package com.ava.base.dao.hibernate;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLDialect;

public class TDMySQLDialect extends MySQLDialect {

	public TDMySQLDialect() {
		super();   
        registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
        registerHibernateType(Types.BIGINT, Hibernate.LONG.getName());
	}
	

}

package com.alura.hotel.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Factory {

	private DataSource dataSource;
	
	
	public Factory() {
		
		ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
	
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/hotel_alura?useTimeZone=true&serverTimeZone=UTC");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("1234");
		
		this.dataSource = pooledDataSource;
	}
	
	public Connection recuperarConnection() {
		
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Hubo error");
			throw new RuntimeException(e);
		}
	}
	
	
	
	
}

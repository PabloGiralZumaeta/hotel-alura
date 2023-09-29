package com.alura.hotel.test;

import java.sql.Connection;
import java.sql.SQLException;

import com.alura.hotel.factory.Factory;

public class Test {

	public static void main(String[] args) throws SQLException {
		Factory con = new Factory();
		Connection conexion = con.recuperarConnection();
	
		System.out.println("Conectó bien");
		conexion.close();
		System.out.println("Cerro bien");
	}
}

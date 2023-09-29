package com.alura.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alura.hotel.modelo.Usuarios;import com.mysql.cj.xdevapi.Result;

public class UsuariosDAO {

	private  Connection con;

	public UsuariosDAO(Connection con) {
		this.con = con;
	}

	public  boolean validarUsuario(Usuarios usuarios) {
		String sql = "SELECT * FROM usuarios WHERE nombre=? AND contraseña=?";

		try {
			

			try (PreparedStatement statement = con.prepareStatement(sql) ) {
				statement.setString(1, usuarios.getNombre());
				statement.setString(2, usuarios.getContraseña());
				System.out.println(usuarios.getContraseña());
				
				
				try(ResultSet result = statement.executeQuery()){
					return result.next();
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e + " hay error");
		}
	

	}

}

package com.alura.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.alura.hotel.modelo.FormasDePago;
import com.alura.hotel.modelo.Reservas;

public class FormaDePagoDAO {

	private Connection con;

	public FormaDePagoDAO(Connection con) {
		this.con = con;
	}

	public List<FormasDePago> listarFormasDePago() {

		List<FormasDePago> lista = new ArrayList<FormasDePago>();
		String sql = "SELECT * FROM formas_de_pago";
		try {

			try (PreparedStatement statement = con.prepareStatement(sql);
					ResultSet resultSet = statement.executeQuery();) {

				while (resultSet.next()) {
					var formaPago = new FormasDePago(resultSet.getInt("id_forma_pago"),
							resultSet.getString("nombre_forma_pago"));

					lista.add(formaPago);
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return lista;

	}

	public String buscarNombrePorId(int idFormaPago) {
		String nombrePago = null;
		String sql = "SELECT nombre_forma_pago FROM formas_de_pago WHERE id_forma_pago = ?";
		try {
			try (PreparedStatement statement = con.prepareStatement(sql);) {
				statement.setInt(1, idFormaPago);
				statement.execute();
				try (ResultSet result = statement.getResultSet()) {
					while(result.next()) {
						
						nombrePago = result.getString("nombre_forma_pago");
					}
					
				}
			}
			return 	nombrePago;
//			return formaPago;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

	public int buscarIdPorNombre(String nombre) {
		
		int idFormaPago = 0;
		String sql = "SELECT id_forma_pago FROM formas_de_pago WHERE nombre_forma_pago = ?";
		try {
			try (PreparedStatement statement = con.prepareStatement(sql);) {
				statement.setString(1, nombre);
				statement.execute();
				try (ResultSet result = statement.getResultSet()) {
					while(result.next()) {
						
						idFormaPago = result.getInt("id_forma_pago");
					}
					
				}
			}
			return 	idFormaPago;
//			return formaPago;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
		
}

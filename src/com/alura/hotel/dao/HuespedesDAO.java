package com.alura.hotel.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.alura.hotel.modelo.Huespedes;
import com.alura.hotel.modelo.Reservas;

public class HuespedesDAO {

	private Connection con;

	public HuespedesDAO(Connection con) {
		this.con = con;
	}

	public void guardarHuesped(Huespedes huesped) {
		System.out.println("CLICK");
		String sql = "INSERT INTO huespedes" + "(nombre, apellido, fecha_de_nacimiento, "
				+ "nacionalidad, telefono, id_reserva) VALUES(" + "?,?,?,?,?,?);";

		try {
			try (PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
				statement.setString(1, huesped.getNombre());
				statement.setString(2, huesped.getApellido());
				statement.setObject(3, huesped.getFechaNacimiento());
				statement.setString(4, huesped.getNacionalidad());
				statement.setString(5, huesped.getTelefono());
				statement.setLong(6, huesped.getIdReserva());
				statement.executeUpdate();
				try (ResultSet result = statement.getGeneratedKeys();) {
					while(result.next()) {
						huesped.setIdHuesped(result.getLong(1));
					}
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			throw new RuntimeException(e);
		}
	}
	
	
	public List<Huespedes> listarHuesped() {
		List<Huespedes> huespedList = new ArrayList<Huespedes>();

		try {
			String sql = "SELECT id_huesped, nombre, apellido, fecha_de_nacimiento,nacionalidad, telefono, id_reserva  FROM huespedes";
			try (PreparedStatement statement = con.prepareStatement(sql);) {
				statement.execute();

				mostrarHuesped(huespedList, statement);

			}
			return huespedList;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}

	}
	


	
	public List<Huespedes> listarHuespedPorId(Long idHuesped) {
		List<Huespedes> huespedList = new ArrayList<Huespedes>();

		try {
			String sql = "SELECT id_huesped, nombre, apellido, fecha_de_nacimiento,nacionalidad, telefono, id_reserva  FROM huespedes WHERE id_huesped=?";
			try (PreparedStatement statement = con.prepareStatement(sql);) {
				statement.setLong(1, idHuesped);
				statement.execute();

				mostrarHuesped(huespedList, statement);

			}
			return huespedList;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}

	}
	
	private void mostrarHuesped(List<Huespedes> huespedList, Statement statement) {
		try (ResultSet result = statement.getResultSet()) {

			while (result.next()) {
				Long idHuesped = result.getLong("id_huesped");
				// Nota: plusDay(0) --> prueba -> suma dias 
				String nombre = result.getString("nombre");
				String apellido = result.getString("apellido");
				LocalDate fechaN = result.getDate("fecha_de_nacimiento").toLocalDate();
				String nacionalidad = result.getString("nacionalidad");
				String telefono = result.getString("telefono");
				Long idReserva = result.getLong("id_reserva");
				Huespedes huesped = new Huespedes(idHuesped, nombre, apellido, fechaN, nacionalidad, telefono, idReserva);

				huespedList.add(huesped);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	
	public void actualizarHuesped(String nombre,String apellido,LocalDate fechaNacimiento,String nacionalidad,String telefono,Long idReserva, Long idHuesped ) {
String sql = "UPDATE huespedes SET nombre = ? ,apellido = ? , "
		+ "fecha_de_nacimiento = ? ,nacionalidad = ?, telefono = ?, "
		+ "id_reserva = ?  WHERE id_huesped = ?";
		
		try(PreparedStatement statement = con.prepareStatement(sql)){
			//SI ES METER UN OBJETO Y LO FIJAMOS COMO LocalDate es mejor pasarlo como Object
//			statement.setObject(1, Date.valueOf(fechaE));
			statement.setString(1, nombre);
			statement.setString(2, apellido);
			statement.setObject(3, fechaNacimiento);
			statement.setString(4, nacionalidad);
			statement.setString(5, telefono);
			statement.setLong(6, idReserva);
			statement.setLong(7, idHuesped);
			
			statement.executeUpdate();
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
			// TODO: handle exception
		}
		
	}
	
	
	public void eliminarHuesped(Long idHuesped) {
		String sql = "DELETE FROM huespedes WHERE id_huesped = ?";
		
		try{
			try(Statement stm = con.createStatement()){
				stm.execute("SET FOREIGN_KEY_CHECKS=0");
				try(PreparedStatement statement = con.prepareStatement(sql);
					){
					statement.setLong(1, idHuesped);
					statement.execute();
					stm.execute("SET FOREIGN_KEY_CHECKS=1");
					
				}
			}
			
			
			
		}catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	
	
	public void eliminarPorIdReserva(Long idReserva) {
		String sql = "DELETE FROM huespedes WHERE id_reserva = ?";

		try{
			try(Statement stm = con.createStatement()){
				stm.execute("SET FOREIGN_KEY_CHECKS=0");
				try(PreparedStatement statement = con.prepareStatement(sql);
					){
					statement.setLong(1, idReserva);
					statement.execute();
					stm.execute("SET FOREIGN_KEY_CHECKS=1");
					
				}
			}
			
			
			
		}catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	
	
	
}

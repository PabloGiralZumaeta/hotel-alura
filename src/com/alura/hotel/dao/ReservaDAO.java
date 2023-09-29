package com.alura.hotel.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.alura.hotel.modelo.Reservas;

public class ReservaDAO {

	private Connection con;

	public ReservaDAO(Connection con) {
		this.con = con;
	}

	public void guardarReserva(Reservas reserva) {

		String sql = "INSERT INTO Reservas(fecha_entrada, fecha_salida, valor, id_forma_pago) VALUES(?,?,?,?)";

		try (PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			// Se utilizó Object porque al ser LocalDate(Nueva Api a partir de J8 ) no se
			// encuentra en jdbc
			statement.setObject(1, reserva.getFechaE());
			statement.setObject(2, reserva.getFechaS());
			statement.setBigDecimal(3, reserva.getValor());
			statement.setInt(4, reserva.getIdFormaPago());
			statement.executeUpdate();
			try (ResultSet result = statement.getGeneratedKeys();) {
				while (result.next()) {
					reserva.setIdReserva(result.getLong(1));
					;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);

		}
	}

	public List<Reservas> listarReservas() {
		List<Reservas> reservasList = new ArrayList<Reservas>();

		try {
			String sql = "SELECT id_reserva, fecha_entrada, fecha_salida, valor, id_forma_pago  FROM Reservas";
			try (PreparedStatement statement = con.prepareStatement(sql);) {
				statement.execute();

				mostrarReserva(reservasList, statement);

			}
			return reservasList;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}

	}

	public List<Reservas> listarReservaPorID(Long id) {
		List<Reservas> reservaList = new ArrayList<Reservas>();

		String sql = "SELECT id_reserva, fecha_entrada, fecha_salida, valor, id_forma_pago FROM Reservas WHERE id_reserva = ?";
		try (PreparedStatement statement = con.prepareStatement(sql)) {
			statement.setLong(1, id);
			statement.execute();
			mostrarReserva(reservaList, statement);
			return reservaList;
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

	private void mostrarReserva(List<Reservas> reservaList, Statement statement) {
		try (ResultSet result = statement.getResultSet()) {

			while (result.next()) {
				Long idReserva = result.getLong("id_reserva");
				// Nota: plusDay(0) --> prueba
				LocalDate fechaEntrada = result.getDate("fecha_entrada").toLocalDate();
				LocalDate fechaSalida = result.getDate("fecha_salida").toLocalDate();
				BigDecimal valor = result.getBigDecimal("valor");
				Integer idFormaPago = result.getInt("id_forma_pago");
				Reservas reserva = new Reservas(idReserva, fechaEntrada, fechaSalida, valor, idFormaPago);

				reservaList.add(reserva);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

	public void actualizarReserva(LocalDate fechaE, LocalDate fechaS, BigDecimal valor, int idFormaPago,
			Long idReserva) {
		String sql = "UPDATE Reservas SET fecha_entrada = ? ,fecha_salida = ? ,valor =? ,id_forma_pago= ?  WHERE id_reserva = ?";
		
		try(PreparedStatement statement = con.prepareStatement(sql)){
			//SI ES METER UN OBJETO Y LO FIJAMOS COMO LocalDate es mejor pasarlo como Object
//			statement.setObject(1, Date.valueOf(fechaE));
			statement.setObject(1, fechaE);
			statement.setObject(2, fechaS);
			statement.setBigDecimal(3, valor);
			statement.setInt(4, idFormaPago);
			statement.setLong(5, idReserva );
			
			statement.executeUpdate();
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
			// TODO: handle exception
		}

	}
	
	
	public void eliminarReserva(Long idReserva) {
		String sql = "DELETE FROM Reservas WHERE id_reserva = ?";
		
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

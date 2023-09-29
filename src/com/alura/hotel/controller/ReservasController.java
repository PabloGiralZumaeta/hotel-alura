package com.alura.hotel.controller;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.alura.hotel.dao.ReservaDAO;
import com.alura.hotel.factory.Factory;
import com.alura.hotel.modelo.Reservas;

public class ReservasController {

	private ReservaDAO reservaDao;
	
	
	public ReservasController() {
		Factory factory = new Factory();
		this.reservaDao = new ReservaDAO(factory.recuperarConnection());
	}
	
	public void guardarReserva(Reservas reserva, Integer formaPago) {
		reserva.setIdFormaPago(formaPago);
		this.reservaDao.guardarReserva(reserva);
	}
	
	public List<Reservas> listarReservas(){
		return reservaDao.listarReservas();
	}
	
	public List<Reservas> listarReservasPorId(Long id){
		return reservaDao.listarReservaPorID(id);
	}
	
	public void actualizarReserva(LocalDate fechaE, LocalDate fechaS, BigDecimal valor, int idFormaPago,
			Long idReserva) {
		this.reservaDao.actualizarReserva(fechaE, fechaS, valor, idFormaPago, idReserva);
	}
	
	public void eliminarReserva(Long id) {
		this.reservaDao.eliminarReserva(id);
	}
}

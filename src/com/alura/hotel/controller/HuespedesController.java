package com.alura.hotel.controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import com.alura.hotel.dao.HuespedesDAO;
import com.alura.hotel.factory.Factory;
import com.alura.hotel.modelo.Huespedes;

public class HuespedesController {

	private HuespedesDAO huespedDao;
	
	
	public HuespedesController() {
		// TODO Auto-generated constructor stub
		Factory factory = new Factory();
		this.huespedDao = new HuespedesDAO(factory.recuperarConnection());
	}
	
	public void guardarHuesped( Huespedes huesped) {
		this.huespedDao.guardarHuesped(huesped);
	}
	
	public List<Huespedes> listarHuesped() {
		return this.huespedDao.listarHuesped();
	}
	
	public List<Huespedes> listarHuespedPorId(Long idHuesped) {
		return this.huespedDao.listarHuespedPorId(idHuesped);
	}
	
	
	public void actualizarHuesped(String nombre,String apellido,LocalDate fechaNacimiento,String nacionalidad,String telefono,Long idReserva, Long idHuesped ) {
		this.huespedDao.actualizarHuesped(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva, idHuesped);
	}
	
	public void eliminarHuesped(Long idHuesped) {
		this.huespedDao.eliminarHuesped(idHuesped);
	}
	
	public void elimnarHuespedPorIdReserva(Long idReserva) {
		this.huespedDao.eliminarPorIdReserva(idReserva);
	}
	
	
}

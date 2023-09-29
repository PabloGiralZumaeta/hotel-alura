package com.alura.hotel.controller;

import java.util.List;

import com.alura.hotel.dao.FormaDePagoDAO;
import com.alura.hotel.factory.Factory;
import com.alura.hotel.modelo.FormasDePago;

public class FormaDePagoController {

	private FormaDePagoDAO formaPagoDao;
	
	public FormaDePagoController() {
		Factory factory = new Factory();
		
		this.formaPagoDao = new FormaDePagoDAO( factory.recuperarConnection());
	}
	
	public List<FormasDePago> listaFormasDePago() {
		return this.formaPagoDao.listarFormasDePago();
	} 
	
	public String buscarNombrePorId(int idFormaPago) {
		return this.formaPagoDao.buscarNombrePorId(idFormaPago);
	}
	
	public int buscarIdPorNombre(String nombre) {
		return this.formaPagoDao.buscarIdPorNombre(nombre);
	}
}

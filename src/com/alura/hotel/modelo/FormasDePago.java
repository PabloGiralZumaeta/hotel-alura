package com.alura.hotel.modelo;

import java.util.Objects;

public class FormasDePago {
	private int id_formaPago;
	private String nombrePago ;
	
	public FormasDePago(int id_categoria, String nombrePago) {
		this.id_formaPago = id_categoria;
		this.nombrePago = nombrePago;
	}

	public int getId_formaPago() {
		return id_formaPago;
	}

	public void setId_formaPago(int id_categoria) {
		this.id_formaPago = id_categoria;
	}

	public String getNombrePago() {
		return nombrePago;
	}

	public void setNombrePago(String nombrePago) {
		this.nombrePago = nombrePago;
	}

	@Override
	public String toString() {
		return this.nombrePago;
	}

	
	
	
	
	
	
}

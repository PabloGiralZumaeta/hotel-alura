package com.alura.hotel.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservas {

	private Long idReserva;
	//Nuevo objeto de fecha -> LocalDate
	private LocalDate fechaE;
	private LocalDate fechaS;
	private BigDecimal valor;
	private int idFormaPago;
	
	public Reservas(LocalDate fechaE, LocalDate fechaS, BigDecimal valor) {
		this.fechaE = fechaE;
		this.fechaS = fechaS;
		this.valor = valor;
	}

	
	
	public Reservas(Long idReserva, LocalDate fechaE, LocalDate fechaS, BigDecimal valor, int idFormaPago) {
		this.idReserva = idReserva;
		this.fechaE = fechaE;
		this.fechaS = fechaS;
		this.valor = valor;
		this.idFormaPago = idFormaPago;
	}



	public Long getIdReserva() {
		return idReserva;
	}

	

	public void setIdReserva(Long idReserva) {
		this.idReserva = idReserva;
	}

	public LocalDate getFechaE() {
		return fechaE;
	}

	public void setFechaE(LocalDate fechaE) {
		this.fechaE = fechaE;
	}

	public LocalDate getFechaS() {
		return fechaS;
	}

	public void setFechaS(LocalDate fechaS) {
		this.fechaS = fechaS;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public int getIdFormaPago() {
		return idFormaPago;
	}

	public void setIdFormaPago(int idFormaPago) {
		this.idFormaPago = idFormaPago;
	}
	
	
	
	
	
}

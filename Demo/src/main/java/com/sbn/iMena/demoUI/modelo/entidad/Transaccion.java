package com.sbn.iMena.demoUI.modelo.entidad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Transaccion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int trsId;

	private String trsNumero;
	@Temporal(TemporalType.DATE)
	private Date trsFecha;
	private String trsTipo;
	private String trsConcepto;
	private Double trsMonto;

	private int trsEstado;

	// generar llaves

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "fkCuentaBancaria")
	private CuentaBancaria fkCuentaBancaria;

	// getter y setters
	public int getTrsId() {
		return trsId;
	}

	public void setTrsId(int trsId) {
		this.trsId = trsId;
	}

	public String getTrsNumero() {
		return trsNumero;
	}

	public void setTrsNumero(String trsNumero) {
		this.trsNumero = trsNumero;
	}

	public Date getTrsFecha() {
		return trsFecha;
	}

	public void setTrsFecha(Date trsFecha) {
		this.trsFecha = trsFecha;
	}

	public String getTrsTipo() {
		return trsTipo;
	}

	public void setTrsTipo(String trsTipo) {
		this.trsTipo = trsTipo;
	}

	public String getTrsConcepto() {
		return trsConcepto;
	}

	public void setTrsConcepto(String trsConcepto) {
		this.trsConcepto = trsConcepto;
	}

	public Double getTrsMonto() {
		return trsMonto;
	}

	public void setTrsMonto(Double trsMonto) {
		this.trsMonto = trsMonto;
	}

	public int getTrsEstado() {
		return trsEstado;
	}

	public void setTrsEstado(int trsEstado) {
		this.trsEstado = trsEstado;
	}

	public CuentaBancaria getFkCuentaBancaria() {
		return fkCuentaBancaria;
	}

	public void setFkCuentaBancaria(CuentaBancaria fkCuentaBancaria) {
		this.fkCuentaBancaria = fkCuentaBancaria;
	}

	@Override
	public String toString() {
		return "Transaccion [trsId=" + trsId + ", trsNumero=" + trsNumero + ", trsFecha=" + trsFecha + ", trsTipo="
				+ trsTipo + ", trsConcepto=" + trsConcepto + ", trsMonto=" + trsMonto + ", trsEstado=" + trsEstado
				+ ", fkCuentaBancaria=" + fkCuentaBancaria + "]";
	}

}

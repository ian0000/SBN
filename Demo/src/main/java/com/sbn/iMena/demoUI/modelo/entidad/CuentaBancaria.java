package com.sbn.iMena.demoUI.modelo.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CuentaBancaria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ctaId;

	private String ctaTipo;
	private String ctaNumero;

	@Temporal(TemporalType.DATE)
	private Date ctaCreacion;

	private int ctaEstado;

	// generar llaves
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "fkCliente")
	private Cliente fkCliente;

	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "fkCuentaBancaria")
	private List<Transaccion> listaTransaccion = new ArrayList<Transaccion>();
	// getter y setters

	public int getCtaId() {
		return ctaId;
	}

	public void setCtaId(int ctaId) {
		this.ctaId = ctaId;
	}

	public String getCtaTipo() {
		return ctaTipo;
	}

	public void setCtaTipo(String ctaTipo) {
		this.ctaTipo = ctaTipo;
	}

	public String getCtaNumero() {
		return ctaNumero;
	}

	public void setCtaNumero(String ctaNumero) {
		this.ctaNumero = ctaNumero;
	}

	public Date getCtaCreacion() {
		return ctaCreacion;
	}

	public void setCtaCreacion(Date ctaCreacion) {
		this.ctaCreacion = ctaCreacion;
	}

	public int getCtaEstado() {
		return ctaEstado;
	}

	public void setCtaEstado(int ctaEstado) {
		this.ctaEstado = ctaEstado;
	}

	public Cliente getFkCliente() {
		return fkCliente;
	}

	public void setFkCliente(Cliente fkCliente) {
		this.fkCliente = fkCliente;
	}

	public List<Transaccion> getListaTransaccion() {
		return listaTransaccion;
	}

	public void setListaTransaccion(List<Transaccion> listaTransaccion) {
		this.listaTransaccion = listaTransaccion;
	}

	@Override
	public String toString() {
		return "CuentaBancaria [ctaId=" + ctaId + ", ctaTipo=" + ctaTipo + ", ctaNumero=" + ctaNumero + ", ctaCreacion="
				+ ctaCreacion + ", ctaEstado=" + ctaEstado + ", fkCliente=" + fkCliente + ", listaTransaccion="
				+ listaTransaccion + "]";
	}

}

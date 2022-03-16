package com.sbn.iMena.demoUI.modelo.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cliId;

	private int cliIdentificacion;
	private String cliNombre;
	private String cliApellido;
	private String cliDireccion;
	private String cliTelefono;
	private String cliCorreo;
	private String cliUser;
	private String cliPass;
	@Temporal(TemporalType.DATE)
	private Date cliCreacion;

	private int cliEstado;

	// generar llaves

	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "fkCliente")
	private List<CuentaBancaria> listaBancaria = new ArrayList<CuentaBancaria>();
	// getter y setters

	public int getCliId() {
		return cliId;
	}

	public void setCliId(int cliId) {
		this.cliId = cliId;
	}

	public int getCliIdentificacion() {
		return cliIdentificacion;
	}

	public void setCliIdentificacion(int cliIdentificacion) {
		this.cliIdentificacion = cliIdentificacion;
	}

	public String getCliNombre() {
		return cliNombre;
	}

	public void setCliNombre(String cliNombre) {
		this.cliNombre = cliNombre;
	}

	public String getCliApellido() {
		return cliApellido;
	}

	public void setCliApellido(String cliApellido) {
		this.cliApellido = cliApellido;
	}

	public String getCliDireccion() {
		return cliDireccion;
	}

	public void setCliDireccion(String cliDireccion) {
		this.cliDireccion = cliDireccion;
	}

	public String getCliTelefono() {
		return cliTelefono;
	}

	public void setCliTelefono(String cliTelefono) {
		this.cliTelefono = cliTelefono;
	}

	public String getCliCorreo() {
		return cliCorreo;
	}

	public void setCliCorreo(String cliCorreo) {
		this.cliCorreo = cliCorreo;
	}

	public String getCliUser() {
		return cliUser;
	}

	public void setCliUser(String cliUser) {
		this.cliUser = cliUser;
	}

	public String getCliPass() {
		return cliPass;
	}

	public void setCliPass(String cliPass) {
		this.cliPass = cliPass;
	}

	public int getCliEstado() {
		return cliEstado;
	}

	public void setCliEstado(int cliEstado) {
		this.cliEstado = cliEstado;
	}

	public List<CuentaBancaria> getListaBancaria() {
		return listaBancaria;
	}

	public void setListaBancaria(List<CuentaBancaria> listaBancaria) {
		this.listaBancaria = listaBancaria;
	}

	public Date getCliCreacion() {
		return cliCreacion;
	}

	public void setCliCreacion(Date cliCreacion) {
		this.cliCreacion = cliCreacion;
	}

	@Override
	public String toString() {
		return "Cliente [cliId=" + cliId + ", cliIdentificacion=" + cliIdentificacion + ", cliNombre=" + cliNombre
				+ ", cliApellido=" + cliApellido + ", cliDireccion=" + cliDireccion + ", cliTelefono=" + cliTelefono
				+ ", cliCorreo=" + cliCorreo + ", cliUser=" + cliUser + ", cliPass=" + cliPass + ", cliCreacion="
				+ cliCreacion + ", cliEstado=" + cliEstado + ", listaBancaria=" + listaBancaria + "]";
	}

}

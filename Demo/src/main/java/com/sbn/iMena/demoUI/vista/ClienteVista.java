package com.sbn.iMena.demoUI.vista;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import com.sbn.iMena.demoUI.controlador.ClienteControlador;
import com.sbn.iMena.demoUI.controlador.CuentaBancariaControlador;
import com.sbn.iMena.demoUI.controlador.TransaccionControlador;
import com.sbn.iMena.demoUI.controlador.impl.ClienteControladorImpl;
import com.sbn.iMena.demoUI.controlador.impl.CuentaBancariaControladorImpl;
import com.sbn.iMena.demoUI.controlador.impl.TransaccionControladorImpl;
import com.sbn.iMena.demoUI.modelo.entidad.Cliente;
import com.sbn.iMena.demoUI.modelo.entidad.CuentaBancaria;
import com.sbn.iMena.demoUI.modelo.entidad.Transaccion;

@ManagedBean
@ViewScoped
public class ClienteVista implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClienteControlador clienteControlador;
	private CuentaBancariaControlador cuentaBancariaControlador;
	private TransaccionControlador transaccionControlador;

	private Cliente cliente;
	private CuentaBancaria cuentaBancaria;
	private CuentaBancaria cuentaSeleccionada;
	private CuentaBancaria datosCuenta;
	private CuentaBancaria cuentaTransferencia;

	private Transaccion datosTransaccion;

	private List<CuentaBancaria> listaCuentaBancaria = new ArrayList<CuentaBancaria>();
	private List<Transaccion> listaTransaccion = new ArrayList<Transaccion>();

	FacesContext context = FacesContext.getCurrentInstance();

	@PostConstruct
	public void init() {
		clienteControlador = new ClienteControladorImpl();
		cuentaBancariaControlador = new CuentaBancariaControladorImpl();
		transaccionControlador = new TransaccionControladorImpl();

		cliente = new Cliente();
		cliente = (Cliente) context.getExternalContext().getSessionMap().get("usuario");
		if (cliente == null) {
			redirigir();
		} else {
			listarCuentas();
		}
		cuentaBancaria = new CuentaBancaria();
		datosCuenta = new CuentaBancaria();
		cuentaTransferencia = new CuentaBancaria();
		datosTransaccion = new Transaccion();
	}

	public void comprobarClave() {
		try {
			if (cliente == null) {
				context.getExternalContext().redirect("./../login.xhtml");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void cerrarSesion() {
		context.getExternalContext().invalidateSession();
		redirigir();
	}

	public void redirigir() {
		try {
			context.getExternalContext().redirect("./../login.xhtml");
		} catch (Exception e) {
		}

	}

	public void listarCuentas() {
		listaCuentaBancaria = cuentaBancariaControlador.listarCuentaBancaria(cliente);
	}

	public void listarTransacciones() {
		listaTransaccion = transaccionControlador.listarTransaccion(cuentaSeleccionada.getCtaId());
		PrimeFaces.current().ajax().update("form:lstTransaccion");
	}

	public void listarDatosCuenta() {
		if (!cuentaTransferencia.getCtaNumero().toUpperCase().equals(cuentaSeleccionada.getCtaNumero())
				&& cuentaTransferencia.getCtaNumero().length() == 7) {
			datosCuenta = cuentaBancariaControlador
					.obtenerDatosCuenta(cuentaTransferencia.getCtaNumero().toUpperCase());
			if(datosCuenta == null) {
				FacesContext.getCurrentInstance().addMessage("confDatos", new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"Aviso", "La cuenta no existe"));
				
			}
		} else {
			FacesContext.getCurrentInstance().addMessage("confDatos", new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"Aviso", "La cuenta no puede ser la misma"));
			
		}
	}

	public void generarTransferencia() {
		System.out.println(datosTransaccion );
		if(datosTransaccion.getTrsConcepto().equals("")) {
			datosTransaccion.setTrsConcepto("");
		}
		try {
			
			if (obtenerPrecio(cuentaSeleccionada.getCtaId()) < datosTransaccion.getTrsMonto() || datosTransaccion.getTrsMonto() ==0) {
				
				FacesContext.getCurrentInstance().addMessage("confDatos",
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "La cuenta no tiene fondos suficientes"));
				
			} else {
				if((cuentaSeleccionada.getCtaTipo().equalsIgnoreCase("Corriente") && datosTransaccion.getTrsMonto() <= 500) || (cuentaSeleccionada.getCtaTipo().equalsIgnoreCase("Ahorros") && datosTransaccion.getTrsMonto() <= 100)) {
					
					//recibe
					cuentaTransferencia = datosCuenta;
					cuentaTransferencia.setFkCliente(datosCuenta.getFkCliente());
					generarTransaccion(cuentaTransferencia,"Déposito");
					// manda
					datosTransaccion.setTrsMonto(-datosTransaccion.getTrsMonto());
					generarTransaccion(cuentaSeleccionada,"Retiro");
					PrimeFaces.current().ajax().update("form:lstTransaccion");
					FacesContext.getCurrentInstance().addMessage("confDatos",
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se ha realizado la transferencia"));
					
				}else {
					if(cuentaSeleccionada.getCtaTipo().equalsIgnoreCase("Corriente")) {
						FacesContext.getCurrentInstance().addMessage("confDatos",
								new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "El monto no puede se mayor a $500"));
						
					}else {
						FacesContext.getCurrentInstance().addMessage("confDatos",
								new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "El monto no puede se mayor a $100"));
						
					}
				}
			}
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("confDatos",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error al realizar la transaccion"));
		}
	}
	
	
	public void generarTransaccion(CuentaBancaria cuenta, String tipo) {

		transaccionControlador = new TransaccionControladorImpl();
		Date fecha = new Date();
		datosTransaccion.setTrsFecha(fecha);
		datosTransaccion.setTrsEstado(1);
		datosTransaccion.setFkCuentaBancaria(cuenta);
		datosTransaccion.setTrsTipo(tipo);
		datosTransaccion.setTrsNumero(transaccionControlador.obtenerNumeroTransaccion(cuenta.getCtaId()));
		transaccionControlador.insertarTransaccion(datosTransaccion);
	}
	

	public Double obtenerPrecio(int cuenta) {
		transaccionControlador = new TransaccionControladorImpl();
		return transaccionControlador.obtenerSaldo(cuenta);
	}
//	****************************

	public ClienteControlador getClienteControlador() {
		return clienteControlador;
	}

	public void setClienteControlador(ClienteControlador clienteControlador) {
		this.clienteControlador = clienteControlador;
	}

	public CuentaBancariaControlador getCuentaBancariaControlador() {
		return cuentaBancariaControlador;
	}

	public void setCuentaBancariaControlador(CuentaBancariaControlador cuentaBancariaControlador) {
		this.cuentaBancariaControlador = cuentaBancariaControlador;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public CuentaBancaria getCuentaBancaria() {
		return cuentaBancaria;
	}

	public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	public List<CuentaBancaria> getListaCuentaBancaria() {
		return listaCuentaBancaria;
	}

	public void setListaCuentaBancaria(List<CuentaBancaria> listaCuentaBancaria) {
		this.listaCuentaBancaria = listaCuentaBancaria;
	}

	public TransaccionControlador getTransaccionControlador() {
		return transaccionControlador;
	}

	public void setTransaccionControlador(TransaccionControlador transaccionControlador) {
		this.transaccionControlador = transaccionControlador;
	}

	public CuentaBancaria getCuentaSeleccionada() {
		return cuentaSeleccionada;
	}

	public void setCuentaSeleccionada(CuentaBancaria cuentaSeleccionada) {
		this.cuentaSeleccionada = cuentaSeleccionada;
	}

	public List<Transaccion> getListaTransaccion() {
		return listaTransaccion;
	}

	public void setListaTransaccion(List<Transaccion> listaTransaccion) {
		this.listaTransaccion = listaTransaccion;
	}

	public CuentaBancaria getDatosCuenta() {
		return datosCuenta;
	}

	public void setDatosCuenta(CuentaBancaria datosCuenta) {
		this.datosCuenta = datosCuenta;
	}

	public CuentaBancaria getCuentaTransferencia() {
		return cuentaTransferencia;
	}

	public void setCuentaTransferencia(CuentaBancaria cuentaTransferencia) {
		this.cuentaTransferencia = cuentaTransferencia;
	}

	public Transaccion getDatosTransaccion() {
		return datosTransaccion;
	}

	public void setDatosTransaccion(Transaccion datosTransaccion) {
		this.datosTransaccion = datosTransaccion;
	}

}

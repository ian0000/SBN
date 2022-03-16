package com.sbn.iMena.demoUI.vista;

import java.io.Serializable;
import java.util.Date;

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
public class AdminVista implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Cliente cliente;
	private Cliente nuevoCliente;
	private CuentaBancaria nuevaCuentaBancaria;

	private CuentaBancaria cuentaTransferencia;
	private CuentaBancaria datosCuenta;

	private ClienteControlador clienteControlador;
	private CuentaBancariaControlador cuentaBancariaControlador;
	private TransaccionControlador transaccionControlador;

	private Transaccion datosTransaccion;
//	Boolean sesion = false;
	FacesContext context = FacesContext.getCurrentInstance();

	@PostConstruct
	public void init() {

		cliente = new Cliente();

		nuevoCliente = new Cliente();
		nuevaCuentaBancaria = new CuentaBancaria();
		datosCuenta = new CuentaBancaria();
		if (context.getExternalContext().getSessionMap().get("admin") == null) {
			redirigir();
		}
		datosTransaccion = new Transaccion();

		cuentaTransferencia = new CuentaBancaria();
		cuentaBancariaControlador = new CuentaBancariaControladorImpl();
		transaccionControlador = new TransaccionControladorImpl();

		clienteControlador = new ClienteControladorImpl();
		cuentaBancariaControlador = new CuentaBancariaControladorImpl();
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

	public void verificarExisteCliente() {
		if (clienteControlador.buscarCliente(nuevoCliente.getCliIdentificacion()) != null) {
			nuevoCliente = clienteControlador.buscarCliente(nuevoCliente.getCliIdentificacion());
			PrimeFaces.current().ajax().update("formAdmin:trsDialog");
		} else {
			nuevoCliente.setCliNombre(null);
			nuevoCliente.setCliApellido(null);
			nuevoCliente.setCliCorreo(null);
			nuevoCliente.setCliDireccion(null);
			nuevoCliente.setCliTelefono(null);
			FacesContext.getCurrentInstance().addMessage("confCliente",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "El cliente no existe "));

		}
	}

	public void crearCliente() {
		clienteControlador = new ClienteControladorImpl();
		nuevoCliente.setCliEstado(1);
		Date fecha = new Date();
		nuevoCliente.setCliCreacion(fecha);
		nuevoCliente.setCliUser(clienteControlador.crearUSuario(nuevoCliente));
		nuevoCliente.setCliPass(clienteControlador.generarPassword());
		clienteControlador.insertarCliente(nuevoCliente);
		FacesContext.getCurrentInstance().addMessage("confCliente",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se a creado el cliente Correctamente"));
	}

	public void crearCuenta() {
		String flag = String.valueOf(nuevoCliente.getCliIdentificacion());
		if (flag.length() < 10) {
			FacesContext.getCurrentInstance().addMessage("confCliente",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "La cedula debe se de almenos 10 digitos"));
		} else {
			if (clienteControlador.buscarCliente(nuevoCliente.getCliIdentificacion()) == null) {
				crearCliente();
			}
			cuentaBancariaControlador = new CuentaBancariaControladorImpl();
			nuevaCuentaBancaria.setCtaNumero(cuentaBancariaControlador.obtenerUltimo(nuevaCuentaBancaria.getCtaTipo()));
			Date fecha = new Date();
			nuevaCuentaBancaria.setCtaCreacion(fecha);
			nuevaCuentaBancaria.setCtaEstado(1);
			nuevaCuentaBancaria.setFkCliente(clienteControlador.buscarCliente(nuevoCliente.getCliIdentificacion()));

			cuentaBancariaControlador.insertarCuentaBancaria(nuevaCuentaBancaria);
			FacesContext.getCurrentInstance().addMessage("confCliente",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se a creado la cuenta Correctamente"));

		}

	}

	public void listarDatosCuenta() {
		if (cuentaTransferencia.getCtaNumero().length() == 7) {
			datosCuenta = cuentaBancariaControlador
					.obtenerDatosCuenta(cuentaTransferencia.getCtaNumero().toUpperCase());
			if (datosCuenta == null) {
				FacesContext.getCurrentInstance().addMessage("trsMensaje",
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "La cuenta no existe"));

			}
		} else {
			FacesContext.getCurrentInstance().addMessage("trsMensaje",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "La cuenta debe ser de 7 digitos"));

		}
	}

	public void generarTransferencia() {
		if (datosTransaccion.getTrsTipo().equals("Retiro")
				&& datosTransaccion.getTrsMonto() > obtenerPrecio(datosCuenta.getCtaId())) {
			FacesContext.getCurrentInstance().addMessage("trsMensaje",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "No hay fondos suficientes"));
			vaciarDatos();
		} else {
			datosTransaccion.setFkCuentaBancaria(datosCuenta);
			if (datosTransaccion.getFkCuentaBancaria().getCtaTipo().equals("Corriente")
					&& datosTransaccion.getTrsMonto() > 500 && datosTransaccion.getTrsTipo().equals("Retiro")
					|| datosTransaccion.getFkCuentaBancaria().getCtaTipo().equals("Ahorros")
							&& datosTransaccion.getTrsMonto() > 100 && datosTransaccion.getTrsTipo().equals("Retiro")) {
				if (datosTransaccion.getFkCuentaBancaria().getCtaTipo().equals("Corriente")) {
					FacesContext.getCurrentInstance().addMessage("trsMensaje", new FacesMessage(
							FacesMessage.SEVERITY_FATAL, "Aviso", "El monto no puede se mayor a $500"));
				} else {
					FacesContext.getCurrentInstance().addMessage("trsMensaje", new FacesMessage(
							FacesMessage.SEVERITY_FATAL, "Aviso", "El monto no puede se mayor a $100"));

				}
				vaciarDatos();

			} else {
				datosTransaccion.setTrsConcepto("CRÉDITO");
				if (datosTransaccion.getTrsTipo().equals("Retiro")) {
					datosTransaccion.setTrsMonto(-datosTransaccion.getTrsMonto());
					datosTransaccion.setTrsConcepto("DÉBITO");
				}
				transaccionControlador = new TransaccionControladorImpl();
				Date fecha = new Date();
				datosTransaccion.setTrsFecha(fecha);
				datosTransaccion.setTrsEstado(1);
				datosTransaccion.setTrsNumero(transaccionControlador.obtenerNumeroTransaccion(datosCuenta.getCtaId()));

				transaccionControlador.insertarTransaccion(datosTransaccion);
				FacesContext.getCurrentInstance().addMessage("trsMensaje",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se ha realizado la transaccion"));
				

				vaciarDatos();
			}
		}
	}

	public void vaciarDatos() {
		datosTransaccion.setTrsMonto(0.00);
	}

	public Double obtenerPrecio(int cuenta) {
		transaccionControlador = new TransaccionControladorImpl();
		return transaccionControlador.obtenerSaldo(cuenta);
	}

//*****************
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getNuevoCliente() {
		return nuevoCliente;
	}

	public void setNuevoCliente(Cliente nuevoCliente) {
		this.nuevoCliente = nuevoCliente;
	}

	public CuentaBancaria getNuevaCuentaBancaria() {
		return nuevaCuentaBancaria;
	}

	public void setNuevaCuentaBancaria(CuentaBancaria nuevaCuentaBancaria) {
		this.nuevaCuentaBancaria = nuevaCuentaBancaria;
	}

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

	public CuentaBancaria getCuentaTransferencia() {
		return cuentaTransferencia;
	}

	public void setCuentaTransferencia(CuentaBancaria cuentaTransferencia) {
		this.cuentaTransferencia = cuentaTransferencia;
	}

	public CuentaBancaria getDatosCuenta() {
		return datosCuenta;
	}

	public void setDatosCuenta(CuentaBancaria datosCuenta) {
		this.datosCuenta = datosCuenta;
	}

	public Transaccion getDatosTransaccion() {
		return datosTransaccion;
	}

	public void setDatosTransaccion(Transaccion datosTransaccion) {
		this.datosTransaccion = datosTransaccion;
	}

	public TransaccionControlador getTransaccionControlador() {
		return transaccionControlador;
	}

	public void setTransaccionControlador(TransaccionControlador transaccionControlador) {
		this.transaccionControlador = transaccionControlador;
	}

}

package com.sbn.iMena.demoUI.vista;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sbn.iMena.demoUI.controlador.ClienteControlador;
import com.sbn.iMena.demoUI.controlador.impl.ClienteControladorImpl;
import com.sbn.iMena.demoUI.modelo.entidad.Cliente;

@ManagedBean
@ViewScoped
public class LoginVista implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ClienteControlador clienteControlador;
	private Cliente cliente;

	@PostConstruct
	public void init() {
		clienteControlador = new ClienteControladorImpl();
		cliente = new Cliente();
	}

	public String comprobarClave() {
		Cliente cli = null;
		String redirigir = null;
		if(cliente.getCliPass().equals("Admin1") && cliente.getCliUser().equals("Test1")) {
			redirigir ="/protegido/admin.xhtml?faces-redirect=true";
			Boolean sesion = true;
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("admin", sesion);
		}else {		
			try {
				cli = clienteControlador.comprobarAcceso(cliente);
				if (cli != null) {

					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", cli);

					redirigir = "/protegido/cuenta.xhtml?faces-redirect=true";

				} else {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Credenciales Incorrectas"));
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Credenciales Incorrectas"));
			}
		}
		return redirigir;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}

package com.sbn.iMena.demoUI.controlador.impl;

import java.util.List;

import com.sbn.iMena.demoUI.controlador.ClienteControlador;
import com.sbn.iMena.demoUI.modelo.dao.ClienteDao;
import com.sbn.iMena.demoUI.modelo.dao.impl.ClienteDaoImpl;
import com.sbn.iMena.demoUI.modelo.entidad.Cliente;

public class ClienteControladorImpl implements ClienteControlador{

	public ClienteDao clienteDao;
	@Override
	public void insertarCliente(Cliente nuevoCliente) {
			clienteDao = new ClienteDaoImpl();
			clienteDao.insertarCliente(nuevoCliente);
	}

	@Override
	public void actualizarCliente(Cliente actualizarCliente) {
		clienteDao = new ClienteDaoImpl();
		clienteDao.actualizarCliente(actualizarCliente);
	}

	@Override
	public List<Cliente> listarClientes() {
		clienteDao = new ClienteDaoImpl();
		return clienteDao.listarClientes();
	}

	@Override
	public Cliente buscarCliente(int cliId) {
		clienteDao = new ClienteDaoImpl();
		return clienteDao.buscarCliente(cliId);
	}

	@Override
	public String crearUSuario(Cliente nuevoCliente) {
		clienteDao = new ClienteDaoImpl();
		return clienteDao.crearUSuario(nuevoCliente);
	}

	@Override
	public String generarPassword() {
		clienteDao = new ClienteDaoImpl();
		return clienteDao.generarPassword();
	}

	@Override
	public Cliente comprobarAcceso(Cliente comprobarCliente) {
		clienteDao = new ClienteDaoImpl();
		return clienteDao.comprobarAcceso(comprobarCliente);
	}
	
}

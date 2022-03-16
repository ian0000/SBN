package com.sbn.iMena.demoUI.modelo.dao;

import java.util.List;

import com.sbn.iMena.demoUI.modelo.entidad.Cliente;

public interface ClienteDao {
	public void insertarCliente(Cliente nuevoCliente);

	public void actualizarCliente(Cliente actualizarCliente);

	public List<Cliente> listarClientes();

	public Cliente buscarCliente(int cliId);

	public String crearUSuario(Cliente nuevoCliente);

	public String generarPassword();

	public Cliente comprobarAcceso(Cliente comprobarCliente);
}

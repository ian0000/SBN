package com.sbn.iMena.demoUI.modelo.dao.impl;

import java.text.Normalizer;
import java.util.List;
import java.util.Random;

import javax.persistence.TypedQuery;

import com.sbn.iMena.demoUI.modelo.dao.ClienteDao;
import com.sbn.iMena.demoUI.modelo.entidad.Cliente;

public class ClienteDaoImpl extends GenericaDaoImpl<Cliente> implements ClienteDao {

	@Override
	public void insertarCliente(Cliente nuevoCliente) {
		try {
			this.beginTransaction();
			this.create(nuevoCliente);
			this.commit();
			this.closeTransaction();
		} catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public void actualizarCliente(Cliente actualizarCliente) {
		try {
			this.beginTransaction();
			this.update(actualizarCliente);
			this.commit();
			this.closeTransaction();
		} catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public List<Cliente> listarClientes() {
		TypedQuery<Cliente> sqlCliente = this.entityManager
				.createQuery("SELECT cli FROM Cliente cli WHERE cli.cliEstado != 0", Cliente.class);
		return sqlCliente.getResultList();
	}

	@Override
	public Cliente buscarCliente(int cliId) {
		TypedQuery<Cliente> sqlCliente = this.entityManager
				.createQuery("SELECT cli FROM Cliente cli WHERE cli.cliIdentificacion = "+ cliId, Cliente.class);
		try {
			
			return sqlCliente.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public String crearUSuario(Cliente nuevoCliente) {
		String apellido = nuevoCliente.getCliApellido();
		String nombre = nuevoCliente.getCliNombre();
		
		apellido = retirarAcentos(apellido).toUpperCase();
		nombre = retirarAcentos(nombre).toUpperCase();
		return nombre.charAt(0) +  apellido;
	}
	
	@Override
	public Cliente comprobarAcceso(Cliente comprobarCliente) {
		Cliente cliente =null;
		TypedQuery<Cliente> sqlCliente = this.entityManager.createQuery("SELECT cli FROM Cliente cli WHERE cli.cliUser = '"+comprobarCliente.getCliUser()+"'", Cliente.class);
		List<Cliente>lista=sqlCliente.getResultList();
		if(!lista.isEmpty()) {
			cliente = lista.get(0);
		}
		return cliente;
	}
	
	public String retirarAcentos(String texto) {
		texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
		texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return texto;
	}
	
	public String generarPassword() {
		int length = 8;
        String cap_letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        String small_letter = "abcdefghijklmnopqrstuvwxyz"; 
        String numbers = "0123456789"; 


        String finalString = cap_letter + small_letter + 
                numbers; 

        Random random = new Random(); 

        char[] password = new char[length]; 

        for (int i = 0; i < length; i++) 
        { 
            password[i] = 
                    finalString.charAt(random.nextInt(finalString.length())); 

        }
        String pass = String.valueOf(password);
		return pass;
	}
	
	

}

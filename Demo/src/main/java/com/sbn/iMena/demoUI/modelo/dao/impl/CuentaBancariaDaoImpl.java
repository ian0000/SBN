package com.sbn.iMena.demoUI.modelo.dao.impl;

import java.text.DecimalFormat;
import java.util.List;

import javax.persistence.TypedQuery;

import com.sbn.iMena.demoUI.modelo.dao.CuentaBancariaDao;
import com.sbn.iMena.demoUI.modelo.entidad.Cliente;
import com.sbn.iMena.demoUI.modelo.entidad.CuentaBancaria;

public class CuentaBancariaDaoImpl extends GenericaDaoImpl<CuentaBancaria> implements CuentaBancariaDao{

	@Override
	public void insertarCuentaBancaria(CuentaBancaria nuevaCuentaBancaria) {
		try {
			this.beginTransaction();
			this.create(nuevaCuentaBancaria);
			this.commit();
			this.closeTransaction();
		} catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public void actualizarCuentaBancaria(CuentaBancaria actualizarCuentaBancaria) {
		try {
			this.beginTransaction();
			this.update(actualizarCuentaBancaria);
			this.commit();
			this.closeTransaction();
		} catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public List<CuentaBancaria> listarCuentaBancaria(Cliente buscarCliente) {
		TypedQuery<CuentaBancaria> sqlCuenta = this.entityManager.createQuery("SELECT cta FROM CuentaBancaria cta WHERE cta.ctaEstado !=0 AND cta.fkCliente.cliIdentificacion = "+buscarCliente.getCliIdentificacion(), CuentaBancaria.class);
		return sqlCuenta.getResultList();
	}

	@Override
	public CuentaBancaria buscarCuentaBancaria(int ctaId) {
		return this.readClase(ctaId, CuentaBancaria.class);
	}
	
	@Override
	public CuentaBancaria obtenerDatosCuenta(String buscarCuenta) {
		TypedQuery<CuentaBancaria> sqlCuenta = this.entityManager.createQuery("SELECT cta FROM CuentaBancaria cta WHERE cta.ctaNumero= '"+buscarCuenta+"'", CuentaBancaria.class);
		try {
			
			return sqlCuenta.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override 
	public String obtenerUltimo(String tipo) {
		String query = "SELECT cta.ctaNumero FROM CuentaBancaria cta  WHERE cta.ctaTipo = '"+tipo+"' order by cta.ctaId desc";
		TypedQuery<CuentaBancaria> sqlCuenta = this.entityManager.createQuery(query, CuentaBancaria.class);
		String numCuenta = "";
		numCuenta = sqlCuenta.setMaxResults(1).getResultList().toString();
		System.err.println(numCuenta + tipo);
		String resultado = "";
		if(tipo.equals("Corriente")) {
			resultado = numeroCuenta("CC00000", convertirAEntero(numCuenta, "CC"));
		}else if(tipo.equals("Ahorros")) {
			resultado = numeroCuenta("CA00000", convertirAEntero(numCuenta, "CA"));
		}

		return resultado;
	}
	
	public int convertirAEntero(String texto, String tipo) {
		String flag = "";
		if(texto.length() == 2) return 1;
		for (String splitStart : texto.split(tipo)) {
			for(String splitEnd : splitStart.split("]")) {
				if(splitEnd.length() > 4) {					
					flag = splitEnd;
				}
			}
		}
		int resultado = Integer.parseInt(flag);
		return resultado + 1;
	}
	
	public String numeroCuenta(String patron, double valor) {
		DecimalFormat formato= new DecimalFormat(patron);
	      String resultado = formato.format(valor);
	      return resultado;
	   }

}

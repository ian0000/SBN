package com.sbn.iMena.demoUI.modelo.dao.impl;

import java.text.DecimalFormat;
import java.util.List;

import javax.persistence.TypedQuery;

import com.sbn.iMena.demoUI.modelo.dao.TransaccionDao;
import com.sbn.iMena.demoUI.modelo.entidad.Transaccion;

public class TransaccionDaoImpl extends GenericaDaoImpl<Transaccion> implements TransaccionDao {

	@Override
	public void insertarTransaccion(Transaccion nuevoTransaccion) {
		try {
			this.beginTransaction();
			this.create(nuevoTransaccion);
			this.commit();
			this.closeTransaction();
		} catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public void actualizarTransaccion(Transaccion actualizarTransaccion) {
		try {
			this.beginTransaction();
			this.update(actualizarTransaccion);
			this.commit();
			this.closeTransaction();
		} catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public List<Transaccion> listarTransaccion(int cuentaNumero) {
		TypedQuery<Transaccion> sqlTransaccion = this.entityManager
				.createQuery("SELECT trs FROM Transaccion trs WHERE trs.fkCuentaBancaria.ctaId= '"+cuentaNumero+"' order by trs.trsId desc", Transaccion.class);
		return sqlTransaccion.getResultList();
	}

	@Override
	public Transaccion buscarTransaccion(int trsId) {
		return this.readClase(trsId, Transaccion.class);
	}
	
	
	@Override
	public String obtenerNumeroTransaccion(int cuenta) {
		
		String query = "SELECT trs.trsNumero FROM Transaccion trs WHERE trs.fkCuentaBancaria.ctaId = "+cuenta+" order by trs.trsId desc";
		TypedQuery<Transaccion> sqlCuenta = this.entityManager.createQuery(query, Transaccion.class);
		String numCuenta =sqlCuenta.setMaxResults(1).getResultList().toString();
		return numeroTransaccion("000000000", convertirAEntero(numCuenta));
		
	}
	
	@Override
	public Double obtenerSaldo(int cuenta) {
		double resultado = obtenerSumaTipo(cuenta, "Déposito") + obtenerSumaTipo(cuenta, "Retiro");
		return Double.parseDouble(numeroTransaccion("#.00", resultado));
	}
	
	public Double obtenerSumaTipo(int cuenta, String tipo) {
		String query ="SELECT SUM(trs.trsMonto) FROM Transaccion trs WHERE trs.fkCuentaBancaria.ctaId = "+cuenta +" AND trs.trsTipo = '"+tipo+"'";
		TypedQuery<Double> sqlDepositos= this.entityManager.createQuery(query, Double.class);
		if(sqlDepositos.getSingleResult() != null) {
			return sqlDepositos.getSingleResult();
		}
		return 00.00;
	}

	public int convertirAEntero(String texto) {
		if(texto.length() == 2) return 1;
		texto =  texto.substring(0,0)+texto.substring(1);
		for(String splitEnd : texto.split("]")) {
			if(splitEnd.length() > 4) {					
				texto = splitEnd;
			}
		}
		int resultado = Integer.parseInt(texto);
		return resultado+1;
	}
	
	public String numeroTransaccion(String patron, double valor) {
		DecimalFormat formato= new DecimalFormat(patron);
	      String resultado = formato.format(valor);
	      return resultado;
	   }
}

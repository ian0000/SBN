package com.sbn.iMena.demoUI.controlador.impl;

import java.util.List;

import com.sbn.iMena.demoUI.controlador.TransaccionControlador;
import com.sbn.iMena.demoUI.modelo.dao.TransaccionDao;
import com.sbn.iMena.demoUI.modelo.dao.impl.TransaccionDaoImpl;
import com.sbn.iMena.demoUI.modelo.entidad.Transaccion;

public class TransaccionControladorImpl implements TransaccionControlador {

	public TransaccionDao transaccionDao;

	@Override
	public void insertarTransaccion(Transaccion nuevoTransaccion) {
		transaccionDao = new TransaccionDaoImpl();
		transaccionDao.insertarTransaccion(nuevoTransaccion);
	}

	@Override
	public void actualizarTransaccion(Transaccion actualizarTransaccion) {
		transaccionDao = new TransaccionDaoImpl();
		transaccionDao.actualizarTransaccion(actualizarTransaccion);
	}

	@Override
	public List<Transaccion> listarTransaccion(int cuentaNumero) {
		transaccionDao = new TransaccionDaoImpl();
		return transaccionDao.listarTransaccion(cuentaNumero);
	}

	@Override
	public Transaccion buscarTransaccion(int trsId) {
		transaccionDao = new TransaccionDaoImpl();
		return transaccionDao.buscarTransaccion(trsId);
	}

	@Override
	public String obtenerNumeroTransaccion(int cuenta) {
		transaccionDao = new TransaccionDaoImpl();
		return transaccionDao.obtenerNumeroTransaccion(cuenta);
	}

	@Override
	public Double obtenerSaldo(int cuenta) {
		transaccionDao = new TransaccionDaoImpl();
		return transaccionDao.obtenerSaldo(cuenta);
	}

	@Override
	public Double obtenerSumaTipo(int cuenta, String tipo) {
		transaccionDao = new TransaccionDaoImpl();
		return transaccionDao.obtenerSumaTipo(cuenta, tipo);
	}

}

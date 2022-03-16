package com.sbn.iMena.demoUI.controlador.impl;

import java.util.List;

import com.sbn.iMena.demoUI.controlador.CuentaBancariaControlador;
import com.sbn.iMena.demoUI.modelo.dao.CuentaBancariaDao;
import com.sbn.iMena.demoUI.modelo.dao.impl.CuentaBancariaDaoImpl;
import com.sbn.iMena.demoUI.modelo.entidad.Cliente;
import com.sbn.iMena.demoUI.modelo.entidad.CuentaBancaria;

public class CuentaBancariaControladorImpl implements CuentaBancariaControlador{

	public CuentaBancariaDao cuentaBancariaDao;
	@Override
	public void insertarCuentaBancaria(CuentaBancaria nuevaCuentaBancaria) {
		cuentaBancariaDao = new CuentaBancariaDaoImpl();
		cuentaBancariaDao.insertarCuentaBancaria(nuevaCuentaBancaria);
	}

	@Override
	public void actualizarCuentaBancaria(CuentaBancaria actualizarCuentaBancaria) {
		cuentaBancariaDao = new CuentaBancariaDaoImpl();
		cuentaBancariaDao.actualizarCuentaBancaria(actualizarCuentaBancaria);
	}

	@Override
	public List<CuentaBancaria> listarCuentaBancaria(Cliente buscarCliente) {
		cuentaBancariaDao = new CuentaBancariaDaoImpl();
		return cuentaBancariaDao.listarCuentaBancaria(buscarCliente);
	}

	@Override
	public CuentaBancaria buscarCuentaBancaria(int ctaId) {
		cuentaBancariaDao = new CuentaBancariaDaoImpl();
		return cuentaBancariaDao.buscarCuentaBancaria(ctaId);
	}

	@Override
	public String obtenerUltimo(String tipo) {
		cuentaBancariaDao = new CuentaBancariaDaoImpl();
		return cuentaBancariaDao.obtenerUltimo(tipo);
	}

	@Override
	public CuentaBancaria obtenerDatosCuenta(String buscarCuenta) {
		cuentaBancariaDao = new CuentaBancariaDaoImpl();
		return cuentaBancariaDao.obtenerDatosCuenta(buscarCuenta);
	}
	

}

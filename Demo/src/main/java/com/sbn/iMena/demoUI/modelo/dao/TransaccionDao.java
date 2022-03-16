package com.sbn.iMena.demoUI.modelo.dao;

import java.util.List;

import com.sbn.iMena.demoUI.modelo.entidad.Transaccion;

public interface TransaccionDao {

	public void insertarTransaccion(Transaccion nuevoTransaccion);

	public void actualizarTransaccion(Transaccion actualizarTransaccion);

	public List<Transaccion> listarTransaccion(int cuentaNumero);

	public Transaccion buscarTransaccion(int trsId);

	public String obtenerNumeroTransaccion(int cuenta);

	public Double obtenerSaldo(int cuenta);

	public Double obtenerSumaTipo(int cuenta, String tipo);
}

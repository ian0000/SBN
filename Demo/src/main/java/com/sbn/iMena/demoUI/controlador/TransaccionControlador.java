package com.sbn.iMena.demoUI.controlador;

import java.util.List;

import com.sbn.iMena.demoUI.modelo.entidad.Transaccion;

public interface TransaccionControlador {
	public void insertarTransaccion(Transaccion nuevoTransaccion);

	public void actualizarTransaccion(Transaccion actualizarTransaccion);

	public List<Transaccion> listarTransaccion(int cuentaNumero);

	public Transaccion buscarTransaccion(int trsId);

	public String obtenerNumeroTransaccion(int cuenta);

	public Double obtenerSaldo(int cuenta);
	
	public Double obtenerSumaTipo(int cuenta, String tipo);
}

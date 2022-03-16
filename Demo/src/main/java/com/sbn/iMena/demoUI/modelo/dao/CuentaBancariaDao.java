package com.sbn.iMena.demoUI.modelo.dao;

import java.util.List;

import com.sbn.iMena.demoUI.modelo.entidad.Cliente;
import com.sbn.iMena.demoUI.modelo.entidad.CuentaBancaria;

public interface CuentaBancariaDao {

	public void insertarCuentaBancaria(CuentaBancaria nuevaCuentaBancaria);

	public void actualizarCuentaBancaria(CuentaBancaria actualizarCuentaBancaria);

	public List<CuentaBancaria> listarCuentaBancaria(Cliente buscarCliente);

	public CuentaBancaria buscarCuentaBancaria(int ctaId);

	public String obtenerUltimo(String tipo);

	public CuentaBancaria obtenerDatosCuenta(String buscarCuenta);
}

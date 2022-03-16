package demo;

import java.util.Date;

import org.junit.Test;

import com.sbn.iMena.demoUI.controlador.ClienteControlador;
import com.sbn.iMena.demoUI.controlador.CuentaBancariaControlador;
import com.sbn.iMena.demoUI.controlador.TransaccionControlador;
import com.sbn.iMena.demoUI.controlador.impl.ClienteControladorImpl;
import com.sbn.iMena.demoUI.controlador.impl.CuentaBancariaControladorImpl;
import com.sbn.iMena.demoUI.controlador.impl.TransaccionControladorImpl;
import com.sbn.iMena.demoUI.modelo.entidad.Cliente;
import com.sbn.iMena.demoUI.modelo.entidad.CuentaBancaria;
import com.sbn.iMena.demoUI.modelo.entidad.Transaccion;

public class DemoTest {

	public ClienteControlador clienteControlador;
	public CuentaBancariaControlador cuentaBancariaControlador;
	public TransaccionControlador transaccionControlador;

	@Test
	public void crearCliente() {
		Cliente nuevoCliente = new Cliente();
		nuevoCliente.setCliIdentificacion(1718644048);
		nuevoCliente.setCliNombre("Ian");
		nuevoCliente.setCliApellido("Mena");
		nuevoCliente.setCliCorreo("ianmenaamagua@gmail.com");
		nuevoCliente.setCliDireccion("Dir1");
		nuevoCliente.setCliTelefono("0996521428");
		nuevoCliente.setCliEstado(1);

		Date date = new Date();
		nuevoCliente.setCliCreacion(date);

		clienteControlador = new ClienteControladorImpl();
		nuevoCliente.setCliUser(clienteControlador.crearUSuario(nuevoCliente));
		nuevoCliente.setCliPass(clienteControlador.generarPassword());

		clienteControlador.insertarCliente(nuevoCliente);
	}

	@Test
	public void crearCuentaBancariaCorriente() {
		CuentaBancaria nuevoCuentaBancaria = new CuentaBancaria();
		cuentaBancariaControlador = new CuentaBancariaControladorImpl();

		nuevoCuentaBancaria.setCtaTipo("Corriente");
		nuevoCuentaBancaria.setCtaNumero(cuentaBancariaControlador.obtenerUltimo(nuevoCuentaBancaria.getCtaTipo()));

		Cliente nuevoCliente = new Cliente();
		nuevoCliente.setCliId(1);
		nuevoCuentaBancaria.setFkCliente(nuevoCliente);

		Date date = new Date();
		nuevoCuentaBancaria.setCtaCreacion(date);
		nuevoCuentaBancaria.setCtaEstado(1);

		cuentaBancariaControlador.insertarCuentaBancaria(nuevoCuentaBancaria);
	}

	@Test
	public void crearCuentaBancariaAhorros() {
		CuentaBancaria nuevoCuentaBancaria = new CuentaBancaria();
		cuentaBancariaControlador = new CuentaBancariaControladorImpl();

		nuevoCuentaBancaria.setCtaTipo("Ahorros");
		nuevoCuentaBancaria.setCtaNumero(cuentaBancariaControlador.obtenerUltimo(nuevoCuentaBancaria.getCtaTipo()));

		Cliente nuevoCliente = new Cliente();
		nuevoCliente.setCliId(1);
		nuevoCuentaBancaria.setFkCliente(nuevoCliente);

		Date date = new Date();
		nuevoCuentaBancaria.setCtaCreacion(date);
		nuevoCuentaBancaria.setCtaEstado(1);

		cuentaBancariaControlador.insertarCuentaBancaria(nuevoCuentaBancaria);
	}

	@Test
	public void obtenerUltimoTest() {
		cuentaBancariaControlador = new CuentaBancariaControladorImpl();
		System.out.println(cuentaBancariaControlador.obtenerUltimo("Corriente"));
	}

	@Test
	public void crearTransaccionDeposito() {
		Transaccion nuevaTransaccion = new Transaccion();
		transaccionControlador = new TransaccionControladorImpl();

		CuentaBancaria nuevoCuentaBancaria = new CuentaBancaria();
		nuevoCuentaBancaria.setCtaId(1);

		Date date = new Date();
		nuevaTransaccion.setTrsFecha(date);
		nuevaTransaccion.setTrsMonto(222.22);
		nuevaTransaccion.setTrsNumero(transaccionControlador.obtenerNumeroTransaccion(nuevoCuentaBancaria.getCtaId()));
		nuevaTransaccion.setTrsEstado(1);
		nuevaTransaccion.setTrsConcepto("CRÉDITO");
		nuevaTransaccion.setTrsTipo("Déposito");

		nuevaTransaccion.setFkCuentaBancaria(nuevoCuentaBancaria);

		transaccionControlador.insertarTransaccion(nuevaTransaccion);
	}

	@Test
	public void crearTransaccionRetiro() {
		Transaccion nuevaTransaccion = new Transaccion();
		transaccionControlador = new TransaccionControladorImpl();

		CuentaBancaria nuevoCuentaBancaria = new CuentaBancaria();
		nuevoCuentaBancaria.setCtaId(1);

		Date date = new Date();
		nuevaTransaccion.setTrsFecha(date);
		nuevaTransaccion.setTrsMonto(-22.22);
		if (nuevaTransaccion.getTrsMonto() > transaccionControlador.obtenerSaldo(nuevoCuentaBancaria.getCtaId())) {
			System.err.println("No hay fondos suficientes para realizar la operacion");
		} else {
			nuevaTransaccion
					.setTrsNumero(transaccionControlador.obtenerNumeroTransaccion(nuevoCuentaBancaria.getCtaId()));
			nuevaTransaccion.setTrsEstado(1);
			nuevaTransaccion.setTrsConcepto("DÉBITO");
			nuevaTransaccion.setTrsTipo("Retiro");

			nuevaTransaccion.setFkCuentaBancaria(nuevoCuentaBancaria);

			transaccionControlador.insertarTransaccion(nuevaTransaccion);
		}

	}

	@Test
	public void test() {
		clienteControlador = new ClienteControladorImpl();
		System.out.println(clienteControlador.buscarCliente(14444));

	}

}

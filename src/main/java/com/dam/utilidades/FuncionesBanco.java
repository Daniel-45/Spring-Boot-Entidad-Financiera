package com.dam.utilidades;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.dam.modelos.Cliente;
import com.dam.modelos.Contacto;
import com.dam.modelos.Cuenta;
import com.dam.modelos.CuentaEmpresa;
import com.dam.modelos.CuentaPersonal;
import com.dam.modelos.Riesgo;
import com.dam.modelos.Tipo;

import daw.com.Teclado;

/**
 * 
 * @author Daniel
 *
 */
public class FuncionesBanco {

	final static String URLBASE = "http://localhost:8080/banco";

	/**
	 * Método para devolver una cuenta
	 * 
	 * @param ncc
	 * @return cuenta
	 */
	public static Cuenta getCuenta(String ncc) {
		final String URL = URLBASE + "cuentas/consultar/{ncc}";
		Cuenta cuenta;
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<Cuenta> response = restTemplate.getForEntity(URL, Cuenta.class);
			cuenta = response.getBody();
		} catch (HttpClientErrorException e) {
			cuenta = null;
		}
		return cuenta;
	}

	/**
	 * Método para devolver un cliente
	 * 
	 * @param nif
	 * @return cliente
	 */
	public static Cliente getCliente(String nif) {
		final String URL = URLBASE + "clientes/consultar/{nif}";
		Cliente cliente;
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<Cliente> response = restTemplate.getForEntity(URL, Cliente.class);
			cliente = response.getBody();
		} catch (HttpClientErrorException e) {
			cliente = null;
		}
		return cliente;
	}

	/**
	 * Método para insertar una cuenta
	 */
	public static void insertarCuenta() {

		final String URLCUENTA = URLBASE + "/cuentas/insertar";
		final String URLCLIENTE = URLBASE + "/clientes/insertar";
		Cuenta cuenta;
		int tipo;
		String ncc, nif, seguir;
		Set<Cliente> clientes = new HashSet<Cliente>();
		Set<Contacto> telefonos = new HashSet<Contacto>();
		Set<Cuenta> cuentas = new HashSet<Cuenta>();
		RestTemplate restTemplateCuenta = new RestTemplate();
		RestTemplate restTemplateCliente = new RestTemplate();

		do {
			tipo = Libreria.leerEntre(1, 2, "\nSelecciona el tipo de cuenta: 1.Personal - 2.Empresa");

			if (tipo == 1) {
				cuenta = new CuentaPersonal();
			} else {
				cuenta = new CuentaEmpresa();
			}

			ncc = Teclado.leerString("\nNúmero de cuenta:");
			
			cuenta.setNcc(ncc);

			cuenta.setSaldo(Teclado.leerFloat("\nSaldo:"));

			if (cuenta instanceof CuentaPersonal) {

				String respuesta = Teclado.leerString("\n¿Tiene tarjeta de crédito? S/N:");

				do {

					if (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N")) {

						System.out.println("\nERROR!! Respuesta no válida.");

						System.out.println("\nPor favor introduce S/N");

						respuesta = Teclado.leerString("\n¿Tiene tarjeta de crédito? S/N:");
					}

					if (respuesta.equalsIgnoreCase("S")) {

						((CuentaPersonal) cuenta).setCredito(true);
					} else {

						((CuentaPersonal) cuenta).setCredito(false);
					}

				} while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));
			} else if (cuenta instanceof CuentaEmpresa) {

				((CuentaEmpresa) cuenta).setEmpresa(Teclado.leerString("\nEmpresa:"));

				((CuentaEmpresa) cuenta).setCif(Teclado.leerString("\nCIF:"));

				int opcionLocal;

				opcionLocal = Libreria.leerEntre(1, 2, "\nSelecciona el tipo de local: 1.Propio - 2.Alquilado");

				switch (opcionLocal) {
				case 1:
					((CuentaEmpresa) cuenta).setLocal(Tipo.PROPIO);
					break;
				case 2:
					((CuentaEmpresa) cuenta).setLocal(Tipo.ALQUILADO);
				default:
					break;
				}
			}

			do {

				nif = Teclado.leerString("\nDNI cliente:");

				Cliente cliente = new Cliente(nif);

				cliente.setNombre(Teclado.leerString("\nNombre cliente:"));

				cliente.setApellidos(Teclado.leerString("\nApellidos cliente:"));

				cliente.setAval(Teclado.leerFloat("\nAval cliente:"));

				do {
					Contacto telefono = new Contacto();

					telefono.setNumero(Teclado.leerString("\nIntroduce el número de teléfono:"));

					telefono.setProveedor(Teclado.leerString("\nIntroduce la compañía de teléfono:"));

					telefonos.add(telefono);

					seguir = Teclado.leerString("\n¿Quieres insertar más teléfonos? S/N:");

					if (!seguir.equalsIgnoreCase("S") && !seguir.equalsIgnoreCase("N")) {

						System.out.println("\nERROR!! Respuesta no válida.");

						System.out.println("\nPor favor introduce S/N");

						seguir = Teclado.leerString("\n¿Quieres insertar más teléfonos? S/N:");

					}

					cliente.setTelefonos(telefonos);

				} while (seguir.equalsIgnoreCase("S"));

				int opcionRiesgo = Libreria.leerEntre(1, 3, "\nSelecciona el riesgo: 1.Alto - 2.Medio - 3.Bajo");

				switch (opcionRiesgo) {
				case 1:
					cliente.setRiesgo(Riesgo.ALTO);
					break;
				case 2:
					cliente.setRiesgo(Riesgo.MEDIO);
					break;
				case 3:
					cliente.setRiesgo(Riesgo.BAJO);
				default:
					break;
				}

				try {

					cliente = restTemplateCliente.postForObject(URLCLIENTE, cliente, Cliente.class);

					System.out.println("\nCliente insertado correctamente.");

					cliente.mostrarDatos();

				} catch (HttpClientErrorException e) {

					System.out.println("\nERROR!! Ya existe un cliente con ese DNI");
				}

				clientes.add(cliente);

				seguir = Teclado.leerString("\n¿Quieres insertar más clientes a la cuenta? S/N:");

				if (!seguir.equalsIgnoreCase("S") && !seguir.equalsIgnoreCase("N")) {

					System.out.println("\nERROR!! Respuesta no válida.");

					System.out.println("\nPor favor introduce S/N");

					seguir = Teclado.leerString("\n¿Quieres insertar más clientes a la cuenta? S/N:");
				}

			} while (seguir.equalsIgnoreCase("S"));

			cuenta.setClientes(clientes);

			try {

				if (cuenta instanceof CuentaPersonal) {

					cuenta = restTemplateCuenta.postForObject(URLCUENTA, cuenta, Cuenta.class);

					System.out.println("\nLa cuenta se ha insertado correctamente.");

					((CuentaPersonal) cuenta).mostrarDatos();
				}

				if (cuenta instanceof CuentaEmpresa) {

					cuenta = restTemplateCuenta.postForObject(URLCUENTA, cuenta, Cuenta.class);

					System.out.println("\nLa cuenta se ha insertado correctamente.");

					((CuentaEmpresa) cuenta).mostrarDatos();
				}

			} catch (HttpClientErrorException e) {
				System.out.println("\nERROR!! " + e.getMessage());
			}

			cuentas.add(cuenta);

			seguir = Teclado.leerString("\n¿Quieres insertar más cuentas? S/N:");

			if (!seguir.equalsIgnoreCase("S") && !seguir.equalsIgnoreCase("N")) {

				System.out.println("\nERROR!! Respuesta no válida.");

				System.out.println("\nPor favor introduce S/N");

				seguir = Teclado.leerString("\n¿Quieres insertar más cuentas? S/N:");
			}

		} while (seguir.equalsIgnoreCase("S"));
	}

	/**
	 * Método para actualizar una cuenta
	 */
	public static void actualizarCuenta() {

		final String URLCONSULTAR = URLBASE + "/cuentas/consultar/{ncc}";

		final String URLMODIFICAR = URLBASE + "/cuentas/modificar";

		RestTemplate restTemplate = new RestTemplate();

		String ncc = Teclado.leerString("\nNúmero de cuenta a actualizar:");

		Cuenta cuenta;

		try {

			ResponseEntity<Cuenta> response = restTemplate.getForEntity(URLCONSULTAR, Cuenta.class, ncc);

			cuenta = response.getBody();

			System.out.println("\n------------ DATOS ACTUALES DE LA CUENTA ------------");

			cuenta.mostrarDatos();

			System.out.println("\n------------- NUEVOS DATOS DE LA CUENTA -------------");

			cuenta.setSaldo(Teclado.leerFloat("\nIntroduce el nuevo saldo:"));

			if (cuenta instanceof CuentaPersonal) {

				String respuesta = Teclado.leerString("\n¿Tiene tarjeta de crédito? S/N:");

				do {

					if (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N")) {

						System.out.println("\nERROR!! Respuesta no válida.");

						System.out.println("\nPor favor introduce S/N");

						respuesta = Teclado.leerString("\n¿Tiene tarjeta de crédito? S/N:");
					}

					if (respuesta.equalsIgnoreCase("S")) {

						((CuentaPersonal) cuenta).setCredito(true);
					} else {

						((CuentaPersonal) cuenta).setCredito(false);
					}

				} while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));

			} else if (cuenta instanceof CuentaEmpresa) {

				((CuentaEmpresa) cuenta).setEmpresa(Teclado.leerString("\nEmpresa:"));

				((CuentaEmpresa) cuenta).setCif(Teclado.leerString("\nCIF:"));

				int opcionLocal;

				opcionLocal = Libreria.leerEntre(1, 2, "\nSelecciona el tipo de local: 1.Propio - 2.Alquilado");

				switch (opcionLocal) {
				case 1:
					((CuentaEmpresa) cuenta).setLocal(Tipo.PROPIO);
					break;
				case 2:
					((CuentaEmpresa) cuenta).setLocal(Tipo.ALQUILADO);
				default:
					break;
				}
			}

			restTemplate.put(URLMODIFICAR, cuenta);

			System.out.println("\nRegistro actualizado correctamente.");

		} catch (HttpClientErrorException e) {
			System.out.println("\nEl número de cuenta introducido no está en la base de datos.");
		}
	}

	/**
	 * Método para eliminar una cuenta
	 */
	public static void eliminarCuenta() {

		final String URL = URLBASE + "/cuentas/eliminar/{ncc}";

		RestTemplate restTemplate = new RestTemplate();

		String ncc = Teclado.leerString("\nNúmero de cuenta a eliminar:");

		try {

			restTemplate.delete(URL, ncc);

			System.out.println("\nCuenta eliminada correctamente.");

		} catch (HttpClientErrorException e) {

			System.out.println("\nEl número de cuenta introducido no está en la base de datos.");
		}
	}

	/**
	 * Método para mostrar todas las cuentas
	 */
	public static void consultarCuentas() {

		final String URL = URLBASE + "/cuentas/consultar-todas";

		RestTemplate restTemplate = new RestTemplate();

		Cuenta[] cuentas;

		ResponseEntity<Cuenta[]> response = restTemplate.getForEntity(URL, Cuenta[].class);

		cuentas = response.getBody();

		for (Cuenta c : cuentas) {

			if (c instanceof CuentaPersonal) {
				((CuentaPersonal) c).mostrarDatos();
			}

			if (c instanceof CuentaEmpresa) {
				((CuentaEmpresa) c).mostrarDatos();
			}
		}
	}

	/**
	 * Método para mostrar una cuenta Buscando la cuenta por el número de cuenta
	 */
	public static void consultarUnaCuenta() {

		final String URL = URLBASE + "/cuentas/consultar/{ncc}";

		RestTemplate restTemplate = new RestTemplate();

		String ncc = Teclado.leerString("\nNúmero de cuenta a consultar:");

		Cuenta cuenta;

		try {

			ResponseEntity<Cuenta> response = restTemplate.getForEntity(URL, Cuenta.class, ncc);

			cuenta = response.getBody();

			if (cuenta instanceof CuentaPersonal) {
				((CuentaPersonal) cuenta).mostrarDatos();
			}

			if (cuenta instanceof CuentaEmpresa) {
				((CuentaEmpresa) cuenta).mostrarDatos();
			}

		} catch (HttpClientErrorException e) {

			System.out.println("\nEl número de cuenta introducido no está en la base de datos.");
		}
	}

	/**
	 * Método para retirar dinero de una cuenta
	 */
	public static void retirar() {

		final String URLCONSULTAR = URLBASE + "/cuentas/consultar/{ncc}";

		final String URLRETIRAR = URLBASE + "/cuentas/retirar";

		RestTemplate restTemplate = new RestTemplate();

		String ncc = Teclado.leerString("\nnúmero de cuenta:");

		Cuenta cuenta;

		try {

			ResponseEntity<Cuenta> response = restTemplate.getForEntity(URLCONSULTAR, Cuenta.class, ncc);

			cuenta = response.getBody();

			System.out.println("\n------------ SALDO ACTUAL DE LA CUENTA ------------");

			System.out.println("\nSaldo actual de la cuenta: " + cuenta.getSaldo() + " euros");

			cuenta.setSaldo(Teclado.leerFloat("\nIntroduce la cantidad a retirar:"));

			restTemplate.put(URLRETIRAR, cuenta);

			System.out.println("\nOperación realizada correctamente.");

		} catch (HttpClientErrorException e) {
			System.out.println("\nEl número de cuenta introducido no está en la base de datos.");
		}

	}

	/**
	 * Método para ingresar dinero en una cuenta
	 */
	public static void ingresar() {

		final String URLCONSULTAR = URLBASE + "/cuentas/consultar/{ncc}";

		final String URLINGRESAR = URLBASE + "/cuentas/ingresar";

		RestTemplate restTemplate = new RestTemplate();

		String ncc = Teclado.leerString("\nNúmero de cuenta:");

		Cuenta cuenta;

		try {

			ResponseEntity<Cuenta> response = restTemplate.getForEntity(URLCONSULTAR, Cuenta.class, ncc);

			cuenta = response.getBody();

			System.out.println("\n------------ SALDO ACTUAL DE LA CUENTA ------------");

			System.out.println("\nSaldo actual de la cuenta: " + cuenta.getSaldo() + " euros");

			cuenta.setSaldo(Teclado.leerFloat("\nIntroduce la cantidad a ingresar:"));

			restTemplate.put(URLINGRESAR, cuenta);

			System.out.println("\nOperación realizada correctamente.");

		} catch (HttpClientErrorException e) {
			System.out.println("\nEl número de cuenta introducido no está en la base de datos.");
		}

	}

	/**
	 * Método para realizar una transferencia de una cuenta a otra
	 */
	public static void transferencia() {

		final String URLCUENTAORIGEN = URLBASE + "/cuentas/consultar/{ncc}";

		final String URLACTUALIZARORIGEN = URLBASE + "/cuentas/modificar";

		final String URLCUENTADESTINO = URLBASE + "/cuentas/consultar/{ncc}";

		final String URLACTUALIZARDESTINO = URLBASE + "/cuentas/modificar";

		float cantidad;

		Cuenta cuentaOrigen = null, cuentaDestino = null;

		RestTemplate restTemplateOrigen = new RestTemplate();

		RestTemplate restTemplateDestino = new RestTemplate();

		String nccCuentaOrigen = Teclado.leerString("\nNúmero de cuenta de origen:");

		try {

			ResponseEntity<Cuenta> responseOrigen = restTemplateOrigen.getForEntity(URLCUENTAORIGEN, Cuenta.class,
					nccCuentaOrigen);

			cuentaOrigen = responseOrigen.getBody();

			System.out.println(cuentaOrigen);

		} catch (HttpClientErrorException e) {

			System.out.println("\nEl número de cuenta introducido no está en la base de datos.");
		}

		String nccCuentaDestino = Teclado.leerString("\nNúmero de cuenta de destino:");

		try {

			ResponseEntity<Cuenta> responseDestino = restTemplateDestino.getForEntity(URLCUENTADESTINO, Cuenta.class,
					nccCuentaDestino);

			cuentaDestino = responseDestino.getBody();

			System.out.println(cuentaDestino);

		} catch (HttpClientErrorException e) {

			System.out.println("\nEl número de cuenta introducido no está en la base de datos.");
		}

		cantidad = Teclado.leerFloat("\nIntroduce la cantidad a transferir:");

		cuentaOrigen.retirar(cantidad);

		restTemplateOrigen.put(URLACTUALIZARORIGEN, cuentaOrigen);

		cuentaDestino.ingresar(cantidad);

		restTemplateDestino.put(URLACTUALIZARDESTINO, cuentaDestino);

		cuentaOrigen.transferir(cantidad);

	}

	/**
	 * Método para mostrar el saldo total de la entidad financiera
	 */
	public static void mostrarSaldoTotalBanco() {

		final String URL = URLBASE + "/saldo-total";

		float saldoTotal;

		RestTemplate restTemplate = new RestTemplate();

		saldoTotal = restTemplate.getForObject(URL, Float.class);

		System.out.println("\nEl saldo total de la entidad financiera es: " + saldoTotal + " euros");
	}

	/**
	 * Método para mostrar el cliente con mayor saldo
	 */
	public static void mostrarClienteConMayorSaldo() {

		final String URL = URLBASE + "/cliente-mayor-saldo";

		Cliente cliente;

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Cliente> response = restTemplate.getForEntity(URL, Cliente.class);

		cliente = response.getBody();

		cliente.mostrarDatos();

	}

	/**
	 * Método para mostrar el proveedor de teléfono más usado
	 */
	public static void mostrarProveedorMasUsado() {

	}

	public static void salir() {
		System.out.println("\nGuardando datos...");
	}
}

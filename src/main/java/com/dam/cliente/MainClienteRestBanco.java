package com.dam.cliente;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.dam.utilidades.AppMenu;
import com.dam.utilidades.FuncionesBanco;

public class MainClienteRestBanco extends AppMenu {

	public MainClienteRestBanco() {
		super();
		getOpciones().add("1.Insertar cuenta");
		getOpciones().add("\n2.Eliminar cuenta");
		getOpciones().add("\n3.Actualizar cuenta");
		getOpciones().add("\n4.Consultar cuentas");
		getOpciones().add("\n5.Consultar una cuenta");
		getOpciones().add("\n6.Retirar dinero de una cuenta");
		getOpciones().add("\n7.Ingresar dinero en una cuenta");
		getOpciones().add("\n8.Realizar transferencia a una cuenta");
		getOpciones().add("\n9.Mostar saldo total del banco");
		getOpciones().add("\n10.Mostar cliente con mayor saldo");
		getOpciones().add("\n11.Mostar proveedor telefónico más usado");
		getOpciones().add("\n12.Salir de la aplicacion");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MainClienteRestBanco app;

		Instant inicio, fin;

		inicio = Instant.now().plusSeconds(7200);

		System.out.println("Hora de inicio de la aplicación: " + inicio);

		app = new MainClienteRestBanco();

		app.run();

		fin = Instant.now().plusSeconds(7200);

		Duration duracion = Duration.between(inicio, fin);

		LocalTime tiempo = LocalTime.ofSecondOfDay(duracion.getSeconds());

		DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");

		System.out.println("\nTiempo de utilización de la aplicación: " + tiempo.format(formato));
	}

	/** Métodos */
	@Override
	public void evaluarOpcion(int opcion) {
		// TODO Auto-generated method stub

		switch (opcion) {
		case 1:
			FuncionesBanco.insertarCuenta();
			break;
		case 2:
			FuncionesBanco.eliminarCuenta();
			break;
		case 3:
			FuncionesBanco.actualizarCuenta();
			break;
		case 4:
			FuncionesBanco.consultarCuentas();
			break;
		case 5:
			FuncionesBanco.consultarUnaCuenta();
			break;
		case 6:
			FuncionesBanco.retirar();
			break;
		case 7:
			FuncionesBanco.ingresar();
			break;
		case 8:
			FuncionesBanco.transferencia();
			break;
		case 9:
			FuncionesBanco.mostrarSaldoTotalBanco();
			break;
		case 10:
			FuncionesBanco.mostrarClienteConMayorSaldo();
			break;
		case 11:
			FuncionesBanco.mostrarProveedorMasUsado();
			break;
		case 12:
			FuncionesBanco.salir();
			break;
		default:
			System.out.println("\nSelecciona una opción del menú.");
			break;
		}
	}

}

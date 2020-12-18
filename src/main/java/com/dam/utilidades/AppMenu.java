package com.dam.utilidades;

import java.util.ArrayList;

/**
 * 
 * @author Daniel
 *
 */

public abstract class AppMenu {

	private ArrayList<String> opciones;

	// Constructor por defecto.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AppMenu() {
		opciones = new ArrayList();
	}

	// Constructor con parámetros.
	public AppMenu(ArrayList<String> opciones) {
		this.opciones = opciones;
	}

	// Getters & Setters
	public ArrayList<String> getOpciones() {
		return opciones;
	}

	public void setOpciones(ArrayList<String> opciones) {
		this.opciones = opciones;
	}

	// Método para mostrar las opciones del menu de opciones.
	public void mostrarOpciones() {

		for (int i = 0; i < opciones.size(); i++) {
			System.out.println(opciones.get(i));
		}
	}

	// Método para leer una opción del menu de opciones.
	public int leerOpcion() {

		int opcion;

		do {
			opcion = Libreria.leerPositivo("\nSelecciona una opción:");
		} while (opcion < 1 || opcion > opciones.size());

		return opcion;
	}

	// Método para ejecutar la aplicación.
	public void run() {
		int opcion;

		do {
			System.out.println();
			mostrarOpciones();
			opcion = leerOpcion();
			evaluarOpcion(opcion);
		} while (opcion != opciones.size());
	}

	/*
	 * Un Método abstracto es un Método declarado pero no implementado, es decir, es
	 * un Método del que solo se escribe su nombre, parámetros y tipo devuelto pero
	 * no su código. Los métodos abstractos se escriben sin llaves {} y con ; al
	 * final de la declaración.
	 */
	public abstract void evaluarOpcion(int opcion);
}

package com.dam.modelos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Daniel
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Direccion implements Serializable {

	@Column(length = 50)
	private String calle;

	private int numero;

	@Column(length = 50)
	private String poblacion;

	@Column(length = 50)
	private String provincia;

	private static final long serialVersionUID = 1L;

	// Métodos
	public void mostrarDatos() {

		System.out.println("\nCalle: " + calle);

		System.out.println("\nNúmero: " + numero);

		System.out.println("\nPoblación: " + poblacion);

		System.out.println("\nProvincia: " + provincia);
	}

}

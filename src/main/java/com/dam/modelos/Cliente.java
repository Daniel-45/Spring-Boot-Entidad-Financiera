package com.dam.modelos;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

/**
 * 
 * @author Daniel
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "CLIENTES_SPRING")
public class Cliente implements Serializable {

	@Id
	@NonNull
	@Column(length = 9)
	private String nif;

	@Column(length = 25)
	private String nombre;

	@Column(length = 50)
	private String apellidos;

	@Digits(integer = 8, fraction = 2)
	@Column(nullable = true)
	private float aval;

	private static final long serialVersionUID = 1L;

	@Singular
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_NIF_CLIENTE")
	private Set<Contacto> telefonos;

	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Riesgo riesgo;

	// Métodos
	public void mostrarDatos() {

		System.out.println("\nNIF: " + nif);

		System.out.println("\nNombre: " + nombre);

		System.out.println("\nApellidos: " + apellidos);

		System.out.println("\nAval: " + aval + " euros");

		for (Contacto c : telefonos) {
			c.mostrarDatos();
		}

		System.out.println("\nRiesgo: " + riesgo);
	}
}

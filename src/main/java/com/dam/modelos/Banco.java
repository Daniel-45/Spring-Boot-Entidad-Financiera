package com.dam.modelos;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ENTIDADES_FINANCIERAS")
public class Banco {

	@Id
	@Column(name = "SUCURSAL")
	private long idBanco;

	@Column(name = "ENTIDAD_FINANCIERA", length = 50)
	private String nombreBanco;

	@Embedded
	private Direccion direccion;

	@Singular
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_SUCURSAL_BANCO")
	private Set<Cuenta> cuentas;

	// Métodos
	public void mostrarDatos() {

		System.out.println("\n---------------- ENTIDAD FINANCIERA -----------------");

		System.out.println("\nSucursal: " + idBanco);

		System.out.println("\nBanco: " + nombreBanco);

		direccion.mostrarDatos();

		for (Cuenta c : cuentas) {
			c.mostrarDatos();
		}
	}
}

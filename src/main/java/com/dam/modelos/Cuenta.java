package com.dam.modelos;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

/**
 * 
 * @author Daniel
 *
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "CUENTAS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO_CUENTA", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = CuentaPersonal.class, name = "CUENTA_PERSONAL"),
		@Type(value = CuentaEmpresa.class, name = "CUENTA_EMPRESA") })
public abstract class Cuenta implements Serializable {

	@Id
	@NonNull
	@EqualsAndHashCode.Include
	@Column(name = "NUMERO_CUENTA", length = 24)
	private String ncc;

	private float saldo;

	@Singular
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "CUENTAS_CLIENTES", joinColumns = @JoinColumn(name = "FK_CUENTA"), inverseJoinColumns = @JoinColumn(name = "FK_CLIENTE"))
	private Set<Cliente> clientes;

	private static final long serialVersionUID = 1L;

	/** Métodos */

	/**
	 * Método para calcular la suma total de los avales de un cliente
	 * 
	 * @return la suma de los avales
	 */
	public float totalAvales() {

		float total = 0;

		for (Cliente c : clientes) {

			total += c.getAval();
		}

		return total;

		// return (float) clientes.stream().mapToDouble(Cliente::getAval).sum();
	}

	/**
	 * Método para ingresar dinero en una cuenta
	 * 
	 * @param cantidad a ingresar
	 */
	public void ingresar(float cantidad) {

		if (cantidad < 0) {
			cantidad = 0;
		}

		saldo += cantidad;
	}

	/**
	 * Método para retirar dinero de una cuenta
	 * 
	 * @param cantidad a ingresar
	 * @return TRUE si se puede retirar o FALSE si no se puede retirar
	 */
	public boolean retirar(float cantidad) {

		boolean exito = true;

		if (cantidad < 0) {
			cantidad = 0;
		}

		if (saldo - cantidad >= maximoNegativo()) {
			saldo -= cantidad;
		} else {
			exito = false;
		}

		return exito;
	}

	/**
	 * Método para realizar transferencias de dinero entre cuentas
	 * 
	 * @param cantidad a ingresar
	 * @return TRUE si se puede transferir o FALSE si no se puede transferir
	 */
	public boolean transferir(float cantidad) {

		boolean exito = true;

		float comision;

		if (retirar(cantidad)) {

			comision = calcularComision(cantidad);

			saldo -= comision;
		} else {
			exito = false;
		}

		return exito;
	}

	/**
	 * Función abstracta para calcular el máximo negativo permitido. Se implementa en las clases hijas.
	 * 
	 * @return máximo negativo
	 */
	public abstract float maximoNegativo();

	/**
	 * Función abstracta para calcular la comisión de la transferencia. Se implementa en las clases hijas.
	 * 
	 * @param cantidad de la comisión
	 * @return comisión por realizar la transferencia
	 */
	public abstract float calcularComision(float cantidad);

	/**
	 * Método para mostrar datos de la clase Cuenta
	 */
	public void mostrarDatos() {
		System.out.println("\nNúmero de cuenta: " + ncc);
		for (Cliente c : clientes) {
			c.mostrarDatos();
		}
		System.out.println("\nSaldo: " + saldo + " euros");
	}

}

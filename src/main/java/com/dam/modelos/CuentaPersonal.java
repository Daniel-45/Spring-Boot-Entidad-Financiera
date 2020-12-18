package com.dam.modelos;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.springframework.data.annotation.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@DiscriminatorValue(value = "CUENTA_PERSONAL")
public class CuentaPersonal extends Cuenta {

	@Transient
	public static final float COMISION = 0.002f;

	@Transient
	public static final float MAXIMOCOMISION = 4f;

	private static final long serialVersionUID = 1L;

	@Column(name = "TARJETA_CREDITO", nullable = true)
	private boolean credito;

	// Métodos
	public void mostrarDatos() {

		System.out.println("\n------------------ CUENTA PERSONAL ------------------");

		super.mostrarDatos();

		if (credito = true) {

			System.out.println("\nTarjeta de crédito: Si");
		} else {

			System.out.println("\nTarjeta de crédito: No");
		}
	}

	@Override
	public float maximoNegativo() {
		// TODO Auto-generated method stub
		float maximo = 0;

		maximo = totalAvales() / 2;

		return -maximo;
	}

	@Override
	public float calcularComision(float cantidad) {
		// TODO Auto-generated method stub
		float comision = 0;

		if (cantidad < 0) {
			cantidad = 0;
		}

		comision = cantidad * COMISION;

		if (comision > MAXIMOCOMISION) {
			comision = MAXIMOCOMISION;
		}

		return comision;
	}

}

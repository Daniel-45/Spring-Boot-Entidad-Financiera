package com.dam.modelos;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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
@DiscriminatorValue(value = "CUENTA_EMPRESA")
public class CuentaEmpresa extends Cuenta {

	@Transient
	public static final float COMISION = 0.001f;

	@Transient
	public static final float MAXIMOCOMISION = 6f;

	private static final long serialVersionUID = 1L;

	@Column(length = 50)
	private String empresa;

	@Column(length = 9, unique = true)
	private String cif;

	@Enumerated(EnumType.STRING)
	@Column(length = 9)
	private Tipo local;

	/** Métodos */

	/** Mostrar datos */
	public void mostrarDatos() {
		System.out.println("\n------------------ CUENTA EMPRESA -------------------");
		super.mostrarDatos();
		System.out.print("\nEmpresa: " + empresa);
		System.out.print(" - CIF: " + cif + "\n");
		System.out.println("\nLocal: " + local);
	}

	@Override
	public float maximoNegativo() {
		// TODO Auto-generated method stub
		float maximo = 0;

		maximo = totalAvales() * 2;

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

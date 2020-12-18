package com.dam.modelos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Entity
@Table(name = "TELEFONOS")
public class Contacto implements Serializable {

	@Id
	@Column(length = 12)
	private String numero;

	@Column(length = 20)
	private String proveedor;

	private static final long serialVersionUID = 1L;

	// Métodos
	public void mostrarDatos() {
		System.out.print("\nTeléfono: " + numero);
		System.out.print(" - Proveedor: " + proveedor + "\n");
	}
}

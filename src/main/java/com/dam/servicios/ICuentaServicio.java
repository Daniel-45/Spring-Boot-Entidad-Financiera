package com.dam.servicios;

import java.util.List;
import java.util.Optional;

import com.dam.modelos.Cuenta;

/**
 * 
 * @author Daniel
 *
 */
public interface ICuentaServicio {

	public boolean insert(Cuenta cuenta);

	public boolean update(Cuenta cuenta);

	public boolean deleteById(String ncc);

	public List<Cuenta> findAll();

	public List<Cuenta> findAllSorted();

	public Optional<Cuenta> findById(String ncc);

	public boolean ingresar(String nccCuentaOrigen, float cantidad);

	public boolean retirar(String nccCuenta, float cantidad);

	public boolean transferir(String nccCuentaOrigen, float cantidad);

}

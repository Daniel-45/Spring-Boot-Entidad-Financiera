package com.dam.servicios;

import java.util.List;
import java.util.Optional;

import com.dam.modelos.Cliente;

/**
 * 
 * @author Daniel
 *
 */
public interface IClienteServicio {

	public boolean insert(Cliente cliente);

	public boolean update(Cliente cliente);

	public boolean delete(String id);

	public List<Cliente> findAll();

	public List<Cliente> findAllSorted();

	public Optional<Cliente> findByNif(String nif);

	public String findProveedorMasUsado();

}

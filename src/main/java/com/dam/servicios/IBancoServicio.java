package com.dam.servicios;

import java.util.List;
import java.util.Optional;

import com.dam.modelos.Banco;
import com.dam.modelos.Cliente;

/**
 * 
 * @author Daniel
 *
 */
public interface IBancoServicio {

	public boolean insert(Banco banco);

	public boolean update(Banco banco);

	public boolean deleteById(Long idBanco);

	public List<Banco> findAll();

	public List<Banco> findAllSorted();

	public Optional<Banco> findById(Long idBanco);

	public float findSaldoTotal();

	public Cliente findClienteConMayorSaldo();

}

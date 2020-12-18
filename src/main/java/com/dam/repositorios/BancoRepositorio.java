package com.dam.repositorios;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dam.modelos.Banco;

/**
 * 
 * @author Daniel
 *
 */

@Repository
public interface BancoRepositorio extends CrudRepository<Banco, Long> {

	Iterable<Banco> findAll(Sort sort);

	@Query("SELECT SUM(c.saldo) FROM Cuenta c ")
	public float findSaldoTotal();

	@Query("SELECT cliente, SUM(cuenta.saldo) " 
			+ "FROM Cuenta cuenta INNER JOIN cuenta.clientes cliente "
			+ "GROUP BY cliente.nif " 
			+ "ORDER BY SUM(cuenta.saldo) DESC")
	public List<Object[]> findClienteConMayorSaldo();

}

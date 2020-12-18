package com.dam.repositorios;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dam.modelos.Cliente;

/**
 * 
 * @author Daniel
 *
 */

@Repository
public interface ClienteRepositorio extends CrudRepository<Cliente, String> {

	Iterable<Cliente> findAll(Sort sort);
	
	@Query("SELECT t.proveedor, COUNT(t) "
			+ "FROM Cliente cliente INNER JOIN cliente.telefonos t "
			+ "GROUP BY t.proveedor "
			+ "ORDER BY COUNT(t) DESC" )
	List<Object[]> findProveedorMasUsado();
	
}

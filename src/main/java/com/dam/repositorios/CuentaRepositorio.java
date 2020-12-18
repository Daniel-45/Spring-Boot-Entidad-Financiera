package com.dam.repositorios;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dam.modelos.Cuenta;

/**
 * 
 * @author Daniel
 *
 */
@Repository
public interface CuentaRepositorio extends CrudRepository<Cuenta, String> {

	Iterable<Cuenta> findAll(Sort sort);

}

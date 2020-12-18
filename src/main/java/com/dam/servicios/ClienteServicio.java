package com.dam.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dam.modelos.Cliente;
import com.dam.repositorios.ClienteRepositorio;

/**
 * 
 * @author Daniel
 *
 */

@Service
public class ClienteServicio implements IClienteServicio {

	@Autowired
	private ClienteRepositorio daoCliente;

	@Override
	public boolean insert(Cliente cliente) {
		// TODO Auto-generated method stub
		boolean exito = false;

		if (!daoCliente.existsById(cliente.getNif())) {
			daoCliente.save(cliente);
			exito = true;
		}

		return exito;
	}

	@Override
	public boolean update(Cliente cliente) {
		// TODO Auto-generated method stub
		boolean exito = false;

		if (daoCliente.existsById(cliente.getNif())) {
			daoCliente.save(cliente);
			exito = true;
		}

		return exito;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		boolean exito = false;

		if (daoCliente.existsById(id)) {
			daoCliente.deleteById(id);
			exito = true;
		}
		return exito;
	}

	@Override
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) daoCliente.findAll();
	}

	@Override
	public List<Cliente> findAllSorted() {
		// TODO Auto-generated method stub
		Sort sortNombre = Sort.by("nombre").ascending();
		Sort sortApellidos = Sort.by("apellidos").descending();
		Sort sort = sortNombre.and(sortApellidos);
		return (List<Cliente>) daoCliente.findAll(sort);
	}

	@Override
	public Optional<Cliente> findByNif(String nif) {
		// TODO Auto-generated method stub
		return daoCliente.findById(nif);
	}

	@Override
	public String findProveedorMasUsado() {
		// TODO Auto-generated method stub
		List<Object[]> listaProveedores = daoCliente.findProveedorMasUsado();

		return (String) listaProveedores.get(0)[0];
	}

}

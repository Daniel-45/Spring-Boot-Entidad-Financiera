package com.dam.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dam.modelos.Banco;
import com.dam.modelos.Cliente;
import com.dam.repositorios.BancoRepositorio;

/**
 * 
 * @author Daniel
 *
 */

@Service
public class BancoServicio implements IBancoServicio {

	@Autowired
	private BancoRepositorio daoBanco;

	@Override
	public boolean insert(Banco banco) {
		// TODO Auto-generated method stub
		boolean exito = false;

		if (!daoBanco.existsById(banco.getIdBanco())) {
			daoBanco.save(banco);
			exito = true;
		}
		return exito;
	}

	@Override
	public boolean update(Banco banco) {
		// TODO Auto-generated method stub
		boolean exito = false;
		if (daoBanco.existsById(banco.getIdBanco())) {
			daoBanco.save(banco);
			exito = true;
		}
		return exito;
	}

	@Override
	public boolean deleteById(Long idBanco) {
		// TODO Auto-generated method stub
		boolean exito = false;
		if (daoBanco.existsById(idBanco)) {
			daoBanco.deleteById(idBanco);
			exito = true;
		}
		return exito;
	}

	@Override
	public List<Banco> findAll() {
		// TODO Auto-generated method stub
		return (List<Banco>) daoBanco.findAll();
	}

	@Override
	public List<Banco> findAllSorted() {
		// TODO Auto-generated method stub
		Sort sort = Sort.by("nombreBanco").descending();
		return (List<Banco>) daoBanco.findAll(sort);
	}

	@Override
	public Optional<Banco> findById(Long id) {
		// TODO Auto-generated method stub
		return daoBanco.findById(id);
	}

	@Override
	public float findSaldoTotal() {
		// TODO Auto-generated method stub
		return daoBanco.findSaldoTotal();
	}

	@Override
	public Cliente findClienteConMayorSaldo() {
		// TODO Auto-generated method stub
		return (Cliente) daoBanco.findClienteConMayorSaldo().get(0)[0];
	}

}

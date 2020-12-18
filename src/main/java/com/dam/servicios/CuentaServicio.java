package com.dam.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dam.modelos.Cuenta;
import com.dam.repositorios.CuentaRepositorio;

/**
 * 
 * @author Daniel
 *
 */

@Service
public class CuentaServicio implements ICuentaServicio {

	@Autowired
	private CuentaRepositorio daoCuenta;

	@Override
	public boolean insert(Cuenta cuenta) {
		// TODO Auto-generated method stub
		boolean exito = false;

		if (!daoCuenta.existsById(cuenta.getNcc())) {
			daoCuenta.save(cuenta);
			exito = true;
		}

		return exito;
	}

	@Override
	public boolean update(Cuenta cuenta) {
		// TODO Auto-generated method stub
		boolean exito = false;
		if (daoCuenta.existsById(cuenta.getNcc())) {
			daoCuenta.save(cuenta);
			exito = true;
		}
		return exito;
	}

	@Override
	public boolean deleteById(String ncc) {
		// TODO Auto-generated method stub
		boolean exito = false;
		if (daoCuenta.existsById(ncc)) {
			daoCuenta.deleteById(ncc);
			exito = true;
		}
		return exito;
	}

	@Override
	public List<Cuenta> findAll() {
		// TODO Auto-generated method stub
		return (List<Cuenta>) daoCuenta.findAll();
	}

	@Override
	public List<Cuenta> findAllSorted() {
		// TODO Auto-generated method stub
		Sort sort = Sort.by("saldo").descending();
		return (List<Cuenta>) daoCuenta.findAll(sort);
	}

	@Override
	public Optional<Cuenta> findById(String ncc) {
		// TODO Auto-generated method stub
		return daoCuenta.findById(ncc);
	}

	@Override
	public boolean ingresar(String ncc, float cantidad) {
		// TODO Auto-generated method stub
		boolean exito = false;

		Cuenta cuenta;

		Optional<Cuenta> cuentaOptional = findById(ncc);

		if (cuentaOptional.isPresent()) {

			cuenta = cuentaOptional.get();

			if (cuenta.getNcc().equals(ncc)) {

				cuenta.ingresar(cantidad);

				daoCuenta.save(cuenta);

				exito = true;
			}
		}
		return exito;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean retirar(String ncc, float cantidad) {
		// TODO Auto-generated method stub
		boolean exito = false;

		float cantidadRetirar = cantidad;

		Cuenta cuenta;

		Optional<Cuenta> cuentaOptional = findById(ncc);

		if (cuentaOptional.isPresent()) {

			cuenta = cuentaOptional.get();

			if (cuenta.retirar(cantidad)) {

				daoCuenta.save(cuenta);

				exito = true;
			} else {

				cantidadRetirar = cuenta.maximoNegativo();
			}
		}

		return exito;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean transferir(String nccCuentaOrigen, float cantidad) {
		// TODO Auto-generated method stub
		boolean exito = false;

		float cantidadTransferir = cantidad;

		Cuenta cuenta;

		Optional<Cuenta> cuentaOptional = findById(nccCuentaOrigen);

		if (cuentaOptional.isPresent()) {

			cuenta = cuentaOptional.get();

			if (cuenta.transferir(cantidad)) {

				daoCuenta.save(cuenta);

				exito = true;
			} else {

				cantidadTransferir = cuenta.maximoNegativo();
			}
		}

		return exito;
	}

}

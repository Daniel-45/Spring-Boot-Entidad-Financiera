package com.dam.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dam.modelos.Cuenta;
import com.dam.servicios.ICuentaServicio;

/**
 * 
 * @author Daniel
 *
 */

@RestController
@RequestMapping("banco/cuentas")
public class ControladorRestCuenta {

	private ICuentaServicio servicioCuenta;

	public ControladorRestCuenta(ICuentaServicio servicioCuenta) {

		this.servicioCuenta = servicioCuenta;
	}

	@PostMapping("/insertar")
	public ResponseEntity<Cuenta> insertarCuenta(@RequestBody Cuenta cuenta, @RequestHeader HttpHeaders httpHeaders) {

		HttpStatus status = HttpStatus.CREATED;

		System.out.println("cuenta" + cuenta);
		if (!servicioCuenta.findById(cuenta.getNcc()).isPresent()) {
			servicioCuenta.insert(cuenta);
		} else {
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<Cuenta>(cuenta, status);
	}

	@PutMapping("/modificar")
	public ResponseEntity<Cuenta> modificarCuenta(@RequestBody Cuenta cuenta) {

		HttpStatus status = HttpStatus.ACCEPTED;

		if (!servicioCuenta.update(cuenta)) {
			status = HttpStatus.BAD_REQUEST;
		} else {
			servicioCuenta.update(cuenta);
		}

		return new ResponseEntity<Cuenta>(cuenta, status);
	}

	@DeleteMapping("/eliminar/{ncc}")
	public ResponseEntity<Cuenta> eliminarCuenta(@PathVariable String ncc) {

		ResponseEntity<Cuenta> response;

		if (servicioCuenta.deleteById(ncc)) {
			response = new ResponseEntity<Cuenta>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<Cuenta>(HttpStatus.NOT_FOUND);
		}

		return response;
	}

	@GetMapping("/consultar-todas")
	public ResponseEntity<Cuenta[]> consultarCuentas() {

		ResponseEntity<Cuenta[]> response;

		List<Cuenta> cuentas = servicioCuenta.findAll();

		Cuenta[] array = new Cuenta[cuentas.size()];

		array = cuentas.toArray(array);

		response = new ResponseEntity<Cuenta[]>(array, HttpStatus.OK);

		return response;

	}

	@GetMapping("/consultar/{ncc}")
	public ResponseEntity<Cuenta> consultarCuenta(@PathVariable String ncc) {

		ResponseEntity<Cuenta> response;

		Optional<Cuenta> cuenta = servicioCuenta.findById(ncc);

		if (!cuenta.isPresent()) {
			response = new ResponseEntity<Cuenta>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<Cuenta>(cuenta.get(), HttpStatus.OK);
		}

		return response;

	}

	@PutMapping("/retirar")
	public ResponseEntity<Cuenta> retirar(@RequestBody Cuenta cuenta) {

		HttpStatus status = HttpStatus.OK;

		if (!servicioCuenta.retirar(cuenta.getNcc(), cuenta.getSaldo())) {
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<Cuenta>(cuenta, status);
	}

	@PutMapping("/ingresar")
	public ResponseEntity<Cuenta> ingresar(@RequestBody Cuenta cuenta) {

		HttpStatus status = HttpStatus.OK;

		if (!servicioCuenta.ingresar(cuenta.getNcc(), cuenta.getSaldo())) {
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<Cuenta>(cuenta, status);
	}

}

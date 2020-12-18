package com.dam.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dam.modelos.Cliente;
import com.dam.servicios.IClienteServicio;

/**
 * 
 * @author Daniel
 *
 */

@RestController
@RequestMapping("banco/clientes")
public class ControladorRestCliente {

	@Autowired
	IClienteServicio servicioCliente;

	@PostMapping("/insertar")
	public ResponseEntity<Cliente> insertarCliente(@RequestBody Cliente cliente) {

		HttpStatus status = HttpStatus.CREATED;

		if (!servicioCliente.findByNif(cliente.getNif()).isPresent()) {
			servicioCliente.insert(cliente);
		} else {
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<Cliente>(cliente, status);
	}

	@GetMapping("/listar")
	public ResponseEntity<Cliente[]> consultarClientes() {

		ResponseEntity<Cliente[]> response;

		List<Cliente> clientes = servicioCliente.findAll();

		Cliente[] array = new Cliente[clientes.size()];

		array = clientes.toArray(array);

		response = new ResponseEntity<Cliente[]>(array, HttpStatus.OK);

		return response;
	}

	@GetMapping("/consultar/{nif}")
	public ResponseEntity<Cliente> consultarCliente(@PathVariable String nif) {

		ResponseEntity<Cliente> response;

		Optional<Cliente> cliente = servicioCliente.findByNif(nif);

		if (!cliente.isPresent()) {
			response = new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<Cliente>(cliente.get(), HttpStatus.OK);
		}

		return response;
	}

	@GetMapping("/consultar/proveedor")
	public ResponseEntity<String> consultarProveedorMasUsado() {

		return null;

	}

	@PutMapping("/modificar")
	public ResponseEntity<Cliente> modificarCliente(@RequestBody Cliente cliente) {

		HttpStatus status = HttpStatus.ACCEPTED;

		if (!servicioCliente.update(cliente)) {
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<Cliente>(cliente, status);
	}

	@DeleteMapping("/eliminar/{nif}")
	public ResponseEntity<Cliente> eliminarCliente(@PathVariable String nif) {

		ResponseEntity<Cliente> response;

		if (servicioCliente.delete(nif)) {
			response = new ResponseEntity<>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return response;
	}
}

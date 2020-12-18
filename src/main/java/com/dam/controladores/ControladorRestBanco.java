package com.dam.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dam.modelos.Banco;
import com.dam.modelos.Cliente;
import com.dam.servicios.IBancoServicio;

/**
 * 
 * @author Daniel
 *
 */

@RestController
@RequestMapping("banco")
public class ControladorRestBanco {

	private IBancoServicio servicioBanco;

	public ControladorRestBanco(IBancoServicio servicioBanco) {

		this.servicioBanco = servicioBanco;
	}

	@PostMapping("/insertar")
	public ResponseEntity<Banco> insertarCliente(@RequestBody Banco banco) {

		HttpStatus status = HttpStatus.CREATED;

		if (!servicioBanco.findById(banco.getIdBanco()).isPresent()) {
			servicioBanco.insert(banco);
		} else {
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<Banco>(banco, status);
	}

	@GetMapping("/consultar/{idBanco}")
	public ResponseEntity<Banco> consultarCliente(@PathVariable Long idBanco) {

		ResponseEntity<Banco> response;

		Optional<Banco> banco = servicioBanco.findById(idBanco);

		if (!banco.isPresent()) {
			response = new ResponseEntity<Banco>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<Banco>(banco.get(), HttpStatus.OK);
		}

		return response;
	}

	@GetMapping("/listar")
	public ResponseEntity<Banco[]> consultarClientes() {

		ResponseEntity<Banco[]> response;

		List<Banco> bancos = servicioBanco.findAll();

		Banco[] array = new Banco[bancos.size()];

		array = bancos.toArray(array);

		response = new ResponseEntity<Banco[]>(array, HttpStatus.OK);

		return response;
	}

	@GetMapping("/saldo-total")
	public Float consultarSaldoTotalBanco() {
		return servicioBanco.findSaldoTotal();
	}

	@GetMapping("/cliente-mayor-saldo")
	public Cliente buscarClienteConMayorSaldo(@RequestParam(required = false, name = "cliente") Cliente cliente) {
		cliente = servicioBanco.findClienteConMayorSaldo();
		return cliente;
	}

	@PutMapping("/modificar")
	public ResponseEntity<Banco> modificarCliente(@RequestBody Banco banco) {

		HttpStatus status = HttpStatus.ACCEPTED;

		if (!servicioBanco.update(banco)) {
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<Banco>(banco, status);
	}

	@DeleteMapping("/eliminar/{dni}")
	public ResponseEntity<Banco> eliminarCliente(@PathVariable Long idBanco) {

		ResponseEntity<Banco> response;

		if (servicioBanco.deleteById(idBanco)) {
			response = new ResponseEntity<>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return response;
	}

}

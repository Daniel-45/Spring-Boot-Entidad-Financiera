package com.dam.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dam.modelos.Banco;
import com.dam.modelos.Cliente;
import com.dam.modelos.Contacto;
import com.dam.modelos.Cuenta;
import com.dam.modelos.CuentaEmpresa;
import com.dam.modelos.CuentaPersonal;
import com.dam.modelos.Direccion;
import com.dam.modelos.Riesgo;
import com.dam.modelos.Tipo;
import com.dam.servicios.IBancoServicio;
import com.dam.servicios.IClienteServicio;
import com.dam.servicios.ICuentaServicio;

/**
 * 
 * @author Daniel
 *
 */

@RestController
@RequestMapping("banco/datos")
public class ControladorRestCargarDatosBanco {

	@Autowired
	private IClienteServicio servicioCliente;
	@Autowired
	private ICuentaServicio servicioCuenta;
	@Autowired
	private IBancoServicio servicioBanco;

	@GetMapping("/cargar-datos")
	public ResponseEntity<String> cargarDatos() {

		ResponseEntity<String> response;

		Contacto telefono1, telefono2, telefono3, telefono4;

		telefono1 = Contacto.builder().numero("648 11 99 48").proveedor("Movistar").build();
		telefono2 = Contacto.builder().numero("669 97 90 50").proveedor("Movistar").build();
		telefono3 = Contacto.builder().numero("648 22 56 97").proveedor("Orange").build();
		telefono4 = Contacto.builder().numero("603 86 24 18").proveedor("Vodafone").build();

		Cliente cliente1, cliente2, cliente3;

		cliente1 = Cliente.builder().nif("53016016Z").nombre("Daniel").apellidos("Pompa Pareja").aval(15000)
				.telefono(telefono1).riesgo(Riesgo.BAJO).build();

		cliente2 = Cliente.builder().nif("53443162A").nombre("Emma").apellidos("Ciambrino Baz").aval(10000)
				.telefono(telefono2).telefono(telefono3).riesgo(Riesgo.BAJO).build();

		cliente3 = Cliente.builder().nif("53246567C").nombre("Carmen").apellidos("Ciambrino Baz").aval(6000)
				.telefono(telefono4).riesgo(Riesgo.BAJO).build();

		servicioCliente.insert(cliente1);
		servicioCliente.insert(cliente2);
		servicioCliente.insert(cliente3);

		Cuenta cuentaPersonal, cuentaEmpresa;

		cuentaPersonal = CuentaPersonal.builder().ncc("ES9400786171625838518141").saldo(8540).cliente(cliente3)
				.credito(true).build();

		cuentaEmpresa = CuentaEmpresa.builder().ncc("ES6600782414127080801220").saldo(48000).cliente(cliente1)
				.cliente(cliente2).empresa("Emma Tech Solutions SL").cif("B48822753").local(Tipo.PROPIO).build();

		servicioCuenta.insert(cuentaPersonal);
		servicioCuenta.insert(cuentaEmpresa);

		Direccion direccion = Direccion.builder().calle("Calle Villablanca").numero(79).poblacion("Vicalvaro")
				.provincia("Madrid").build();

		Banco banco = Banco.builder().idBanco(1).nombreBanco("BBVA").direccion(direccion).cuenta(cuentaPersonal)
				.cuenta(cuentaEmpresa).build();

		servicioBanco.insert(banco);

		response = new ResponseEntity<>("\nDatos insertados correctamente.\n", HttpStatus.OK);

		return response;
	}
}

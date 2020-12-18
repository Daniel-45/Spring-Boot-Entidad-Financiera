package com.dam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author Daniel
 *
 */

public class MainCargarDatosBanco {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		cargarDatosBanco();
	}

	public static void cargarDatosBanco() {
		final String URL = "http://localhost:8080/banco/datos/cargar-datos";

		RestTemplate restTemplate = new RestTemplate();

		try {
			ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
			System.out.println(response.getBody());
		} catch (HttpClientErrorException e) {
			System.out.println("\nERROR!! No se ha podido procesar la petición para insertar los datos");
		}
	}
}

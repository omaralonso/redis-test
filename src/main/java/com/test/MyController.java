package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/prueba")
public class MyController {

	private static Logger logger = LoggerFactory.getLogger(MyController.class);

	@GetMapping(value = "/hola", produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> saludar() {
		logger.info("Inicio metodo saludar");
		return new ResponseEntity<>("Holaaaa!", HttpStatus.OK);
	}

	@GetMapping(value = "/obtener/{key}", produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> obtenerData2(@PathVariable(value = "key", required = true) String key) {

		logger.info("Inicio metodo get");

		logger.info("Key recibida: {}", key);

		String mensaje = "";

		logger.info("Inicio conexion");
		try (Jedis jedis = new Jedis("redis-test.vxhe6s.0001.use1.cache.amazonaws.com", 6379, 10000);) {

			long startTime = System.currentTimeMillis();
			String valor = jedis.get(key);
			long stopTime = System.currentTimeMillis();

			logger.info("TIEMPO TOTAL DE CONSULTA A CACHE: {} MILISEGUNDOS", (stopTime - startTime));

			if (valor != null) {
				mensaje = "Valor obtenido de cache: " + valor;
			} else {
				mensaje = "No se obtuvo resultados para key: " + key;
			}

		} catch (Exception e) {
			mensaje = "Error: " + e.getMessage();
		} finally {
			logger.info(mensaje);
		}

		return new ResponseEntity<>(mensaje, HttpStatus.OK);
	}

	@PostMapping(value = "/post", produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> obtenerData() {
		logger.info("INICIO METODO POST");
		return new ResponseEntity<>(new String("Test"), HttpStatus.OK);
	}
}
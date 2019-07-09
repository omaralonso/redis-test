package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.test.aspect.LogExecutionTime;
import com.test.entity.Alumno;
import com.test.service.IAlumnoService;

@Controller
@RequestMapping("alumno")
public class AlumnoController {

	@Autowired
	private IAlumnoService alumnoService;

	@LogExecutionTime
	@GetMapping(value = "/hola", produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> saludar() {
		return new ResponseEntity<>("Holaaaa Mundo (nuevo)!", HttpStatus.OK);
	}

	@GetMapping("alumno/{id}")
	public ResponseEntity<Alumno> getAlumnoById(@PathVariable("id") Long id) {
		Alumno alumno = alumnoService.getAlumnoById(id);
		return new ResponseEntity<Alumno>(alumno, HttpStatus.OK);
	}

	@GetMapping("alumnos")
	public ResponseEntity<List<Alumno>> getAllAlumnos() {
		List<Alumno> list = alumnoService.getAllAlumnos();
		return new ResponseEntity<List<Alumno>>(list, HttpStatus.OK);
	}

	@PostMapping("alumno")
	public ResponseEntity<Void> addAlumno(@RequestBody Alumno alumno, UriComponentsBuilder builder) {
		Alumno savedAlumno = alumnoService.addAlumno(alumno);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/alumno/{id}").buildAndExpand(savedAlumno.getAlumnoId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("alumno")
	public ResponseEntity<Alumno> updateAlumno(@RequestBody Alumno alumno) {
		alumnoService.updateAlumno(alumno);
		return new ResponseEntity<Alumno>(alumno, HttpStatus.OK);
	}

	@DeleteMapping("alumno/{id}")
	public ResponseEntity<Void> deleteAlumno(@PathVariable("id") Long id) {
		alumnoService.deleteAlumno(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}

package com.test.service;

import java.util.List;

import com.test.entity.Alumno;

public interface IAlumnoService {

	public List<Alumno> getAllAlumnos();

	public Alumno getAlumnoById(long alumnoId);

	public Alumno addAlumno(Alumno alumno);

	public Alumno updateAlumno(Alumno alumno);

	public void deleteAlumno(long alumnoId);

}

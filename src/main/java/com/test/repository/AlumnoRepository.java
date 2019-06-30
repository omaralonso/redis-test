package com.test.repository;

import org.springframework.data.repository.CrudRepository;

import com.test.entity.Alumno;

public interface AlumnoRepository extends CrudRepository<Alumno, Long> {

}

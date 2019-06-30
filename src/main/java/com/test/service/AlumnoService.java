package com.test.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.test.entity.Alumno;
import com.test.repository.AlumnoRepository;

@Service
public class AlumnoService implements IAlumnoService {

	private static Logger logger = LoggerFactory.getLogger(AlumnoService.class);

	@Autowired
	private AlumnoRepository alumnoRepository;

	@Override
	@Cacheable(value = "alumnoCache", key = "#alumnoId")
	public Alumno getAlumnoById(long alumnoId) {
		logger.info("getAlumnoById()");
		return alumnoRepository.findById(alumnoId).get();
	}

	@Override
	@Cacheable(value = "allAlumnosCache", unless = "#result.size() == 0")
	public List<Alumno> getAllAlumnos() {
		logger.info("getAllAlumnos()");
		List<Alumno> list = new ArrayList<>();
		alumnoRepository.findAll().forEach(e -> list.add(e));
		return list;
	}

	@Override
	@Caching(put = { @CachePut(value = "alumnoCache", key = "#alumno.alumnoId") }, evict = {
	    @CacheEvict(value = "allAlumnosCache", allEntries = true) })
	public Alumno addAlumno(Alumno alumno) {
		logger.info("addAlumno()");
		return alumnoRepository.save(alumno);
	}

	@Override
	@Caching(put = { @CachePut(value = "alumnoCache", key = "#alumno.alumnoId") }, evict = {
	    @CacheEvict(value = "allAlumnosCache", allEntries = true) })
	public Alumno updateAlumno(Alumno alumno) {
		logger.info("updateAlumno()");
		return alumnoRepository.save(alumno);
	}

	@Override
	@Caching(evict = { @CacheEvict(value = "alumnoCache", key = "#alumnoId"),
	    @CacheEvict(value = "allAlumnosCache", allEntries = true) })
	public void deleteAlumno(long alumnoId) {
		logger.info("deleteAlumno()");
		alumnoRepository.delete(alumnoRepository.findById(alumnoId).get());
	}

}
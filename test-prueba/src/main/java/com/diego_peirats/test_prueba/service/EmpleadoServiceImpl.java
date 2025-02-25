package com.diego_peirats.test_prueba.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diego_peirats.test_prueba.EmpleadoRepository;
import com.diego_peirats.test_prueba.exception.ResourceNotFoundException;
import com.diego_peirats.test_prueba.model.Empleado;


@Service
public class EmpleadoServiceImpl implements EmpleadoService{
	
	@Autowired
	private EmpleadoRepository repository;

	@Override
	public Empleado saveEmpleado(Empleado empleado) {
		Optional<Empleado> empleadoGuardado = repository.findByEmail(empleado.getEmail());
		if (empleadoGuardado.isPresent()) {
			throw new ResourceNotFoundException("El email ya esta presente: " + empleado.getEmail());
		}
		
		return repository.save(empleado);
	}

	@Override
	public List<Empleado> getAllEmpleado() {
		return repository.findAll();
	}

	@Override
	public Optional<Empleado> getEmpleadoById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Empleado updateEmpleado(Empleado empleadoActualizado) {
		return repository.save(empleadoActualizado);
	}

	@Override
	public void deleteEmpleado(Long id) {
		repository.deleteById(id);;
		
	}

}

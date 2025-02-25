package com.diego_peirats.test_prueba.service;

import java.util.List;
import java.util.Optional;

import com.diego_peirats.test_prueba.model.Empleado;

public interface EmpleadoService {
	
	Empleado saveEmpleado(Empleado empleado);
	
	List<Empleado> getAllEmpleado();
	
	Optional<Empleado> getEmpleadoById(Long Id);
	
	Empleado updateEmpleado(Empleado empleadoActualizado);
	
	void deleteEmpleado(Long id);

}

package com.diego_peirats.test_prueba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego_peirats.test_prueba.model.Empleado;
import com.diego_peirats.test_prueba.service.EmpleadoService;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {
	
	@Autowired
	private EmpleadoService service;
	
	@PostMapping("/save")
	public ResponseEntity<Empleado> saveEmpleado(@RequestBody Empleado empleado) {
		
		service.saveEmpleado(empleado);
		return ResponseEntity.status(HttpStatus.CREATED).body(empleado);
	}
	
	@GetMapping("/all")
	public List<Empleado> getAll() {
		return service.getAllEmpleado();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Empleado> getById(@PathVariable("id") Long id) {
		return service.getEmpleadoById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable("id") Long id,@RequestBody Empleado empleado ){
		return service.getEmpleadoById(id)
				.map(
						empleadoGuardado -> {

							empleadoGuardado.setNombre(empleado.getNombre());
							empleadoGuardado.setApellido(empleado.getApellido());
							empleadoGuardado.setEmail(empleado.getEmail());
							
							Empleado empleadoActualizado = service.updateEmpleado(empleadoGuardado);
							return new ResponseEntity<>(empleadoActualizado, HttpStatus.OK);
						}
						)
				.orElseGet(()-> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmpleado(@PathVariable("id") Long id){
		service.deleteEmpleado(id);
		return new ResponseEntity<String>("Eliminado con éxito", HttpStatus.ACCEPTED);
	}
	
	
	

}

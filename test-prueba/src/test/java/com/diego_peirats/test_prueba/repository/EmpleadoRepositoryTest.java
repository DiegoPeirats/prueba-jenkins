package com.diego_peirats.test_prueba.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.diego_peirats.test_prueba.EmpleadoRepository;
import com.diego_peirats.test_prueba.model.Empleado;

@DataJpaTest
public class EmpleadoRepositoryTest {
	
	@Autowired
	private EmpleadoRepository repository;
	
	private Empleado empleado;
	
	@BeforeEach
	public void setup() {
		empleado = new Empleado("Mario", "Mariano", "melagarraconlamano@gmail.com");
	}
	
	@Test
	void testGuardarEmpleado() {
		 //given
		Empleado emple1 = new Empleado("Juan", "Martinez", "juan@gmail.com");
		
		//when
		Empleado empleadoGuardado = repository.save(emple1);
		
		//then
		Assertions.assertThat(empleadoGuardado).isNotNull();
		Assertions.assertThat(empleadoGuardado.getId() > 0);
		
	}
	
	@Test
	void testListarElementos() {
		Empleado emple2 = new Empleado("Juan", "Loape", "chachojuan@gmail.com");
		
		repository.save(empleado);
		repository.save(emple2);
		
		List<Empleado> empleados = repository.findAll();
		
		Assertions.assertThat(empleados).isNotNull();
		Assertions.assertThat(empleados.size()).isEqualTo(2);
		
	}
	
	@Test
	void obtenerEmpleadoById() {
		Empleado empleadoGuardado = repository.save(empleado);
		
		Empleado empleadoBD = repository.findById(empleadoGuardado.getId()).get();
		
		Assertions.assertThat(empleadoBD).isNotNull();
	}
	
	@Test
	void actualizarEmpleado() {
		Empleado empleadoGuardado = repository.save(empleado);
		
		Empleado empleadoBD = repository.findById(empleadoGuardado.getId()).get();
		
		empleadoBD.setNombre("picha brava");
		empleadoBD.setApellido("Martinez");

		Empleado empleadoActualizado = repository.save(empleadoBD);

		Assertions.assertThat(empleadoActualizado.getId()).isEqualTo(empleadoGuardado.getId());
		Assertions.assertThat(empleadoActualizado.getNombre()).isEqualTo("picha brava");
		Assertions.assertThat(empleadoActualizado.getApellido()).isEqualTo("Martinez");
		Assertions.assertThat(empleadoActualizado.getEmail()).isEqualTo("melagarraconlamano@gmail.com");
		
	}
	
	@Test
	void eliminarEmpleado() {
		Empleado empleadoGuardado = repository.save(empleado);
		
		repository.deleteById(empleadoGuardado.getId());
		
		Optional<Empleado> empleadoBD = repository.findById(empleadoGuardado.getId());
		
		Assertions.assertThat(empleadoBD).isEmpty();
		
	}

}

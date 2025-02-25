package com.diego_peirats.test_prueba.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.diego_peirats.test_prueba.EmpleadoRepository;
import com.diego_peirats.test_prueba.exception.ResourceNotFoundException;
import com.diego_peirats.test_prueba.model.Empleado;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTests {
	
	@Mock
	private EmpleadoRepository repository;
	
	@InjectMocks
	private EmpleadoServiceImpl empleadoService;
	
	private Empleado empleado;
	
	@BeforeEach
	public void setup() {
		empleado = new Empleado(1L,"Mario", "Mariano", "melagarraconlamano@gmail.com");
	}
	
	@Test
	void testGuardarEmpleado() {
		 //given
		BDDMockito.given(repository.findByEmail(empleado.getEmail()))
		.willReturn(Optional.empty());
		
		BDDMockito.given(repository.save(empleado)).willReturn(empleado);
		
		Empleado empleadoGuardado = empleadoService.saveEmpleado(empleado);
		
		Assertions.assertThat(empleadoGuardado).isNotNull();
		
	}
	
	@Test
	void testGuardarEmpleadoConThrowException() {
		 //given
		BDDMockito.given(repository.findByEmail(empleado.getEmail()))
		.willReturn(Optional.of(empleado));
		
		assertThrows(ResourceNotFoundException.class, () -> {
			empleadoService.saveEmpleado(empleado);
		});
		
		verify(repository, never()).save(any(Empleado.class));
		
	}
	
	@Test
	void testListarElementos() {
		Empleado emple2 = new Empleado("Juan", "Loape", "chachojuan@gmail.com");
		
		BDDMockito.given(repository.findAll()).willReturn(List.of(empleado, emple2));
		
		List<Empleado> empleados = repository.findAll();
		
		Assertions.assertThat(empleados).isNotNull();
		Assertions.assertThat(empleados.size()).isEqualTo(2);
		
	}
	
	@Test
	void testListarElementosVacia() {
		
		BDDMockito.given(repository.findAll()).willReturn(Collections.emptyList());
		
		List<Empleado> empleados = repository.findAll();
		
		Assertions.assertThat(empleados).isEmpty();
		Assertions.assertThat(empleados.size()).isEqualTo(0);
		
	}
	
	@Test
	void eliminarEmpleado() {
		BDDMockito.willDoNothing().given(repository).deleteById(empleado.getId());
		
		empleadoService.deleteEmpleado(empleado.getId());
		
		verify(repository, times(1)).deleteById(empleado.getId());
	}

}

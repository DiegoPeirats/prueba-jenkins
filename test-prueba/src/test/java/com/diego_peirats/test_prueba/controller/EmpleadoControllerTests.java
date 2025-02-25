package com.diego_peirats.test_prueba.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.diego_peirats.test_prueba.model.Empleado;
import com.diego_peirats.test_prueba.service.EmpleadoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class EmpleadoControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmpleadoService service;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void guardarEmpleado() throws JsonProcessingException, Exception {
		
		Empleado empleado = new Empleado(1L, "Mario", "Mariano", "melagarraconlamano@gmail.com");
		
		given(service.saveEmpleado(any(Empleado.class)))
		.willAnswer((invocation) -> invocation.getArgument(0));
		
		ResultActions response = mockMvc.perform(post("/empleados/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(empleado)));
		
		response.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.nombre", is(empleado.getNombre())))
		.andExpect(jsonPath("$.apellido", is(empleado.getApellido())))
		.andExpect(jsonPath("$.email", is(empleado.getEmail())));
		
	}
	
	@Test
	void getAll() throws Exception {
		Empleado emp1 = new Empleado("chcho", "loko", "dnd@vas.joe");
		Empleado emp2 = new Empleado("chichi", "liki", "dind@vas.joe");
		Empleado emp3 = new Empleado("chacho", "laka", "dand@vas.joe");
		
		List<Empleado> empleados = new ArrayList<Empleado>();
		Collections.addAll(empleados , emp1,emp2,emp3);
		
		given(service.getAllEmpleado()).willReturn(empleados);
		
		ResultActions response = mockMvc.perform(get("/empleados/all"));
		
		response.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.size()", is(empleados.size())));
	}
	
	@Test
	void getById() throws Exception {
		Empleado emp = new Empleado(1L,"luca", "modric", "mdri@gmail.com");
		
		given(service.getEmpleadoById(emp.getId())).willReturn(Optional.of(emp));
		
		ResultActions response = mockMvc.perform(get("/empleados/{id}", emp.getId()));
		
		response.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.nombre", is(emp.getNombre())))
			.andExpect(jsonPath("$.apellido", is(emp.getApellido())))
			.andExpect(jsonPath("$.email", is(emp.getEmail())));
	}
	
	@Test
	void getByIdNoEncontrado() throws Exception {
		Empleado emp = new Empleado(1L,"luca", "modric", "mdri@gmail.com");
		
		given(service.getEmpleadoById(emp.getId())).willReturn(Optional.empty());
		
		ResultActions response = mockMvc.perform(get("/empleados/{id}", emp.getId()));
		
		response.andExpect(status().isNotFound())
			.andDo(print());
	}
	
	
	@Test
	void updateEmpleado() throws Exception{
		
		Long id = 1L;

		Empleado empGuardado = new Empleado("luca", "modric", "mdri@gmail.com");
		Empleado empActualizado = new Empleado("lucarlinhos", "modric", "mdri@gmail.com");
		
		given(service.getEmpleadoById(id)).willReturn(Optional.of(empGuardado));
		
		given(service.updateEmpleado(any(Empleado.class)))
			.willAnswer((invocation) -> invocation.getArgument(0));
		
		ResultActions response = mockMvc.perform(put("/empleados/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(empActualizado)));
		
		response.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.nombre", is(empActualizado.getNombre())))
			.andExpect(jsonPath("$.apellido", is(empActualizado.getApellido())))
			.andExpect(jsonPath("$.email", is(empActualizado.getEmail())));
	}
	
	@Test
	void eliminarEmpleado() throws Exception{
		Long id = 1L;
		
		BDDMockito.willDoNothing().given(service).deleteEmpleado(id);
		
		ResultActions response = mockMvc.perform(delete("/empleados/{id}", id));
		
		response.andExpect(status().isAccepted())
			.andDo(print());
	}
	

}

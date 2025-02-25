package com.diego_peirats.test_prueba;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diego_peirats.test_prueba.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{

	Optional<Empleado> findByEmail(String email);
}

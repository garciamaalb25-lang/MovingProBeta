package com.salesianostriana.dam.movingprobeta.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesianostriana.dam.movingprobeta.model.Operario;

// Repositorio para la entidad Operario, con consultas personalizadas para buscar operarios experimentados y activos
public interface OperarioRepository extends JpaRepository<Operario, Long> {

	@Query("SELECT o FROM Operario o WHERE o.experiencia >= :anios ORDER BY o.experiencia DESC")
	List<Operario> findOperariosExperimentados(@Param("anios") int anios);

	@Query("SELECT o FROM Operario o WHERE SIZE(o.mudanzas) > 0")
	List<Operario> findOperariosActivos();
}
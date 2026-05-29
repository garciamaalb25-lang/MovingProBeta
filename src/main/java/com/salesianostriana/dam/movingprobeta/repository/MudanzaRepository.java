package com.salesianostriana.dam.movingprobeta.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesianostriana.dam.movingprobeta.model.Mudanza;

import org.springframework.data.repository.query.Param;


public interface MudanzaRepository extends JpaRepository<Mudanza, Long> {

    @Query("SELECT m FROM Mudanza m WHERE m.fecha = :fecha")
    List<Mudanza> findByFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT m FROM Mudanza m WHERE m.fecha >= :fechaInicio AND m.fecha <= :fechaFin")
    List<Mudanza> findByFechaEntreRango(@Param("fechaInicio") LocalDate fechaInicio,
                                        @Param("fechaFin") LocalDate fechaFin);
}
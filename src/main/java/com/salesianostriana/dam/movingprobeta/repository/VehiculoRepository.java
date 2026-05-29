package com.salesianostriana.dam.movingprobeta.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesianostriana.dam.movingprobeta.model.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    @Query("SELECT v FROM Vehiculo v WHERE v.disponible = false")
    List<Vehiculo> findVehiculosNoDisponibles();

    @Query("SELECT v FROM Vehiculo v WHERE v.capacidad >= :capacidadMinima")
    List<Vehiculo> findByCapacidadMinima(@Param("capacidadMinima") double capacidadMinima);
}
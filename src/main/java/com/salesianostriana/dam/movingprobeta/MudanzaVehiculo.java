package com.salesianostriana.dam.movingprobeta;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MudanzaVehiculo {

    @Id @GeneratedValue
    private Long idMudanzaVehiculo;

    @ManyToOne
    @JoinColumn(name = "mudanza_id")
    private Mudanza mudanza;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @Enumerated(EnumType.STRING)
    private EstadoMudanza estado;

    private String observaciones;
    private LocalDateTime fechaAsignacion;
    private LocalDateTime fechaLiberacion;
}
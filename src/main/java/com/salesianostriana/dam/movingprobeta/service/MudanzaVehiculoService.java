package com.salesianostriana.dam.movingprobeta.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.salesianostriana.dam.movingprobeta.EstadoMudanza;
import com.salesianostriana.dam.movingprobeta.Mudanza;
import com.salesianostriana.dam.movingprobeta.MudanzaVehiculo;
import com.salesianostriana.dam.movingprobeta.Vehiculo;
import com.salesianostriana.dam.movingprobeta.exception.CapacidadExcedidaException;
import com.salesianostriana.dam.movingprobeta.exception.VehiculoNoDisponibleException;
import com.salesianostriana.dam.movingprobeta.repository.MudanzaVehiculoRepository;
import com.salesianostriana.dam.movingprobeta.repository.MudanzaRepository;
import com.salesianostriana.dam.movingprobeta.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MudanzaVehiculoService {

    private final MudanzaVehiculoRepository mudanzaVehiculoRepository;
    private final MudanzaRepository mudanzaRepository;
    private final VehiculoRepository vehiculoRepository;

    public List<MudanzaVehiculo> findAll() {
        return mudanzaVehiculoRepository.findAll();
    }

    public MudanzaVehiculo findById(Long id) {
        return mudanzaVehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada con id: " + id));
    }

    public MudanzaVehiculo save(MudanzaVehiculo mudanzaVehiculo) {
        return mudanzaVehiculoRepository.save(mudanzaVehiculo);
    }

    public void deleteById(Long id) {
        MudanzaVehiculo mv = findById(id);
        Vehiculo vehiculo = mv.getVehiculo();
        vehiculo.setDisponible(true);
        vehiculoRepository.save(vehiculo);
        mudanzaVehiculoRepository.deleteById(id);
    }

    public MudanzaVehiculo asignarVehiculo(Long mudanzaId, Long vehiculoId,
                                            double pesoMudanza, String observaciones) {
        Mudanza mudanza = mudanzaRepository.findById(mudanzaId)
                .orElseThrow(() -> new RuntimeException("Mudanza no encontrada"));

        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        if (!vehiculo.isDisponible()) {
            throw new VehiculoNoDisponibleException(vehiculo.getMatricula());
        }

        if (pesoMudanza > vehiculo.getCapacidad()) {
            throw new CapacidadExcedidaException(vehiculo.getCapacidad());
        }

        vehiculo.setDisponible(false);
        vehiculoRepository.save(vehiculo);

        MudanzaVehiculo mv = MudanzaVehiculo.builder()
                .mudanza(mudanza)
                .vehiculo(vehiculo)
                .estado(EstadoMudanza.EN_ORIGEN)
                .observaciones(observaciones)
                .fechaAsignacion(LocalDateTime.now())
                .build();

        return mudanzaVehiculoRepository.save(mv);
    }

    public MudanzaVehiculo cambiarEstado(Long id, EstadoMudanza nuevoEstado) {
        MudanzaVehiculo mv = findById(id);
        mv.setEstado(nuevoEstado);

        if (nuevoEstado == EstadoMudanza.TERMINADO) {
            mv.setFechaLiberacion(LocalDateTime.now());
            mv.getVehiculo().setDisponible(true);
            vehiculoRepository.save(mv.getVehiculo());
        }

        return mudanzaVehiculoRepository.save(mv);
    }
}
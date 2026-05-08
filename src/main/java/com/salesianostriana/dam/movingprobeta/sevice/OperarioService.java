package com.salesianostriana.dam.movingprobeta.sevice;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.salesianostriana.dam.movingprobeta.Operario;
import com.salesianostriana.dam.movingprobeta.repository.OperarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OperarioService {

    private final OperarioRepository operarioRepository;

    public List<Operario> findAll() {
        return operarioRepository.findAll();
    }

    public Operario findById(Long id) {
        return operarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Operario no encontrado con id: " + id));
    }

    public Operario save(Operario operario) {
        return operarioRepository.save(operario);
    }

    public void deleteById(Long id) {
        operarioRepository.deleteById(id);
    }

    public List<Operario> findOperariosExperimentados(int anios) {
        return operarioRepository.findAll()
                .stream()
                .filter(o -> o.getExperiencia() >= anios)
                .sorted((o1, o2) -> o2.getExperiencia() - o1.getExperiencia())
                .collect(Collectors.toList());
    }
}
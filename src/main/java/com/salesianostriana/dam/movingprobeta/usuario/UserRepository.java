package com.salesianostriana.dam.movingprobeta.usuario;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio para la entidad User, con un método personalizado para buscar por nombre de usuario
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
}
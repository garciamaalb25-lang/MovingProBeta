package com.salesianostriana.dam.movingprobeta.usuario;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDataSeed {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    @PostConstruct
    public void init() {
        User admin = User.builder()
                .email("admin@movingpro.com")
                .username("admin")
                .fullname("Administrador")
                .password(encoder.encode("admin"))
                .role(UserRole.ADMIN)
                .build();
        repo.save(admin);

        User user = User.builder()
                .email("user@movingpro.com")
                .username("user")
                .fullname("Usuario")
                .password(encoder.encode("user"))
                .role(UserRole.OPERADOR)
                .build();
        repo.save(user);
    }
}
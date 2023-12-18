package dev.acobano.springsecurity.basicauth.repositorios;

import dev.acobano.springsecurity.basicauth.modelo.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}

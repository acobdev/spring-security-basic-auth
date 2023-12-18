package dev.acobano.springsecurity.basicauth.repositorios;

import dev.acobano.springsecurity.basicauth.modelo.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
}

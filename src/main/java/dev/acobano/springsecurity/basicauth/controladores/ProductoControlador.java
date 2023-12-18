package dev.acobano.springsecurity.basicauth.controladores;

import dev.acobano.springsecurity.basicauth.modelo.entidades.Producto;
import dev.acobano.springsecurity.basicauth.repositorios.ProductoRepositorio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoControlador
{
    @Autowired
    private ProductoRepositorio repositorio;

    @PreAuthorize("hasAuthority('LEER_PRODUCTO')")
    @GetMapping
    public ResponseEntity<List<Producto>> getListaProductos()
    {
        List<Producto> listaProductos = this.repositorio.findAll();

        if (Objects.isNull(listaProductos) || listaProductos.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(listaProductos);
    }

    @PreAuthorize("hasAuthority('GUARDAR_NUEVO_PRODUCTO')")
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto nuevoProducto)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.repositorio.save(nuevoProducto));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> gestorExcepcionesGenericas(Exception e, HttpServletRequest request)
    {
        Map<String, String> apiError = new HashMap<>();
        apiError.put("message", e.getLocalizedMessage());
        apiError.put("timestamp", new Date().toString());
        apiError.put("url", request.getRequestURL().toString());
        apiError.put("http-method", request.getMethod());

        if(e instanceof AccessDeniedException)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}

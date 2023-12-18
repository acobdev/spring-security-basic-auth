package dev.acobano.springsecurity.basicauth.controladores;

import dev.acobano.springsecurity.basicauth.dto.AutenticacionRequest;
import dev.acobano.springsecurity.basicauth.dto.AutenticacionResponse;
import dev.acobano.springsecurity.basicauth.servicios.AutenticacionServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticacionControlador
{
    @Autowired
    private AutenticacionServicio servicio;

    @PreAuthorize("permitAll")
    @PostMapping("/login")
    public ResponseEntity<AutenticacionResponse> accederSistema(
            @Valid @RequestBody AutenticacionRequest dtoEntrada
    ) {
        AutenticacionResponse jwtDto = servicio.registrarse(dtoEntrada);
        return ResponseEntity.ok(jwtDto);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/publico")
    public ResponseEntity<String> getEndpointPublico()
    {
        return ResponseEntity.ok("El acceso a este endpoint es público, se puede entrar sin autenticarse anteriormente en el sistema.");
    }
}

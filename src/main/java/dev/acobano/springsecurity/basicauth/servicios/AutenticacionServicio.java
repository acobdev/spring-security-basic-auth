package dev.acobano.springsecurity.basicauth.servicios;

import dev.acobano.springsecurity.basicauth.dto.AutenticacionRequest;
import dev.acobano.springsecurity.basicauth.dto.AutenticacionResponse;
import dev.acobano.springsecurity.basicauth.modelo.entidades.Usuario;
import dev.acobano.springsecurity.basicauth.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AutenticacionServicio
{
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private JwtServicio jwtServicio;

    public AutenticacionResponse registrarse(AutenticacionRequest authRequest)
    {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()
        );

        authManager.authenticate(authToken);

        Usuario usuario = usuarioRepositorio.findByUsername(authRequest.getUsername()).get();

        String jwt = jwtServicio.generarToken(usuario, generarExtraClaims(usuario));
        AutenticacionResponse jwtDto = new AutenticacionResponse(jwt);
        return jwtDto;
    }

    private Map<String, Object> generarExtraClaims(Usuario usuario)
    {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("email", usuario.getEmail());
        extraClaims.put("role", usuario.getRol().name());
        extraClaims.put("permissions", usuario.getAuthorities());
        return extraClaims;
    }
}

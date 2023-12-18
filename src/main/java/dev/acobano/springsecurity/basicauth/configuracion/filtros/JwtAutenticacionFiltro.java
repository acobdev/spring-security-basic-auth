package dev.acobano.springsecurity.basicauth.configuracion.filtros;

import dev.acobano.springsecurity.basicauth.modelo.entidades.Usuario;
import dev.acobano.springsecurity.basicauth.repositorios.UsuarioRepositorio;
import dev.acobano.springsecurity.basicauth.servicios.JwtServicio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAutenticacionFiltro extends OncePerRequestFilter
{
    @Autowired
    private JwtServicio jwtServicio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        //1. Obtener el header que contiene el JWT:
        String authHeader = request.getHeader("Authorization"); //Beader + ' ' + JWT

        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request, response);
            return;
        }

        //2. Obtener el JWT desde el header:
        String jwt = authHeader.split(" ")[1];

        //3. Obtener subject/username desde el JWT:
        String username = jwtServicio.extraerUsername(jwt);

        //4. Settear un objeto de la clase Authentication dentro del SecurityContext:
        Usuario usuario = usuarioRepositorio.findByUsername(username).get();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, usuario.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //5. Ejecutar el resto de filtros:
        filterChain.doFilter(request, response);
    }
}

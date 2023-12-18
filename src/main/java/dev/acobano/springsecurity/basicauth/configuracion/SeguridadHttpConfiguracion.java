package dev.acobano.springsecurity.basicauth.configuracion;

import dev.acobano.springsecurity.basicauth.configuracion.filtros.JwtAutenticacionFiltro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SeguridadHttpConfiguracion
{
    @Autowired
    private AuthenticationProvider authProvider;

    @Autowired
    private JwtAutenticacionFiltro jwtAuthFiltro;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
                .csrf( csrfConfig -> csrfConfig.disable() )
                .sessionManagement( sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFiltro, UsernamePasswordAuthenticationFilter.class)

                .build();
    }
}

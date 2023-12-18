package dev.acobano.springsecurity.basicauth.servicios;

import dev.acobano.springsecurity.basicauth.modelo.entidades.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServicio
{
    @Value("${seguridad.jwt.minutos-expiracion}")
    private long MINUTOS_EXPIRACION;

    @Value("${seguridad.jwt.clave-secreta}")
    private String CLAVE_SECRETA;

    public String generarToken(Usuario usuario, Map<String, Object> extraClaims)
    {
        Date fechaEmision = new Date(System.currentTimeMillis());
        Date fechaExpiracion = new Date(fechaEmision.getTime() + (MINUTOS_EXPIRACION * 60 * 1000));

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(usuario.getUsername())
                .setIssuedAt(fechaEmision)
                .setExpiration(fechaExpiracion)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(generarClaveSecreta(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key generarClaveSecreta()
    {
        byte[] claveSecretaByte = Decoders.BASE64.decode(CLAVE_SECRETA);
        return Keys.hmacShaKeyFor(claveSecretaByte);
    }

    /**
     * Método que valida tres cosas:
     * 1. Que el JWT tenga el formato correcto.
     * 2. Que la fecha actual sea menor a la fecha de expedición.
     * 3. Que la firma recibida sea válida.
     *
     * @param jwt
     * @return
     */
    public String extraerUsername(String jwt)
    {
        return extraerTodasClaims(jwt).getSubject();
    }

    private Claims extraerTodasClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generarClaveSecreta()).build()
                .parseClaimsJws(jwt).getBody();
    }
}

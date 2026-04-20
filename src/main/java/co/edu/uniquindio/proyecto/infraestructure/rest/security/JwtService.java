package co.edu.uniquindio.proyecto.infraestructure.rest.security;

import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.security.Key;


import java.util.Date;

import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generarToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tipoUsuario", usuario.getTipoUsuario().name());
        claims.put("nombre", usuario.getNombre());
        claims.put("tipoDocumento", usuario.getIdentificacion().tipo().name());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getIdentificacion().numero())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extraerUsuarioId(String token) {
        return extraerClaims(token).getSubject();
    }

    public String extraerTipoDocumento(String token) {
        return extraerClaims(token).get("tipoDocumento", String.class);
    }

    public String extraerTipoUsuario(String token) {
        return extraerClaims(token).get("tipoUsuario", String.class);
    }

    public boolean tokenValido(String token) {
        try {
            extraerClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extraerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}

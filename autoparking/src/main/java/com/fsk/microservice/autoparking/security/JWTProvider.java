package com.fsk.microservice.autoparking.security;

import com.fsk.microservice.autoparking.entity.UserRoles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JWTProvider {
    private String secret;
    private long validity;

    @Autowired
    public JWTProvider(@Value("${security.jwt.secret}") String secret,
                       @Value("${security.jwt.expiration}") long validity) {
        this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
        this.validity = validity;
    }

    public String createToken(String userName, List<UserRoles> roles) {
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("roles", roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole()))
                .filter(Objects::nonNull).collect(Collectors.toList()));
        return Jwts.builder().setClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(validity)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserName(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public List<GrantedAuthority> getRoles(String token){
        List<Map<String, String>>  roleClaims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody()
                .get("roles", List.class);
        return roleClaims.stream().map(i -> new SimpleGrantedAuthority(i.get("authority")))
                .collect(Collectors.toList());
    }
}

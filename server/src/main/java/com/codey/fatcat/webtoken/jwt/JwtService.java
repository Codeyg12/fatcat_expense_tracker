package com.codey.fatcat.webtoken.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String SECRET;
  @Value("${jwt.expiration}")
  private long VALIDITY;

  public String generateToken(UserDetails userDetails) {
    Map<String, String> claims = new HashMap<>();
    claims.put("username", userDetails.getUsername());
    return Jwts.builder()
        .claims(claims)
        .subject(userDetails.getUsername())
        .issuedAt(Date.from(Instant.now()))
        .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
        .signWith(generateKey())
        .compact();
  }

  private SecretKey generateKey() {
    byte[] decodedKey = Base64.getDecoder().decode(SECRET);
    return Keys.hmacShaKeyFor(decodedKey);
  }

  public String extractUsername(String jwt) {
    Claims claims = getClaims(jwt);
    return claims.getSubject();
  }

  private Claims getClaims(String jwt) {
    return Jwts.parser()
        .verifyWith(generateKey())
        .build()
        .parseSignedClaims(jwt)
        .getPayload();
  }

  public boolean isTokenValid(String jwt) {
    Claims claims = getClaims(jwt);
    return claims.getExpiration().after(Date.from(Instant.now()));
  }
}

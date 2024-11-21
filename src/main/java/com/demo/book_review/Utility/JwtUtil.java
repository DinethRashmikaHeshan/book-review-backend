package com.demo.book_review.Utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "3af8dc82afec46497bf4bfb93d8674507429063ae13ee8b08b19fe5464e0b318108b6160c3fb62f37c124e49e0a977a7bec76465c663964fe4abebdbeee02d1682d94f8a1aed64c910244262113f45069d3728d83d161fc412f1d6144a9fb1ac25f51f65aa09db87c93fd90301ee1dd904f20323536c65a1f9cd94e45bfa5df44264f5c0616706da8169261991d8a035f8e1f06b6af4185ab128bb995aea9356a2b8251e7545305fca4f1b18e2694e74d4ea7857af91cf7dca853aa67fa5c03863c7d0df7b76890436fbcc2ba086b39bed9f97e822f263b046de3877c01dbe5472228a9179f4f7053fc793b7db6ed41dabe9c8c12c0757cf0a46198595199749";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}


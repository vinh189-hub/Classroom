package com.example.classroom.config;


import com.example.classroom.services.AuthService;
import com.example.classroom.services.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JWTService {

    private Logger logger = LoggerFactory.getLogger(AuthService.class);
    private static final String SECRET_KEY = System.getenv("SECRET_KEY");
    public String extractUserID(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(Long userID){
        logger.info("Lozzzzzzzzzzzzzzzz");
        String userId = Long.toString(userID);
        return generateToken(new HashMap<>(), userId);
    }

    public String generateToken(Map<String, Objects> claims, String userId){
        return
                Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 600 * 24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    public boolean isValidToken(String token, MyUserDetails userDetails){
        final String userID = extractUserID(token);
        return userID.equals(Long.toString(userDetails.getId())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}

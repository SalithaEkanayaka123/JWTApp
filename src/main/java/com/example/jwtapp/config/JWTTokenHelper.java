package com.example.jwtapp.config;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTTokenHelper {

    //need to check
    @Value("${jwt.auth.app}")
    private String appName;//which app token has been issued

    @Value("${jwt.auth.secret_key}")
    private String secreKey;// useful when you are validating the token

    //need to check
    @Value("${jwt.auth.expires_in}")
    private int expiresIn;//expire time

    //need to check
    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

//    public String getUsernameFromToken (String token){
//        String username;
//        try{
//            final Claims claims = this.getAllClaimsFromToken(token);
//            username = claims.getSubject();
//        }catch (Exception e){
//            username = null;
//        }
//        return username;
//    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(secreKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String generateToken (String username) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return Jwts.builder()
                .setIssuer(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SIGNATURE_ALGORITHM, secreKey)
                .compact();
    }




    private Claims getAllClaimsFromToken (String token){
        Claims claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(secreKey)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            claims = null;
        }
        return claims;

    }



    public Boolean validateToken(String token, UserDetails employeeDetails){
        final String username = extractUsername(token);
        return (username.equals(employeeDetails.getUsername()) && !isTokenExpired(token));
    }





}

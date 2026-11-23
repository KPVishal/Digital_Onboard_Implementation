package com.bijlipay.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
public class JwtUtil {

    @Value("${app.secret}")
    private String secret;


    //1.Generate Token
    public String generateToken(String subject){
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("Vaishu")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(15)))
                .signWith(SignatureAlgorithm.HS512,secret.getBytes())
                .compact();

    }


    //2.Read Claims
    public Claims getClaim(String token){

        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    //3.Read Exp date
    public Date getExpDate(String token){
        return getClaim(token).getExpiration();
    }




    //4.Read subject/username
    public String getUsername(String token){
        return getClaim(token).getSubject();
    }

    //5.Validate Exp Date
    public boolean isTokenExp(String token){
        Date expdate=getExpDate(token);
        return expdate.before(new Date(System.currentTimeMillis()));
    }

    //6.Validate username in token and database,expDate
    public boolean validateToken(String token,String username){
        String tokenUserName=getUsername(token);
        return (username.equals(tokenUserName) && !isTokenExp(token));
    }


}

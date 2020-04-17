package com.ascending.training.club.service;

import com.ascending.training.club.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
public class JWTService {
    private final String SECRET_KEY="weicao-project";
    private final String ISSUER="com.ascending";
    private final long EXPIRATION_TIME=86400*1000;

    private Logger logger= LoggerFactory.getLogger(getClass());

    public String generateToken(User u){
        //JWT signature algorithm uses to sign the token
        SignatureAlgorithm signatureAlgorithm=SignatureAlgorithm.HS256;

        //Sign JWT with SECRET_KEY
        byte[] apiKeySecretBytes= DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey=new SecretKeySpec(apiKeySecretBytes,signatureAlgorithm.getJcaName());

        //define claims
        Claims claims= Jwts.claims();
        claims.setId(String.valueOf(u.getId()));
        claims.setSubject(u.getName());
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setIssuer(ISSUER);
        claims.setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME));

        //combine three parts above to generate jwt token
        JwtBuilder builder=Jwts.builder().setClaims(claims).signWith(signatureAlgorithm,signingKey);
        return builder.compact();
    }

    public Claims decpytToken(String token){
        Claims claims=Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).parseClaimsJws(token).getBody();
        logger.info("Claims: "+claims.toString());
        return claims;
    }
}

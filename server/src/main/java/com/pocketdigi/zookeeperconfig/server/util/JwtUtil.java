package com.pocketdigi.zookeeperconfig.server.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import java.time.Instant;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author fhp
 * @date 2019-08-08
 */
@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    String secret;

    public String generateToken(String username) {
        DefaultClaims claims = new DefaultClaims();
        claims.setAudience(username);
        claims.setExpiration(new Date(Instant.now().toEpochMilli() + 10 * 60 * 60 * 1000));
        String token = Jwts.builder().setClaims(claims).signWith(
            SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 验证JWT
     */
    public boolean validateToken(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        if(claimsFromToken==null) {
            return false;
        }
        Date expiration = claimsFromToken.getExpiration();
        return expiration.after(new Date());
    }


    private Claims getClaimsFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
            return claims;
        } catch (Exception e) {
            log.error("token错误",e);
        }
        return null;
    }


}

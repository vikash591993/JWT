import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

public class JWTGenerateValidateHMAC {
    public static void main(String[] args) {
        String jwt = createJwtSignedHMAC();
        System.out.println("Jwt" + jwt);
        Jws<Claims> token = parseJwt(jwt);
        System.out.println(token.getBody());
    }

    public static Jws<Claims> parseJwt(String jwtString){
       String secret =  "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
       Key hmackey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
       Jws<Claims> jwt = Jwts.parserBuilder()
               .setSigningKey(hmackey)
               .build()
               .parseClaimsJws(jwtString);
       return jwt;
    }

    public static String createJwtSignedHMAC(){
        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
        Instant now = Instant.now();

        String jwtToken = Jwts.builder()
                .claim("name", "Vikash Kumar")
                .claim("email", "vikashkumar599193@gmail.com")
                .claim("city", "Gaya")
                .setSubject("jane")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();
        return jwtToken;
    }
}

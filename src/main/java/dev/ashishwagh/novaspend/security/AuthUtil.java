package dev.ashishwagh.novaspend.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.ashishwagh.novaspend.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class AuthUtil {
	@Value("${jwt.secretKey}")
	private String secretKey;
	private SecretKey getSecretKey()
	{
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}
	public String generateJWTToken(User user)
	{
		return Jwts.builder()
				.subject(user.getEmail())
				.claim("UserId",user.getId().toString())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+1000*60*20))
				.signWith(getSecretKey())
				.compact();
	}
	public String getUserEmail(String token) {
		return Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	public boolean isTokenExpired(String token) {
	    Date expiration = Jwts.parser()
	        .verifyWith(getSecretKey())
	        .build()
	        .parseSignedClaims(token)
	        .getPayload()
	        .getExpiration();
	    return expiration.before(new Date());
	}

}

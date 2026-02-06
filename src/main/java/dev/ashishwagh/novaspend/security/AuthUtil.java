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
	
	@Value("${jwt.accessTokenExpiry}")
	private long jwtExpiry;
	private SecretKey getSecretKey()
	{
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}
	public String generateJWTToken(User user)
	{
		return Jwts.builder()
				.subject(user.getEmail())
				.claim("user_id",user.getId().toString())
				.claim("role","ROLE_"+user.getRole().name())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+jwtExpiry*1000))
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
	public String getUserRole(String token) {
		return Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.get("role",String.class);
	}
}

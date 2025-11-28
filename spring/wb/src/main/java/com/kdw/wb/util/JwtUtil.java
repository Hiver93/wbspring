package com.kdw.wb.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final String SECRET_STR;
	private final SecretKey SECRET_KEY;
	private final long ACCESS_EXP;
	private final long REFRESH_EXP;
	
	public JwtUtil(
			 @Value("${jwt.properties.secretkey}") String secretKey,
			 @Value("${jwt.properties.access-token-expiration-time-in-milliseconds}")long accessExp,
			 @Value("${jwt.properties.access-token-expiration-time-in-milliseconds}")long refreshExp) {
		SECRET_STR = secretKey;
		ACCESS_EXP = accessExp;
		REFRESH_EXP = refreshExp;
		SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STR.getBytes());
		System.out.println(accessExp);
	}
	
	public String generateAccessToken() {
		Date issued = new Date();
		Date expired = new Date(issued.getTime() + ACCESS_EXP);
	
		return Jwts.builder()
				.signWith(SECRET_KEY)
				.issuedAt(issued)
				.expiration(expired)
				.compact();
	}
	
	public String generateRefreshToken() {
		Date issued = new Date();
		Date expired = new Date(issued.getTime() + REFRESH_EXP);
	
		return Jwts.builder()
				.signWith(SECRET_KEY)
				.issuedAt(issued)
				.expiration(expired)
				.compact();
	}
	
	
	private Claims getClaims(String token) {
		return (Claims)Jwts.parser().verifyWith(SECRET_KEY).build().parse(token).getPayload();
	}
}
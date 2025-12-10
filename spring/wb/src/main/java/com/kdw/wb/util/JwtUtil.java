package com.kdw.wb.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kdw.wb.domain.auth.Auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final String SECRET_STR;
	private final SecretKey SECRET_KEY;
	
	public JwtUtil(
			 @Value("${jwt.properties.secretkey}") String secretKey) {
		SECRET_STR = secretKey;
		SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STR.getBytes());
	}
	
	public String generateToken(Integer userId, Long exp) {
		Date issued = new Date();
		Date expired = new Date(issued.getTime() + exp);
	
		return Jwts.builder()
				.claim("userId", userId)
				.signWith(SECRET_KEY)
				.issuedAt(issued)
				.expiration(expired)
				.compact();
	}
	
//	public String generateRefreshToken(Auth auth) {
//		Date issued = new Date();
//		Date expired = new Date(issued.getTime() + REFRESH_EXP);
//	
//		return Jwts.builder()
//				.claim("userId", auth.getUserId())
//				.signWith(SECRET_KEY)
//				.issuedAt(issued)
//				.expiration(expired)
//				.compact();
//	}
	
	public Auth getAuth(String token) {
		return Auth.builder().userId((Integer)this.getClaims(token).get("userId")).build();
	}
	
	private Claims getClaims(String token) {
		return (Claims)Jwts.parser().verifyWith(SECRET_KEY).build().parse(token).getPayload();
	}
}
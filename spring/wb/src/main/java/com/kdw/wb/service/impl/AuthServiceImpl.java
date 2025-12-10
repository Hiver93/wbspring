package com.kdw.wb.service.impl;


import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kdw.wb.domain.auth.Auth;
import com.kdw.wb.error.ErrorCode;
import com.kdw.wb.error.WhiteboardException;
import com.kdw.wb.service.AuthService;
import com.kdw.wb.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthServiceImpl implements AuthService {

	private final String COOKIE_PATH = "/";
	private final long ACCESS_EXP;
	private final long REFRESH_EXP;
	private final JwtUtil jwtUtil;
	private final boolean httpOnly;
	private final boolean secure;
	
	public AuthServiceImpl( 
			JwtUtil jwtUtil,
			@Value("${jwt.properties.access-token-expiration-time-in-milliseconds}")long accessExp,
			@Value("${jwt.properties.access-token-expiration-time-in-milliseconds}")long refreshExp,
			@Value("${server.servlet.session.cookie.http-only}") boolean httpOnly,
			@Value("${server.servlet.session.cookie.secure}") boolean secure) {
		this.ACCESS_EXP = accessExp;
		this.REFRESH_EXP = refreshExp;
		this.jwtUtil = jwtUtil;
		this.httpOnly = httpOnly;
		this.secure = secure;
	}
	
	@Override
	public void setAuthentication(Integer userId) {
		String access = "Bearer " + this.jwtUtil.generateToken(userId, ACCESS_EXP);
		String refresh = this.jwtUtil.generateToken(userId, REFRESH_EXP);
		
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();
		
		Cookie cookie = new Cookie("refresh", refresh);
		cookie.setPath(COOKIE_PATH);
		cookie.setHttpOnly(this.httpOnly);
		cookie.setSecure(this.secure);
		
		response.addCookie(cookie);
		response.setHeader("Authorization", access);
	}

	@Override
	public boolean isAuthenticated() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		String token = request.getHeader("Authorization");
		if(token == null || token.equals("null")) {
			return false;
		}
		
		token = this.getTokenFromAccessToken(token);
		
		try {
			this.jwtUtil.getAuth(token);
			return true;
		}catch(ExpiredJwtException e) {
			throw new WhiteboardException(ErrorCode.EXPIRED_JWT);
		}catch(JwtException e) {
			throw new WhiteboardException(ErrorCode.INVALID_JWT);
		}
	}

	@Override
	public void removeAuthentication() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();
		Cookie cookie = Stream.of(request.getCookies())
				.filter(c->c.getName().equals("refresh"))
				.findAny().orElseGet(null);
		if(cookie != null) {
			cookie.setMaxAge(0);
			cookie.setPath(COOKIE_PATH); 
			response.addCookie(cookie);
		}
	}

	@Override
	public void refreshAuthentication() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		if(request.getCookies() == null) {
			throw new WhiteboardException(ErrorCode.AUTHENTICATION_REQUIRED);
		}
		
		String token = Stream.of(request.getCookies())
				.filter(c->c.getName().equals("refresh"))
				.findAny().orElseThrow(()->{throw new WhiteboardException(ErrorCode.AUTHENTICATION_REQUIRED);})
				.getValue();

		try {
			this.setAuthentication(this.jwtUtil.getAuth(token).getUserId());
		}catch(ExpiredJwtException e) {
			throw new WhiteboardException(ErrorCode.EXPIRED_JWT);
		}catch(JwtException e) {
			throw new WhiteboardException(ErrorCode.INVALID_JWT);
		}
		
	}

	@Override
	public Auth getAuthentication() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		String token = request.getHeader("Authorization");
		token = this.getTokenFromAccessToken(token);
		
		if(token == null || token.equals("null")) {
			throw new WhiteboardException(ErrorCode.AUTHENTICATION_REQUIRED);
		}
		
		try {
			return this.jwtUtil.getAuth(token);
		}catch (ExpiredJwtException e) {
			throw new WhiteboardException(ErrorCode.EXPIRED_JWT);
		}catch (JwtException e) {
			throw new WhiteboardException(ErrorCode.INVALID_JWT);
		}
	}
	
	private String getTokenFromAccessToken(String access) {
		
		if(!access.startsWith("Bearer ")) {
			throw new WhiteboardException(ErrorCode.INVALID_JWT);
		}
		
		return access.substring(7);
	}

}

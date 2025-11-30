package com.kdw.wb.service;

import com.kdw.wb.domain.auth.Auth;

public interface AuthService {

	public void setAuthentication(Integer userId);
	public boolean isAuthenticated();
	public void removeAuthentication();
	public void refreshAuthentication();
	public Auth getAuthentication();
}

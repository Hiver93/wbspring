package com.kdw.wb.service;

public interface AuthService {

	public void setAuthentication(String passkey);
	public boolean isAuthenticated();
	public void removeAuthentication();
	public void refreshAuthentication();
}

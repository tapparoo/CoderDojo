package com.skilldistillery.coderdojo.services;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}

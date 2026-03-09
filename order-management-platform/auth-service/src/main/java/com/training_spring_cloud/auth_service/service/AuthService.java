package com.training_spring_cloud.auth_service.service;

import com.training_spring_cloud.auth_service.dto.LoginRequest;
import com.training_spring_cloud.auth_service.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

}
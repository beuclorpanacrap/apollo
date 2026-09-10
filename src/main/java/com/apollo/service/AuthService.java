package com.apollo.service;

import com.apollo.dto.auth.AuthResponse;
import com.apollo.dto.auth.CurrentUserResponse;
import com.apollo.dto.auth.LoginRequest;
import com.apollo.dto.auth.RegisterDoctorRequest;
import com.apollo.dto.auth.RegisterPatientRequest;

import java.util.UUID;

public interface AuthService {

    AuthResponse registerPatient(RegisterPatientRequest request);

    AuthResponse registerDoctor(RegisterDoctorRequest request);

    AuthResponse login(LoginRequest request);

    CurrentUserResponse getCurrentUser(UUID userId);
}

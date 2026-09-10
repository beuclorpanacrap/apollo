package com.apollo.dto.auth;

import com.apollo.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Authentication response containing JWT token and user profile metadata")
public class AuthResponse {

    @Schema(description = "Signed JWT Bearer token", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;

    @Builder.Default
    @Schema(description = "Token type", example = "Bearer")
    private String tokenType = "Bearer";

    @Schema(description = "User unique identifier", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
    private UUID userId;

    @Schema(description = "Associated Patient or Doctor profile identifier", example = "b1ffcd88-8b1c-4fe7-cc7e-7cc8ce491b22")
    private UUID profileId;

    @Schema(description = "Account role", example = "ROLE_PATIENT")
    private Role role;

    @Schema(description = "User email address", example = "patient@example.com")
    private String email;

    @Schema(description = "User full display name", example = "Jane Doe")
    private String fullName;
}

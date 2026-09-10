package com.apollo.dto.auth;

import com.apollo.domain.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Details of the currently authenticated user")
public class CurrentUserResponse {

    @Schema(description = "User unique identifier")
    private UUID userId;

    @Schema(description = "Patient or Doctor profile unique identifier")
    private UUID profileId;

    @Schema(description = "User email address")
    private String email;

    @Schema(description = "User account role")
    private Role role;

    @Schema(description = "Full name of the user or doctor")
    private String fullName;

    @Schema(description = "Account creation timestamp")
    private Instant createdAt;
}

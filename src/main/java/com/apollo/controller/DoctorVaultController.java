package com.apollo.controller;

import com.apollo.dto.doctor.ClinicalEncounterResponse;
import com.apollo.dto.doctor.CreateEncounterRequest;
import com.apollo.dto.doctor.UnlockVaultRequest;
import com.apollo.dto.doctor.UnlockedVaultResponse;
import com.apollo.exception.ErrorResponse;
import com.apollo.security.CustomUserDetails;
import com.apollo.service.DoctorVaultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
@PreAuthorize("hasRole('DOCTOR')")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Doctor Operations", description = "Clinician endpoints for patient vault PIN handshake and append-only clinical encounter logging")
public class DoctorVaultController {

    private final DoctorVaultService doctorVaultService;

    @Operation(summary = "Unlock patient vault via 6-digit access PIN",
            description = "Validates and consumes a single-use 6-digit consultation access PIN. Unlocks patient demographics, baseline health records, and past consultation history for clinical review.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient vault unlocked successfully",
                    content = @Content(schema = @Schema(implementation = UnlockedVaultResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid, expired, or already used access PIN",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Missing or invalid JWT",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires ROLE_DOCTOR",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/vault/unlock")
    public ResponseEntity<UnlockedVaultResponse> unlockVault(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UnlockVaultRequest request) {
        UnlockedVaultResponse response = doctorVaultService.unlockVault(userDetails.getProfileId(), request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Log an append-only clinical consultation encounter",
            description = "Appends an immutable clinical encounter to the patient's record. Optionally records companion diagnoses as DOCTOR_VERIFIED conditions. Encounters are strictly immutable and cannot be edited or deleted once saved.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Clinical encounter successfully recorded",
                    content = @Content(schema = @Schema(implementation = ClinicalEncounterResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires ROLE_DOCTOR",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Patient or doctor profile not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/encounters")
    public ResponseEntity<ClinicalEncounterResponse> createEncounter(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CreateEncounterRequest request) {
        ClinicalEncounterResponse response = doctorVaultService.createEncounter(userDetails.getProfileId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

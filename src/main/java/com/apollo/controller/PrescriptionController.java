package com.apollo.controller;

import com.apollo.dto.prescription.PrescriptionResponse;
import com.apollo.dto.prescription.UpdatePrescriptionStatusRequest;
import com.apollo.exception.ErrorResponse;
import com.apollo.security.CustomUserDetails;
import com.apollo.service.PrescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/prescriptions")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR')")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Prescription Lifecycle", description = "Endpoints for managing prescription status lifecycle (fulfillment by patient, cancellation by doctor)")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @Operation(summary = "Update prescription lifecycle status",
            description = "Transitions prescription status. Patients can mark their prescriptions as FULFILLED (pharmacy dispensing); prescribing doctors can mark prescriptions as CANCELLED (dosage correction or adverse reaction). Requires profile ownership.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prescription status updated successfully",
                    content = @Content(schema = @Schema(implementation = PrescriptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status transition or validation failure",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Missing or invalid JWT",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - Not authorized to update this prescription",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Prescription not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{prescriptionId}/status")
    public ResponseEntity<PrescriptionResponse> updateStatus(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable UUID prescriptionId,
            @Valid @RequestBody UpdatePrescriptionStatusRequest request) {
        PrescriptionResponse response = prescriptionService.updateStatus(
                userDetails.getProfileId(),
                userDetails.getRole(),
                prescriptionId,
                request
        );
        return ResponseEntity.ok(response);
    }
}

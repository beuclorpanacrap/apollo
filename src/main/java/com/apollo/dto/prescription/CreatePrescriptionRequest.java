package com.apollo.dto.prescription;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payload for doctor prescription issuance")
public class CreatePrescriptionRequest {

    @NotNull(message = "Patient ID is required")
    @Schema(description = "UUID of the patient recipient", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
    private UUID patientId;

    @Schema(description = "Optional clinical encounter UUID if prescribed during a consultation", example = "b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22")
    private UUID encounterId;

    @NotBlank(message = "Medication name is required")
    @Size(max = 200, message = "Medication name must not exceed 200 characters")
    @Schema(description = "Name and strength of the prescribed drug", example = "Amoxicillin 500mg")
    private String medicationName;

    @NotBlank(message = "Dosage is required")
    @Size(max = 100, message = "Dosage must not exceed 100 characters")
    @Schema(description = "Dosage schedule", example = "1 capsule 3 times daily")
    private String dosage;

    @NotBlank(message = "Instructions are required")
    @Schema(description = "Instructions for patient and dispensing pharmacy", example = "Take with food for 7 days. Complete full course.")
    private String instructions;

    @NotNull(message = "Expiration date is required")
    @Future(message = "Expiration date must be in the future")
    @Schema(description = "Prescription validity expiration timestamp")
    private Instant expiresAt;
}

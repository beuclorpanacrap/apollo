package com.apollo.dto.prescription;

import com.apollo.domain.enums.PrescriptionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payload for prescription status lifecycle transition")
public class UpdatePrescriptionStatusRequest {

    @NotNull(message = "Status is required")
    @Schema(description = "Updated status for the prescription (FULFILLED by patient or CANCELLED by doctor)", example = "FULFILLED")
    private PrescriptionStatus status;
}

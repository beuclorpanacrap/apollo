package com.apollo.dto.vault;

import com.apollo.domain.enums.HealthConditionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Payload to record a patient-declared foundational health condition or allergy")
public class CreateHealthConditionRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    @Schema(example = "Penicillin Allergy")
    private String title;

    @NotNull(message = "Condition type is required")
    @Schema(example = "ALLERGY")
    private HealthConditionType type;

    @Schema(description = "Date when condition was recorded or diagnosed (defaults to current date if omitted)", example = "2023-01-15")
    private LocalDate dateRecorded;
}

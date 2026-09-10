package com.apollo.dto.doctor;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Condition to record during an encounter with verified doctor source")
public class DoctorConditionInput {

    @NotBlank(message = "Condition title is required")
    @Size(max = 200, message = "Condition title must not exceed 200 characters")
    @Schema(example = "Type 2 Diabetes Mellitus")
    private String title;

    @NotNull(message = "Condition type is required")
    @Schema(example = "CHRONIC_CONDITION")
    private HealthConditionType type;
}

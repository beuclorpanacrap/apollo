package com.apollo.dto.doctor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Payload to log an append-only clinical consultation encounter")
public class CreateEncounterRequest {

    @NotNull(message = "Patient ID is required")
    @Schema(description = "Patient profile unique identifier")
    private UUID patientId;

    @Size(max = 255, message = "Chief complaint must not exceed 255 characters")
    @Schema(example = "Persistent dry cough and mild chest tightness for 5 days")
    private String chiefComplaint;

    @NotBlank(message = "Diagnosis is required")
    @Size(max = 500, message = "Diagnosis must not exceed 500 characters")
    @Schema(example = "Acute Tracheobronchitis")
    private String diagnosis;

    @NotBlank(message = "Clinical notes are required")
    @Schema(example = "Lungs clear bilaterally on auscultation. Recommended hydration, rest, and short-term bronchodilator therapy.")
    private String clinicalNotes;

    @NotNull(message = "Encounter date is required")
    @PastOrPresent(message = "Encounter date cannot be in the future")
    @Schema(example = "2024-03-15")
    private LocalDate encounterDate;

    @Valid
    @Schema(description = "Optional list of conditions/allergies diagnosed during this encounter (automatically tagged as DOCTOR_VERIFIED)")
    private List<DoctorConditionInput> conditionsToAdd;
}

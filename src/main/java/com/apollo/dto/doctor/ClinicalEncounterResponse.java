package com.apollo.dto.doctor;

import com.apollo.dto.vault.HealthConditionResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Details of an appended clinical consultation encounter")
public class ClinicalEncounterResponse {

    @Schema(description = "Encounter unique identifier")
    private UUID id;

    @Schema(description = "Patient profile unique identifier")
    private UUID patientId;

    @Schema(description = "Attending doctor profile unique identifier")
    private UUID doctorId;

    @Schema(description = "Attending doctor's full name", example = "Dr. Gregory House")
    private String doctorName;

    @Schema(description = "Attending doctor's clinical specialty", example = "Diagnostics")
    private String doctorSpecialty;

    @Schema(description = "Patient chief complaint", example = "Persistent dry cough")
    private String chiefComplaint;

    @Schema(description = "Primary clinical diagnosis", example = "Acute Tracheobronchitis")
    private String diagnosis;

    @Schema(description = "Comprehensive clinical consultation notes")
    private String clinicalNotes;

    @Schema(description = "Date of consultation", example = "2024-03-15")
    private LocalDate encounterDate;

    @Schema(description = "Immutable creation timestamp")
    private Instant createdAt;

    @Schema(description = "Any companion health conditions recorded during this encounter with DOCTOR_VERIFIED status")
    private List<HealthConditionResponse> conditionsAdded;
}

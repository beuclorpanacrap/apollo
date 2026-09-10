package com.apollo.dto.vault;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Consolidated chronological view of the patient vault including baseline health data and clinical encounters")
public class PatientVaultTimelineResponse {

    @Schema(description = "Patient basic demographic profile")
    private PatientProfileSummaryDto patient;

    @Schema(description = "Baseline health conditions and allergies")
    private List<HealthConditionResponse> conditions;

    @Schema(description = "Chronological clinical consultations (sorted descending)")
    private List<ClinicalEncounterSummaryDto> encounters;
}

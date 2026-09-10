package com.apollo.dto.vault;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Chronological clinical consultation record summary")
public class ClinicalEncounterSummaryDto {

    @Schema(description = "Encounter unique identifier")
    private UUID id;

    @Schema(description = "Attending doctor's profile identifier")
    private UUID doctorId;

    @Schema(description = "Doctor full name and title", example = "Dr. Gregory House")
    private String doctorName;

    @Schema(description = "Doctor medical specialty", example = "Diagnostics")
    private String doctorSpecialty;

    @Schema(description = "Encounter consultation date", example = "2024-03-10")
    private LocalDate encounterDate;

    @Schema(description = "Primary diagnosis", example = "Acute Bronchitis")
    private String diagnosis;

    @Schema(description = "Clinical consultation notes entered by the doctor")
    private String clinicalNotes;

    @Schema(description = "Timestamp when recorded in vault")
    private Instant createdAt;
}

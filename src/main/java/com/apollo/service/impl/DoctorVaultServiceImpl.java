package com.apollo.service.impl;

import com.apollo.domain.entity.AccessGrant;
import com.apollo.domain.entity.ClinicalEncounter;
import com.apollo.domain.entity.DoctorProfile;
import com.apollo.domain.entity.HealthCondition;
import com.apollo.domain.entity.PatientProfile;
import com.apollo.domain.enums.HealthConditionType;
import com.apollo.domain.enums.SourceType;
import com.apollo.dto.doctor.ClinicalEncounterResponse;
import com.apollo.dto.doctor.CreateEncounterRequest;
import com.apollo.dto.doctor.DoctorConditionInput;
import com.apollo.dto.doctor.UnlockVaultRequest;
import com.apollo.dto.doctor.UnlockedVaultResponse;
import com.apollo.dto.vault.ClinicalEncounterSummaryDto;
import com.apollo.dto.vault.HealthConditionResponse;
import com.apollo.dto.vault.PatientProfileSummaryDto;
import com.apollo.exception.InvalidAccessGrantException;
import com.apollo.exception.ResourceNotFoundException;
import com.apollo.repository.AccessGrantRepository;
import com.apollo.repository.ClinicalEncounterRepository;
import com.apollo.repository.DoctorProfileRepository;
import com.apollo.repository.HealthConditionRepository;
import com.apollo.repository.PatientProfileRepository;
import com.apollo.service.DoctorVaultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorVaultServiceImpl implements DoctorVaultService {

    private final DoctorProfileRepository doctorProfileRepository;
    private final PatientProfileRepository patientProfileRepository;
    private final AccessGrantRepository accessGrantRepository;
    private final HealthConditionRepository healthConditionRepository;
    private final ClinicalEncounterRepository clinicalEncounterRepository;

    @Override
    @Transactional
    public UnlockedVaultResponse unlockVault(UUID doctorProfileId, UnlockVaultRequest request) {
        // Validate doctor profile
        doctorProfileRepository.findById(doctorProfileId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor profile not found: " + doctorProfileId));

        // Find active, unexpired, and unused grant
        AccessGrant grant = accessGrantRepository
                .findByAccessCodeAndIsUsedFalseAndExpiresAtAfter(request.getAccessCode().trim(), Instant.now())
                .orElseThrow(() -> new InvalidAccessGrantException("Invalid or expired consultation access PIN."));

        // Consume grant immediately upon handshake
        grant.setUsed(true);
        accessGrantRepository.save(grant);

        PatientProfile patient = grant.getPatient();

        PatientProfileSummaryDto patientSummary = PatientProfileSummaryDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .dateOfBirth(patient.getDateOfBirth())
                .bloodType(patient.getBloodType())
                .build();

        List<HealthCondition> allConditionsList = healthConditionRepository
                .findByPatientIdOrderByDateRecordedDescCreatedAtDesc(patient.getId());

        List<HealthConditionResponse> allConditions = allConditionsList.stream()
                .map(this::mapToHealthConditionResponse)
                .toList();

        List<HealthConditionResponse> allergies = allConditionsList.stream()
                .filter(c -> c.getType() == HealthConditionType.ALLERGY)
                .map(this::mapToHealthConditionResponse)
                .toList();

        List<HealthConditionResponse> chronicConditions = allConditionsList.stream()
                .filter(c -> c.getType() == HealthConditionType.CHRONIC_CONDITION)
                .map(this::mapToHealthConditionResponse)
                .toList();

        List<ClinicalEncounterSummaryDto> encounterHistory = clinicalEncounterRepository
                .findByPatientIdOrderByEncounterDateDescCreatedAtDesc(patient.getId())
                .stream()
                .map(this::mapToClinicalEncounterSummary)
                .toList();

        return UnlockedVaultResponse.builder()
                .patient(patientSummary)
                .allergies(allergies)
                .chronicConditions(chronicConditions)
                .allConditions(allConditions)
                .encounterHistory(encounterHistory)
                .build();
    }

    @Override
    @Transactional
    public ClinicalEncounterResponse createEncounter(UUID doctorProfileId, CreateEncounterRequest request) {
        DoctorProfile doctor = doctorProfileRepository.findById(doctorProfileId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor profile not found: " + doctorProfileId));

        PatientProfile patient = patientProfileRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient profile not found: " + request.getPatientId()));

        ClinicalEncounter encounter = ClinicalEncounter.builder()
                .patient(patient)
                .doctor(doctor)
                .chiefComplaint(request.getChiefComplaint() != null ? request.getChiefComplaint().trim() : null)
                .diagnosis(request.getDiagnosis().trim())
                .clinicalNotes(request.getClinicalNotes().trim())
                .encounterDate(request.getEncounterDate())
                .build();

        encounter = clinicalEncounterRepository.save(encounter);

        List<HealthConditionResponse> addedConditions = new ArrayList<>();
        if (request.getConditionsToAdd() != null && !request.getConditionsToAdd().isEmpty()) {
            for (DoctorConditionInput input : request.getConditionsToAdd()) {
                HealthCondition condition = HealthCondition.builder()
                        .patient(patient)
                        .title(input.getTitle().trim())
                        .type(input.getType())
                        .sourceType(SourceType.DOCTOR_VERIFIED) // Strictly DOCTOR_VERIFIED
                        .dateRecorded(request.getEncounterDate())
                        .build();

                condition = healthConditionRepository.save(condition);
                addedConditions.add(mapToHealthConditionResponse(condition));
            }
        }

        String doctorName = "Dr. " + doctor.getFirstName() + " " + doctor.getLastName();

        return ClinicalEncounterResponse.builder()
                .id(encounter.getId())
                .patientId(patient.getId())
                .doctorId(doctor.getId())
                .doctorName(doctorName)
                .doctorSpecialty(doctor.getSpecialty())
                .chiefComplaint(encounter.getChiefComplaint())
                .diagnosis(encounter.getDiagnosis())
                .clinicalNotes(encounter.getClinicalNotes())
                .encounterDate(encounter.getEncounterDate())
                .createdAt(encounter.getCreatedAt())
                .conditionsAdded(addedConditions)
                .build();
    }

    private HealthConditionResponse mapToHealthConditionResponse(HealthCondition condition) {
        return HealthConditionResponse.builder()
                .id(condition.getId())
                .title(condition.getTitle())
                .type(condition.getType())
                .sourceType(condition.getSourceType())
                .dateRecorded(condition.getDateRecorded())
                .createdAt(condition.getCreatedAt())
                .build();
    }

    private ClinicalEncounterSummaryDto mapToClinicalEncounterSummary(ClinicalEncounter encounter) {
        String docName = "Dr. " + encounter.getDoctor().getFirstName() + " " + encounter.getDoctor().getLastName();
        return ClinicalEncounterSummaryDto.builder()
                .id(encounter.getId())
                .doctorId(encounter.getDoctor().getId())
                .doctorName(docName)
                .doctorSpecialty(encounter.getDoctor().getSpecialty())
                .encounterDate(encounter.getEncounterDate())
                .diagnosis(encounter.getDiagnosis())
                .clinicalNotes(encounter.getClinicalNotes())
                .createdAt(encounter.getCreatedAt())
                .build();
    }
}

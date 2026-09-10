package com.apollo.service;

import com.apollo.dto.doctor.ClinicalEncounterResponse;
import com.apollo.dto.doctor.CreateEncounterRequest;
import com.apollo.dto.doctor.UnlockVaultRequest;
import com.apollo.dto.doctor.UnlockedVaultResponse;

import java.util.UUID;

public interface DoctorVaultService {

    UnlockedVaultResponse unlockVault(UUID doctorProfileId, UnlockVaultRequest request);

    ClinicalEncounterResponse createEncounter(UUID doctorProfileId, CreateEncounterRequest request);
}

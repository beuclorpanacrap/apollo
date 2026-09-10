package com.apollo.service;

import com.apollo.domain.enums.PrescriptionStatus;
import com.apollo.domain.enums.Role;
import com.apollo.dto.prescription.CreatePrescriptionRequest;
import com.apollo.dto.prescription.PrescriptionResponse;
import com.apollo.dto.prescription.UpdatePrescriptionStatusRequest;

import java.util.List;
import java.util.UUID;

public interface PrescriptionService {

    PrescriptionResponse issuePrescription(UUID doctorProfileId, CreatePrescriptionRequest request);

    List<PrescriptionResponse> getPatientPrescriptions(UUID patientProfileId, PrescriptionStatus status);

    PrescriptionResponse updateStatus(UUID profileId, Role role, UUID prescriptionId, UpdatePrescriptionStatusRequest request);
}

package com.apollo.repository;

import com.apollo.domain.entity.PatientProfile;
import com.apollo.domain.entity.Prescription;
import com.apollo.domain.enums.PrescriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {
    List<Prescription> findByPatientOrderByIssuedAtDesc(PatientProfile patient);
    List<Prescription> findByPatientIdOrderByIssuedAtDesc(UUID patientId);
    List<Prescription> findByPatientIdAndStatus(UUID patientId, PrescriptionStatus status);
    List<Prescription> findByEncounterId(UUID encounterId);
}

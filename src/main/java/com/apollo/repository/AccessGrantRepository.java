package com.apollo.repository;

import com.apollo.domain.entity.AccessGrant;
import com.apollo.domain.entity.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccessGrantRepository extends JpaRepository<AccessGrant, UUID> {
    Optional<AccessGrant> findByAccessCodeAndIsUsedFalseAndExpiresAtAfter(String accessCode, Instant now);
    Optional<AccessGrant> findTopByPatientOrderByCreatedAtDesc(PatientProfile patient);
    Optional<AccessGrant> findTopByPatientIdOrderByCreatedAtDesc(UUID patientId);
}

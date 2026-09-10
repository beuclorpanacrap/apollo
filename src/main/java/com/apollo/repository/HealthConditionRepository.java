package com.apollo.repository;

import com.apollo.domain.entity.HealthCondition;
import com.apollo.domain.entity.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HealthConditionRepository extends JpaRepository<HealthCondition, UUID> {
    List<HealthCondition> findByPatientOrderByDateRecordedDescCreatedAtDesc(PatientProfile patient);
    List<HealthCondition> findByPatientIdOrderByDateRecordedDescCreatedAtDesc(UUID patientId);
}

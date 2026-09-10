package com.apollo.repository;

import com.apollo.domain.entity.ClinicalEncounter;
import com.apollo.domain.entity.DoctorProfile;
import com.apollo.domain.entity.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClinicalEncounterRepository extends JpaRepository<ClinicalEncounter, UUID> {
    List<ClinicalEncounter> findByPatientOrderByEncounterDateDescCreatedAtDesc(PatientProfile patient);
    List<ClinicalEncounter> findByPatientIdOrderByEncounterDateDescCreatedAtDesc(UUID patientId);
    List<ClinicalEncounter> findByDoctorOrderByEncounterDateDescCreatedAtDesc(DoctorProfile doctor);
}

package com.apollo.repository;

import com.apollo.domain.entity.DoctorProfile;
import com.apollo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, UUID> {
    Optional<DoctorProfile> findByUser(User user);
    Optional<DoctorProfile> findByUserId(UUID userId);
    Optional<DoctorProfile> findByLicenseNumber(String licenseNumber);
    boolean existsByLicenseNumber(String licenseNumber);
}

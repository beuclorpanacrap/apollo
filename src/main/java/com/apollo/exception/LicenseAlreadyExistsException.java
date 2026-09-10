package com.apollo.exception;

public class LicenseAlreadyExistsException extends RuntimeException {
    public LicenseAlreadyExistsException(String licenseNumber) {
        super("Doctor license number is already registered: " + licenseNumber);
    }
}

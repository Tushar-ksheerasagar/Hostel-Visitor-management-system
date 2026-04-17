package com.ooad.hostelvisitor.service.verification;

public class AadhaarGateway {

    public boolean validateAadhaar(String value) {
        return value != null && value.matches("\\d{12}");
    }
}

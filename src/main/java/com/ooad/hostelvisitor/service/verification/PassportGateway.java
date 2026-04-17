package com.ooad.hostelvisitor.service.verification;

public class PassportGateway {

    public boolean validatePassport(String value) {
        return value != null && value.matches("[A-Z][0-9]{7}");
    }
}

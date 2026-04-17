package com.ooad.hostelvisitor.service.verification;

public class AadhaarVerifierAdapter implements IdentityVerifier {

    private final AadhaarGateway gateway;

    public AadhaarVerifierAdapter(AadhaarGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public boolean verify(String idNumber) {
        return gateway.validateAadhaar(idNumber);
    }
}

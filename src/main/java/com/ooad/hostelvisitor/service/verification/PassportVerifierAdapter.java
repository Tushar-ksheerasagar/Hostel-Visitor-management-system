package com.ooad.hostelvisitor.service.verification;

public class PassportVerifierAdapter implements IdentityVerifier {

    private final PassportGateway gateway;

    public PassportVerifierAdapter(PassportGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public boolean verify(String idNumber) {
        return gateway.validatePassport(idNumber);
    }
}

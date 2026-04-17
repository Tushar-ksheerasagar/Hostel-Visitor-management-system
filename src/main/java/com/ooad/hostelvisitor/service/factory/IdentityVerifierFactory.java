package com.ooad.hostelvisitor.service.factory;

import com.ooad.hostelvisitor.model.IdType;
import com.ooad.hostelvisitor.service.verification.AadhaarGateway;
import com.ooad.hostelvisitor.service.verification.AadhaarVerifierAdapter;
import com.ooad.hostelvisitor.service.verification.IdentityVerifier;
import com.ooad.hostelvisitor.service.verification.PassportGateway;
import com.ooad.hostelvisitor.service.verification.PassportVerifierAdapter;
import org.springframework.stereotype.Component;

@Component
public class IdentityVerifierFactory {

    public IdentityVerifier build(IdType idType) {
        return switch (idType) {
            case AADHAAR -> new AadhaarVerifierAdapter(new AadhaarGateway());
            case PASSPORT -> new PassportVerifierAdapter(new PassportGateway());
        };
    }
}

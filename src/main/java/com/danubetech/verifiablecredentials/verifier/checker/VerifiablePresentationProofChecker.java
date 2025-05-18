package com.danubetech.verifiablecredentials.verifier.checker;

import com.danubetech.verifiablecredentials.VerifiablePresentation;
import uniresolver.UniResolver;

public class VerifiablePresentationProofChecker extends ProofChecker<VerifiablePresentation> {

    public VerifiablePresentationProofChecker(UniResolver uniResolver) {
        super("presentation-proof", uniResolver);
    }
}

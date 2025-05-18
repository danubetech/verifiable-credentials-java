package com.danubetech.verifiablecredentials.verifier.checker;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import uniresolver.UniResolver;

public class VerifiableCredentialProofChecker extends ProofChecker<VerifiableCredential> {

    public VerifiableCredentialProofChecker(UniResolver uniResolver) {
        super("credential-proof", uniResolver);
    }
}

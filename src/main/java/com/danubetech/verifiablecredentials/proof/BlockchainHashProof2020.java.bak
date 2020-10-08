package com.danubetech.verifiablecredentials.proof;

import java.io.Reader;
import java.net.URI;

import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.credentialstatus.RevocationQuery2020Status;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;
import info.weboftrust.ldsignatures.LdProof;

import javax.json.JsonObject;

public class BlockchainHashProof2020 extends LdProof {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { VerifiableCredentialKeywords.JSONLD_TERM_BLOCKCHAIN_HASH_PROOF_2020 };
	public static final String DEFAULT_JSONLD_PREDICATE = VerifiableCredentialKeywords.JSONLD_TERM_CREDENTIALSUBJECT;

	private BlockchainHashProof2020() {
		super(VerifiableCredentialContexts.DOCUMENT_LOADER);
	}

	public BlockchainHashProof2020(JsonObject jsonObject) {
		super(VerifiableCredentialContexts.DOCUMENT_LOADER, jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder extends LdProof.Builder<Builder, BlockchainHashProof2020> {

		public Builder(BlockchainHashProof2020 jsonLDObject, boolean addContexts) {
			super(jsonLDObject, addContexts);
		}

		public BlockchainHashProof2020 build() {

			super.build();

			// add JSON-LD properties

			return this.jsonLDObject;
		}
	}

	public static Builder builder(boolean addContexts) {
		return new Builder(new BlockchainHashProof2020(), addContexts);
	}

	public static Builder builder() {
		return builder(false);
	}

	/*
	 * Reading the JSON-LD object
	 */

	public static BlockchainHashProof2020 fromJson(Reader reader) {
		return JsonLDObject.fromJson(BlockchainHashProof2020.class, reader);
	}

	public static BlockchainHashProof2020 fromJson(String json) {
		return JsonLDObject.fromJson(BlockchainHashProof2020.class, json);
	}

	/*
	 * Adding, getting, and removing the JSON-LD object
	 */

	public static BlockchainHashProof2020 getFromJsonLDObject(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObject(BlockchainHashProof2020.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(BlockchainHashProof2020.class, jsonLdObject);
	}
}

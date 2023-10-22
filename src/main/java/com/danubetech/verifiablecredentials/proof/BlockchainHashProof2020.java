package com.danubetech.verifiablecredentials.proof;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.jsonld.JsonLDObject;
import info.weboftrust.ldsignatures.LdProof;
import info.weboftrust.ldsignatures.jsonld.LDSecurityKeywords;

import java.io.Reader;
import java.net.URI;
import java.util.Map;

public class BlockchainHashProof2020 extends LdProof {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { VerifiableCredentialKeywords.JSONLD_TERM_BLOCKCHAIN_HASH_PROOF_2020 };
	public static final String DEFAULT_JSONLD_PREDICATE = LDSecurityKeywords.JSONLD_TERM_PROOF;
	public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = VerifiableCredentialContexts.DOCUMENT_LOADER;

	@JsonCreator
	public BlockchainHashProof2020() {
		super();
	}

	protected BlockchainHashProof2020(Map<String, Object> jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder<B extends Builder<B>> extends LdProof.Builder<B> {

		public Builder(BlockchainHashProof2020 jsonLdObject) {
			super(jsonLdObject);
		}

		public BlockchainHashProof2020 build() {

			super.build();

			// add JSON-LD properties

			return (BlockchainHashProof2020) this.jsonLdObject;
		}
	}

	public static Builder<? extends Builder<?>> builder() {
		return new Builder<? extends Builder<?>>(new BlockchainHashProof2020());
	}

	public static BlockchainHashProof2020 fromJsonObject(Map<String, Object> jsonObject) {
		return new BlockchainHashProof2020(jsonObject);
	}

	public static BlockchainHashProof2020 fromJsonLDObject(JsonLDObject jsonLDObject) { return fromJsonObject(jsonLDObject.getJsonObject()); }

	public static BlockchainHashProof2020 fromJson(Reader reader) {
		return new BlockchainHashProof2020(readJson(reader));
	}

	public static BlockchainHashProof2020 fromJson(String json) {
		return new BlockchainHashProof2020(readJson(json));
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

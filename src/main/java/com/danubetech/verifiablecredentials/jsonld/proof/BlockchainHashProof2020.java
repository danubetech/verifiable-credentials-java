package com.danubetech.verifiablecredentials.jsonld.proof;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.github.jsonldjava.core.JsonLdConsts;

import info.weboftrust.ldsignatures.LdSignature;

public class BlockchainHashProof2020 extends LdSignature {

	public static final LinkedHashMap<String, Object> JSONLD_CONTEXT;

	public static final String JSONLD_TERM_BLOCKCHAINHASHPROOF2020 = "BlockchainHashProof2020";

	static {

		JSONLD_CONTEXT = new LinkedHashMap<String, Object> ();
		JSONLD_CONTEXT.put(JSONLD_TERM_BLOCKCHAINHASHPROOF2020, "https://danubetech.com/schema/2020/proofs/v1#BlockchainHashProof2020");
	}

	protected BlockchainHashProof2020(LinkedHashMap<String, Object> jsonLdProofObject) { 

		super(jsonLdProofObject);
	}

	public BlockchainHashProof2020() {

		super();
	}

	public static BlockchainHashProof2020 fromJsonLdProofObject(LinkedHashMap<String, Object> jsonLdProofObject) {

		return new BlockchainHashProof2020(jsonLdProofObject);
	}

	@SuppressWarnings("unchecked")
	public static void addContextToJsonLdObject(LinkedHashMap<String, Object> jsonLdObject) {

		LdSignature.addContextToJsonLdObject(jsonLdObject);

		Object context = jsonLdObject.get(JsonLdConsts.CONTEXT);
		ArrayList<Object> contexts;

		// add as single value

		if (context == null) {

			jsonLdObject.put(JsonLdConsts.CONTEXT, JSONLD_CONTEXT);
			return;
		}

		// add as array member

		if (context instanceof ArrayList<?>) {

			contexts = (ArrayList<Object>) context;
		} else {

			contexts = new ArrayList<Object> ();
			contexts.add(context);
			jsonLdObject.put(JsonLdConsts.CONTEXT, contexts);
		}

		if (! contexts.contains(JSONLD_CONTEXT)) {

			contexts.add(JSONLD_CONTEXT);
		}
	}

	public void addToJsonLdObject(LinkedHashMap<String, Object> jsonLdObject, boolean addContext) {

		if (addContext) addContextToJsonLdObject(jsonLdObject);

		addToJsonLdObject(jsonLdObject, this.getJsonLdProofObject());
	}

	public void addToJsonLdObject(LinkedHashMap<String, Object> jsonLdObject) {

		this.addToJsonLdObject(jsonLdObject, false);
	}

	public static void removeFromJsonLdObject(LinkedHashMap<String, Object> jsonLdObject) {

		jsonLdObject.remove(JSONLD_TERM_PROOF);
	}

	@SuppressWarnings("unchecked")
	public static BlockchainHashProof2020 getFromJsonLdObject(LinkedHashMap<String, Object> jsonLdObject) {

		Object jsonLdProofObjectEntry = jsonLdObject.get(JSONLD_TERM_PROOF);

		if (jsonLdProofObjectEntry instanceof List) {

			List<LinkedHashMap<String, Object>> jsonLdProofObjectList = (List<LinkedHashMap<String, Object>>) jsonLdProofObjectEntry;

			for (LinkedHashMap<String, Object> jsonLdProofObject : jsonLdProofObjectList) {

				if (JSONLD_TERM_BLOCKCHAINHASHPROOF2020.equals(jsonLdProofObject.get("type"))) return BlockchainHashProof2020.fromJsonLdProofObject(jsonLdProofObject);
			}
		} else if (jsonLdProofObjectEntry instanceof LinkedHashMap) {

			LinkedHashMap<String, Object> jsonLdProofObject = (LinkedHashMap<String, Object>) jsonLdProofObjectEntry;

			if (JSONLD_TERM_BLOCKCHAINHASHPROOF2020.equals(jsonLdProofObject.get("type"))) return BlockchainHashProof2020.fromJsonLdProofObject(jsonLdProofObject);
		}

		return null;
	}
}

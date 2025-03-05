package com.danubetech.verifiablecredentials.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestUtil {

	public static String read(InputStream inputStream) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder buffer = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) buffer.append(line).append("\n");
		return buffer.toString();
	}

	public static byte[] removeMulticodec(byte[] bytes) {
		byte[] ret = new byte[bytes.length-2];
		System.arraycopy(bytes, 2, ret, 0, bytes.length-2);
		return ret;
	}
}

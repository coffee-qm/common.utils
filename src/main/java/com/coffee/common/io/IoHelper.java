package com.coffee.common.io;

import java.io.InputStream;
import java.io.Reader;

import org.apache.commons.io.IOUtils;

/**
 * @author QM
 */
public final class IoHelper {

	public static void close(final InputStream in) {
		IOUtils.closeQuietly(in);
	}

	public static void close(final Reader reader) {
		IOUtils.closeQuietly(reader);
	}
}

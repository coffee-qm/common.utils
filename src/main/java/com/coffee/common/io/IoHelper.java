package com.coffee.common.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author QM
 */
public final class IoHelper {
	
	private static Logger logger = LoggerFactory.getLogger(IoHelper.class);

	public static void close(final InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
	}

	public static void close(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
	}
}

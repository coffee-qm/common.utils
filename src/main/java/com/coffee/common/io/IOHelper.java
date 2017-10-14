package com.coffee.common.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public final class IOHelper {

	public static void close(final InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
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

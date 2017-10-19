package com.coffee.common.file.utils;

import java.io.File;

import com.coffee.common.common.exp.SvcException;

/**
 * @author QM
 */
public final class FileUtils {
	public static void exists(final String path) throws SvcException {
		if (!new File(path).exists()) {
			throw new SvcException("File not exists.");
		}
	}

	public static void delete(final String path) throws SvcException {
		final File f = new File(path);
		if (!f.delete()) {
			throw new SvcException("Failed to delete file.");
		}
	}
}

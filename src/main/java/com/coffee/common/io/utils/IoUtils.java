package com.coffee.common.io.utils;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author QM
 */
public class IoUtils extends IOUtils {

	private static Logger logger = LoggerFactory.getLogger(IoUtils.class);

	public static void closeQuietly(ZipFile zipFile) {
		if (zipFile != null) {
			try {
				zipFile.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
	}
}

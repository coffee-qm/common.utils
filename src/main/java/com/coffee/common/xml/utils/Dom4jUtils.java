package com.coffee.common.xml.utils;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffee.common.common.exp.SvcException;

/**
 * @author QM
 */
public final class Dom4jUtils {

	private static Logger logger = LoggerFactory.getLogger(Dom4jUtils.class);

	public Document getDocument(InputStream in) throws SvcException {
		try {
			SAXReader reader = new SAXReader();
			return reader.read(in);
		} catch (DocumentException e) {
			logger.error("", e);
			throw new SvcException("Failed to read xml.");
		}
	}
}

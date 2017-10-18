package com.coffee.common.xml.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.coffee.common.common.exp.SvcException;

/**
 * @author QM
 */
public final class Dom4jUtils {

	private static Logger logger = LoggerFactory.getLogger(Dom4jUtils.class);

	public Document getDocument(InputStream in) throws SvcException {
		try {
			SAXReader reader = new SAXReader();
			//
			reader.setEntityResolver(new EntityResolver() {
				String emptyDtd = "";
				ByteArrayInputStream byteArrIs = new ByteArrayInputStream(
						emptyDtd.getBytes());

				@Override
				public InputSource resolveEntity(String publicId,
						String systemId) throws SAXException, IOException {
					return new InputSource(byteArrIs);
				}
			});
			// 
			reader.setFeature(
					"http://apache.org/xml/features/disallow-doctype-decl",
					Boolean.TRUE);
			return reader.read(in);
		} catch (DocumentException e) {
			logger.error("", e);
			throw new SvcException("Failed to read xml.");
		} catch (SAXException e) {
			logger.error("", e);
			throw new SvcException("Failed to read xml.");
		}
	}
}

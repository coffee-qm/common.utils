package com.coffee.common.xml.utils;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffee.common.common.exp.SvcException;

/**
 * @author QM
 */
public final class JaxUtils {
	
	private static Logger logger = LoggerFactory.getLogger(JaxUtils.class);

	@SuppressWarnings("unchecked")
	public <T> T read(InputStream in, Class<T> clazz) throws SvcException {
		try {
			final JAXBContext context = JAXBContext.newInstance(clazz);
			final XMLInputFactory factory = XMLInputFactory.newFactory();
			factory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
			final XMLStreamReader reader = factory.createXMLStreamReader(in);
			final Unmarshaller unmarshaller = context.createUnmarshaller();
			return (T) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			logger.error("", e);
			throw new SvcException("Failed to read xml.");
		} catch (XMLStreamException e) {
			logger.error("", e);
			throw new SvcException("Failed to read xml.");
		}
	}
}

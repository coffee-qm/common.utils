package com.coffee.common.xml.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffee.common.common.exp.SvcException;

/**
 * @author QM
 */
public final class JaxUtils {

	private static Logger logger = LoggerFactory.getLogger(JaxUtils.class);

	@SuppressWarnings("unchecked")
	public static <T> T read(final InputStream in, final Class<T> clazz)
			throws SvcException {
		try {
			//
			final JAXBContext context = JAXBContext.newInstance(clazz);
			//
			final XMLInputFactory factory = XMLInputFactory.newFactory();
			factory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
			//
			final XMLStreamReader reader = factory.createXMLStreamReader(in);
			//
			final Unmarshaller unmarshaller = context.createUnmarshaller();
			return (T) unmarshaller.unmarshal(reader);
		} catch (final JAXBException e) {
			logger.error("", e);
			e.printStackTrace();
			throw new SvcException("Failed to read xml.");
		} catch (final XMLStreamException e) {
			logger.error("", e);
			throw new SvcException("Failed to read xml.");
		}
	}

	public static <T> void write(final OutputStream out, final Class<T> clazz,
			final T t) throws SvcException {
		try {
			//
			final JAXBContext context = JAXBContext.newInstance(clazz);
			//
			final XMLOutputFactory factory = XMLOutputFactory.newFactory();
			//
			final XMLStreamWriter writer = factory.createXMLStreamWriter(out);
			//
			final Marshaller marshaller = context.createMarshaller();
			// marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// marshaller
			// .setProperty(
			// "com.sun.xml.internal.bind.characterEscapeHandler",
			// new com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler()
			// {
			// @Override
			// public void escape(final char[] chars,
			// final int i, final int i1,
			// final boolean bln, final Writer writer)
			// throws IOException {
			// writer.write(chars, i, i1);
			// }
			// });
			marshaller.marshal(t, writer);
		} catch (final JAXBException e) {
			logger.error("", e);
			throw new SvcException("Failed to write xml.");
		} catch (final XMLStreamException e) {
			logger.error("", e);
			throw new SvcException("Failed to write xml.");
		}
	}

	public static <T> void write(final String path, final Class<T> clazz,
			final T t) throws SvcException {
		try {
			//
			final JAXBContext context = JAXBContext.newInstance(clazz);
			//
			final StringWriter writer = new StringWriter();
			//
			final Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(t, writer);
			//
			FileUtils.writeStringToFile(new File(path), writer.toString(),
					Charset.forName("UTF-8"));
		} catch (final JAXBException e) {
			logger.error("", e);
			throw new SvcException("Failed to write xml.");
		} catch (final IOException e) {
			logger.error("", e);
			throw new SvcException("Failed to write xml.");
		}
	}
}

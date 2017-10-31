package com.coffee.common.xml.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
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
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

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
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			// format: not success
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// escape: not success
			marshaller.setProperty(
					"com.sun.xml.bind.marshaller.CharacterEscapeHandler",
					new CharacterEscapeHandler() {
						@Override
						public void escape(final char[] ch, final int start,
								final int length, final boolean isAttVal,
								final Writer writer) throws IOException {
							writer.write(ch, start, length);
						}
					});
			System.out.println(out.toString());
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
			// format
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// escape
			marshaller.setProperty(
					"com.sun.xml.bind.marshaller.CharacterEscapeHandler",
					new CharacterEscapeHandler() {
						@Override
						public void escape(final char[] ch, final int start,
								final int length, final boolean isAttVal,
								final Writer writer) throws IOException {
							writer.write(ch, start, length);
						}
					});
			marshaller.marshal(t, writer);
			//
			FileUtils.writeStringToFile(new File(path), writer.toString(),
					Charset.forName("UTF-8"));
		} catch (final JAXBException e) {
			logger.error("", e);
			e.printStackTrace();
			throw new SvcException("Failed to write xml.");
		} catch (final IOException e) {
			logger.error("", e);
			throw new SvcException("Failed to write xml.");
		}
	}
}

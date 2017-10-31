package com.coffee.common.xml.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author QM
 */
public class CDataAdapter extends XmlAdapter<String, String> {

	private static String CDATA_BEGIN = "<![CDATA[";

	private static String CDATA_END = "]]>";

	@Override
	public String unmarshal(final String v) throws Exception {
		return v;
	}

	@Override
	public String marshal(final String v) throws Exception {
		return CDATA_BEGIN + v + CDATA_END;
	}
}

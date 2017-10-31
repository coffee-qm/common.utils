package com.coffee.common.xml.utils.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author QM
 */
@XmlRootElement(name = "sqls")
public class RootNode {

	private String id;

	@XmlElement(name = "sub")
	private List<SubNode> subs;

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"id\":\"").append(id).append("\"");
		sb.append("\"subs\":").append(subs);
		sb.append("}");
		return sb.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	@XmlTransient
	public List<SubNode> getSubs() {
		return subs;
	}

	public void setSubs(final List<SubNode> subs) {
		this.subs = subs;
	}
}

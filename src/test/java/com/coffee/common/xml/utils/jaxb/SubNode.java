package com.coffee.common.xml.utils.jaxb;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.coffee.common.xml.jaxb.CDataAdapter;

public class SubNode {
	private int id;

	@XmlJavaTypeAdapter(value = CDataAdapter.class)
	private String name;

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"id\":\"").append(id).append("\"");
		sb.append(",\"name\":\"").append(name).append("\"");
		sb.append("}");
		return sb.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@XmlTransient
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}

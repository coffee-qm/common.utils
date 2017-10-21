package com.coffee.common.common.mo;

import java.io.Serializable;

/**
 * @author QM
 */
public class BaseMo implements Serializable {

	private static final long serialVersionUID = 2273570974858354649L;

	private long id;

	private String name;

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("\"id\":\"").append(id).append("\"");
		sb.append(",\"name\":\"").append(name).append("\"");
		return sb.toString();
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}

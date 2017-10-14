package com.coffee.common.common.mo;

import java.io.Serializable;

public class BaseMo implements Serializable {

	private static final long serialVersionUID = 2273570974858354649L;

	private long id;

	private String name;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"id\":\"").append(id).append("\"");
		sb.append(",\"name\":\"").append(name).append("\"");
		return sb.toString();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

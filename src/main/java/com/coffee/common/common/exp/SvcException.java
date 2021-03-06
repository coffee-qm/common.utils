package com.coffee.common.common.exp;

/**
 * @author QM
 */
public class SvcException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7872630482307713528L;

	private long id;

	private final String msg;

	public SvcException(final String msg) {
		this.msg = msg;
	}

	public long getId() {
		return id;
	}

	public String getMsg() {
		return msg;
	}
}

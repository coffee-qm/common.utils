package com.coffee.common.sql.format;

/**
 * @author QM
 */
public abstract interface Formatter {
	/**
	 * format
	 * 
	 * @param sql
	 *            sql
	 * @return formatSql
	 */
	public abstract String format(String sql);
}

package com.coffee.common.validate.utils;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.coffee.common.common.exp.ExpMsg;
import com.coffee.common.common.exp.SvcException;

/**
 * @author QM
 */
public final class ValidateUtils {

	/**
	 * required
	 * 
	 * @param o
	 *            object
	 * @throws SvcException
	 *             SvcException
	 */
	public static void required(final Object o) throws SvcException {
		if (o == null) {
			throw new SvcException(ExpMsg.INVALID_DATA);
		}
		if (o instanceof String) {
			if (StringUtils.isEmpty(o.toString())) {
				throw new SvcException(ExpMsg.INVALID_DATA);
			}
		} else if (o instanceof Collection) {
			if (CollectionUtils.sizeIsEmpty(o)) {
				throw new SvcException(ExpMsg.INVALID_DATA);
			}
		} else if (o instanceof Map) {
			if (MapUtils.isEmpty((Map<?, ?>) o)) {
				throw new SvcException(ExpMsg.INVALID_DATA);
			}
		} else {
			throw new SvcException(ExpMsg.NOT_SUPPORT);
		}
	}

	public static void min(final int val, final int minVal) throws SvcException {
		if (val < minVal) {
			throw new SvcException(ExpMsg.INVALID_DATA);
		}
	}

	public static void max(final int val, final int maxVal) throws SvcException {
		if (val > maxVal) {
			throw new SvcException(ExpMsg.INVALID_DATA);
		}
	}

	public static void range(final int val, final int minVal, final int maxVal)
			throws SvcException {
		min(val, minVal);
		max(val, maxVal);
	}

	public static void min(final long val, final long minVal)
			throws SvcException {
		if (val < minVal) {
			throw new SvcException(ExpMsg.INVALID_DATA);
		}
	}

	public static void max(final long val, final long maxVal)
			throws SvcException {
		if (val > maxVal) {
			throw new SvcException(ExpMsg.INVALID_DATA);
		}
	}

	public static void range(final long val, final long minVal,
			final long maxVal) throws SvcException {
		min(val, minVal);
		max(val, maxVal);
	}
}

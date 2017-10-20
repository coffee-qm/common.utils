package com.coffee.common.regex.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author QM
 */
public final class RegexUtils {

	/**
	 * matcher
	 * 
	 * @param str
	 *            string
	 * @param regex
	 *            regexStr
	 * @return List
	 */
	public static List<String> matcher(final String str, final String regex) {
		final List<String> lst = new ArrayList<String>();
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			lst.add(matcher.group());
		}
		return lst;
	}
}

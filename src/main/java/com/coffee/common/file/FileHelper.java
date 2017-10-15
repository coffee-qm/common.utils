package com.coffee.common.file;

import com.coffee.common.common.exp.SvcException;
import com.coffee.common.file.utils.ReaderUtils;

/**
 * @author QM
 */
public final class FileHelper {

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 * 
	 * @param filePath
	 *            filePath
	 * @return content
	 * @throws SvcException
	 */
	public static String readByLines(String filePath) throws SvcException {
		return ReaderUtils.readByLines(filePath);
	}

	/**
	 * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	 * 
	 * @param filePath
	 *            filePath
	 * @return content
	 * @throws SvcException
	 */
	public static String readByChars(String filePath) throws SvcException {
		return ReaderUtils.readByChars(filePath);
	}

	/**
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
	 * 
	 * @param filePath
	 *            filePath
	 * @return content
	 * @throws SvcException
	 */
	public static String readByBytes(String filePath) throws SvcException {
		return ReaderUtils.readByBytes(filePath);
	}

}

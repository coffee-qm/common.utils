package com.coffee.common.file.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffee.common.common.consts.SymbolsChar;
import com.coffee.common.common.exp.SvcException;
import com.coffee.common.io.IoHelper;

/**
 * @author QM
 */
public final class ReaderUtils {

	private static Logger logger = LoggerFactory.getLogger(ReaderUtils.class);

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 * 
	 * @param filePath
	 *            filePath
	 * @return content
	 * @throws SvcException
	 */
	public static String readByLines(final String filePath) throws SvcException {
		final StringBuilder sb = new StringBuilder();
		final File file = new File(filePath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString);
			}
			reader.close();
		} catch (final FileNotFoundException e) {
			logger.error("", e);
			throw new SvcException("Failed to find file.");
		} catch (final IOException e) {
			logger.error("", e);
			throw new SvcException("Failed to read file.");
		} finally {
			IoHelper.close(reader);
		}
		return sb.toString();
	}

	/**
	 * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	 * 
	 * @param filePath
	 *            filePath
	 * @return content
	 * @throws SvcException
	 */
	public static String readByChars(final String filePath) throws SvcException {
		final StringBuilder sb = new StringBuilder();
		Reader reader = null;
		try {
			final char[] tempchars = new char[30];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(filePath));
			while ((charread = reader.read(tempchars)) != -1) {
				// 屏蔽掉\r不显示
				if ((charread == tempchars.length)
						&& (tempchars[tempchars.length - 1] != SymbolsChar.RETURN)) {
					sb.append(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == SymbolsChar.RETURN) {
							continue;
						} else {
							sb.append(tempchars[i]);
						}
					}
				}
			}

		} catch (final FileNotFoundException e) {
			logger.error("", e);
			throw new SvcException("Failed to find file.");
		} catch (final IOException e) {
			logger.error("", e);
			throw new SvcException("Failed to read file.");
		} finally {
			IoHelper.close(reader);
		}
		return sb.toString();
	}

	/**
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
	 * 
	 * @param filePath
	 *            filePath
	 * @return content
	 * @throws SvcException
	 */
	public static String readByBytes(final String filePath) throws SvcException {
		final StringBuilder sb = new StringBuilder();
		InputStream in = null;
		try {
			final byte[] tempBytes = new byte[100];
			in = new FileInputStream(filePath);
			while (in.read(tempBytes) != -1) {
				sb.append(tempBytes);
			}
		} catch (final FileNotFoundException e) {
			logger.error("", e);
			throw new SvcException("Failed to find file.");
		} catch (final IOException e) {
			logger.error("", e);
			throw new SvcException("Failed to read file.");
		} finally {
			IoHelper.close(in);
		}
		return sb.toString();
	}
}

package com.coffee.common.zip.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffee.common.common.consts.CharacterSet;
import com.coffee.common.common.consts.Symbols;
import com.coffee.common.io.utils.IoUtils;

/**
 * @author QM
 */
public class ZipUtils {

	private static Logger logger = LoggerFactory.getLogger(ZipUtils.class);

	private static final int BUFFER_SIZE = 2048;

	/**
	 * 解压ZIP文件
	 * 
	 * @param path
	 *            path
	 */
	public static void unZip(final String path) {
		String savepath = "";

		ZipFile zipFile = null;
		InputStream is = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		// 保存解压文件目录
		savepath = path.substring(0, path.lastIndexOf(Symbols.POINT)) + File.separator;
		// 创建保存目录
		new File(savepath).mkdir();
		try {
			File file = null;
			// 解决中文乱码问题
			zipFile = new ZipFile(path, CharacterSet.GBK);
			final Enumeration<?> entries = zipFile.getEntries();

			while (entries.hasMoreElements()) {
				final byte buf[] = new byte[BUFFER_SIZE];

				final ZipEntry entry = (ZipEntry) entries.nextElement();

				String filename = entry.getName();
				boolean ismkdir = false;
				// 检查此文件是否带有文件夹
				if (filename.lastIndexOf(Symbols.SLASH_L) != -1) {
					ismkdir = true;
				}
				filename = savepath + filename;
				// 如果是文件夹先创建
				if (entry.isDirectory()) {
					file = new File(filename);
					file.mkdirs();
					continue;
				}
				file = new File(filename);
				// 如果是目录先创建
				if (!file.exists()) {
					if (ismkdir) {
						new File(filename.substring(0,
								filename.lastIndexOf(Symbols.SLASH_L))).mkdirs();
					}
				}
				// 创建文件
				file.createNewFile();

				is = zipFile.getInputStream(entry);
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos, BUFFER_SIZE);
				int count = -1;
				while ((count = is.read(buf)) > -1) {
					bos.write(buf, 0, count);
				}
				bos.flush();
			}
		} catch (final IOException e) {
			logger.error("", e);
		} finally {
			IoUtils.closeQuietly(bos);
			IoUtils.closeQuietly(fos);
			IoUtils.closeQuietly(is);
			IoUtils.closeQuietly(zipFile);
		}
	}
}

package com.coffee.common.zip.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipOutputStream;

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
	private static final String SUFFIX = ".zip";
	private static final int BUFFER_SIZE = 2048;

	/**
	 * 压缩文件
	 * 
	 * @param path
	 *            path
	 */
	public static void zip(final String path) {
		final String zipName = path.substring(0, path.indexOf(File.separator))
				+ SUFFIX;
		zip(path, zipName);
	}

	private static void zip(final String path, final String zipName) {
		// 创建zip输出流
		ZipOutputStream out = null;
		BufferedOutputStream bos = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipName));
			// 创建缓冲输出流
			bos = new BufferedOutputStream(out);
			final File source = new File(path);
			// 调用函数
			compress(out, bos, source, source.getName());
		} catch (final FileNotFoundException e) {
			logger.error("", e);
		} catch (final IOException e) {
			logger.error("", e);
		} finally {
			IoUtils.closeQuietly(bos);
			IoUtils.closeQuietly(out);
		}
	}

	private static void compress(final ZipOutputStream out,
			final BufferedOutputStream bos, final File sourceFile,
			final String base) throws IOException {
		// 如果路径为目录（文件夹）
		if (sourceFile.isDirectory()) {
			// 取出文件夹中的文件（或子文件夹）
			final File[] flist = sourceFile.listFiles();
			if (flist.length == 0) {
				// 如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
				out.putNextEntry(new ZipEntry(base + "/"));
			} else {
				// 如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
				for (int i = 0; i < flist.length; i++) {
					compress(out, bos, flist[i],
							base + "/" + flist[i].getName());
				}
			}
		} else {
			// 如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
			out.putNextEntry(new ZipEntry(base));
			FileInputStream fos = null;
			BufferedInputStream bis = null;
			try {
				fos = new FileInputStream(sourceFile);
				bis = new BufferedInputStream(fos);
				int tag;
				// 将源文件写入到zip文件中
				while ((tag = bis.read()) != -1) {
					bos.write(tag);
				}
			} finally {
				IoUtils.closeQuietly(bis);
				IoUtils.closeQuietly(fos);
			}

		}
	}

	/**
	 * 解压文件
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
		savepath = path.substring(0, path.lastIndexOf(Symbols.POINT))
				+ File.separator;
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
								filename.lastIndexOf(Symbols.SLASH_L)))
								.mkdirs();
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

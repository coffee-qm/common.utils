package com.coffee.common.xml.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.coffee.common.common.exp.SvcException;
import com.coffee.common.xml.utils.jaxb.RootNode;
import com.coffee.common.xml.utils.jaxb.SubNode;

public class JaxUtilsTest {
	private static void read() {
		try {
			final String xmlPath = "E:\\Test\\xml\\demo.xml";
			final FileInputStream in = new FileInputStream(new File(xmlPath));
			final RootNode root = JaxUtils.read(in, RootNode.class);
			System.out.println(root);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final SvcException e) {
			e.printStackTrace();
		}
	}

	private static RootNode getWriteData() {
		final RootNode root = new RootNode();
		final SubNode sub1 = new SubNode();
		sub1.setId(1);
		sub1.setName("name1");
		final SubNode sub2 = new SubNode();
		sub2.setId(2);
		sub2.setName("name2");
		final List<SubNode> subs = new ArrayList<SubNode>();
		subs.add(sub1);
		subs.add(sub2);
		root.setSubs(subs);
		return root;
	}

	private static void write() {
		FileOutputStream out = null;
		try {
			final String xmlPath = "E:\\Test\\xml\\demo1.xml";
			out = new FileOutputStream(new File(xmlPath));
			JaxUtils.write(out, RootNode.class, getWriteData());
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final SvcException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	private static void writeFormat() {
		try {
			final String xmlPath = "E:\\Test\\xml\\demo2.xml";
			JaxUtils.write(xmlPath, RootNode.class, getWriteData());
		} catch (final SvcException e) {
			e.printStackTrace();
		} finally {
		}
	}

	public static void main(final String[] args) {
		read();
		write();
		writeFormat();
	}
}

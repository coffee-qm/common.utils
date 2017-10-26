package com.coffee.common.xml.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

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

	private static void write() {
		try {
			final String xmlPath = "E:\\Test\\xml\\demo1.xml";
			final FileOutputStream out = new FileOutputStream(new File(xmlPath));
			final RootNode root = new RootNode();
			final SubNode sub1 = new SubNode();
			sub1.setId(1);
			sub1.setName("name1");
			final List<SubNode> subs = new ArrayList<SubNode>();
			subs.add(sub1);
			root.setSubs(subs);
			JaxUtils.write(out, RootNode.class, root);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final SvcException e) {
			e.printStackTrace();
		}
	}

	public static void main(final String[] args) {
		read();
		write();
	}
}

package com.coffee.common.sql.format;

import org.junit.Test;

import com.coffee.common.sql.format.impl.BasicFormatterImpl;

/**
 * @author QM
 */
public class FormatterTest {
	Formatter f = new BasicFormatterImpl();

	@Test
	public void format01() {
		final String sql = "select id, name from demo.demo where 1=1 and id=1";
		final String fo = f.format(sql);
		System.out.println(fo);
	}

	@Test
	public void format02() {
		final String sql = "select t.id, t.name from (select * from demo.demo where 1=1 and id=1) t where 1=1 and id=1";
		final String fo = f.format(sql);
		System.out.println(fo);
	}

	@Test
	public void format03() {
		final String sql = "select t.id, t.name from (select * from demo.demo where 1=1 and id=1) t left join demo.t_a a on t.id=a.id where 1=1 and id=1";
		final String fo = f.format(sql);
		System.out.println(fo);
	}
}

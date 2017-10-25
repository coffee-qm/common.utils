/*
 * Base on org.hibernate.engine.jdbc.internal.BasicFormatterImpl
 */
package com.coffee.common.sql.format.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

import com.coffee.common.sql.format.Formatter;

/**
 * Performs formatting of basic SQL statements (DML + query).
 * 
 * @author QM
 */
public class BasicFormatterImpl implements Formatter {
	private static final String BETWEEN = "between";
	private static final String END = "end";
	private static final String CASE = "case";
	private static final String INSERT = "insert";
	private static final String UPDATE = "update";
	private static final String UNION = "union";
	private static final Set<String> BEGIN_CLAUSES = new HashSet<String>();
	private static final Set<String> END_CLAUSES = new HashSet<String>();
	private static final Set<String> LOGICAL = new HashSet<String>();
	private static final Set<String> QUANTIFIERS = new HashSet<String>();
	private static final Set<String> DML = new HashSet<String>();
	private static final Set<String> MISC = new HashSet<String>();
	private static final String INITIAL = System.lineSeparator() + "    ";

	@Override
	public String format(final String source) {
		return new FormatProcess(source).perform();
	}

	static {
		BEGIN_CLAUSES.add("left");
		BEGIN_CLAUSES.add("right");
		BEGIN_CLAUSES.add("inner");
		BEGIN_CLAUSES.add("outer");
		BEGIN_CLAUSES.add("group");
		BEGIN_CLAUSES.add("order");

		END_CLAUSES.add("where");
		END_CLAUSES.add("set");
		END_CLAUSES.add("having");
		END_CLAUSES.add("from");
		END_CLAUSES.add("by");
		END_CLAUSES.add("join");
		END_CLAUSES.add("into");
		END_CLAUSES.add("union");

		LOGICAL.add("and");
		LOGICAL.add("or");
		LOGICAL.add("when");
		LOGICAL.add("else");
		LOGICAL.add("end");

		QUANTIFIERS.add("in");
		QUANTIFIERS.add("all");
		QUANTIFIERS.add("exists");
		QUANTIFIERS.add("some");
		QUANTIFIERS.add("any");

		DML.add("insert");
		DML.add("update");
		DML.add("delete");

		MISC.add("select");
		MISC.add("on");
	}

	private static class FormatProcess {
		boolean beginLine = Boolean.TRUE;
		boolean afterBeginBeforeEnd;
		boolean afterByOrSetOrFromOrSelect;
		boolean afterOn;
		boolean afterBetween;
		boolean afterInsert;
		int inFunction;
		int parensSinceSelect;
		private final LinkedList<Integer> parenCounts = new LinkedList<Integer>();
		private final LinkedList<Boolean> afterByOrFromOrSelects = new LinkedList<Boolean>();

		int indent = 1;

		StringBuilder result = new StringBuilder();
		StringTokenizer tokens;
		String lastToken;
		String token;
		String lcToken;

		public FormatProcess(final String sql) {
			this.tokens = new StringTokenizer(sql, "()+*/-=<>'`\"[] \n\r\f\t",
					Boolean.TRUE);
		}

		public String perform() {
			this.result.append(BasicFormatterImpl.INITIAL);

			while (this.tokens.hasMoreTokens()) {
				this.token = this.tokens.nextToken();
				this.lcToken = this.token.toLowerCase(Locale.ROOT);

				if ("'".equals(this.token)) {
					String t;
					do {
						t = this.tokens.nextToken();
						this.token += t;
					} while ((!"'".equals(t)) && (this.tokens.hasMoreTokens()));
				} else if ("\"".equals(this.token)) {
					String t;
					do {
						t = this.tokens.nextToken();
						this.token += t;
					} while (!"\"".equals(t));
				}

				if ((this.afterByOrSetOrFromOrSelect)
						&& (",".equals(this.token))) {
					commaAfterByOrFromOrSelect();
				} else if ((this.afterOn) && (",".equals(this.token))) {
					commaAfterOn();
				} else if ("(".equals(this.token)) {
					openParen();
				} else if (")".equals(this.token)) {
					closeParen();
				} else if (BasicFormatterImpl.BEGIN_CLAUSES
						.contains(this.lcToken)) {
					beginNewClause();
				} else if (BasicFormatterImpl.END_CLAUSES
						.contains(this.lcToken)) {
					endNewClause();
				} else if ("select".equals(this.lcToken)) {
					select();
				} else if (BasicFormatterImpl.DML.contains(this.lcToken)) {
					updateOrInsertOrDelete();
				} else if ("values".equals(this.lcToken)) {
					values();
				} else if ("on".equals(this.lcToken)) {
					on();
				} else if ((this.afterBetween) && (this.lcToken.equals("and"))) {
					misc();
					this.afterBetween = false;
				} else if (BasicFormatterImpl.LOGICAL.contains(this.lcToken)) {
					logical();
				} else if (isWhitespace(this.token)) {
					white();
				} else {
					misc();
				}
				if (!isWhitespace(this.token)) {
					this.lastToken = this.lcToken;
				}
			}
			return this.result.toString();
		}

		private void commaAfterOn() {
			out();
			this.indent -= 1;
			newline();
			this.afterOn = false;
			this.afterByOrSetOrFromOrSelect = Boolean.TRUE;
		}

		private void commaAfterByOrFromOrSelect() {
			out();
			newline();
		}

		private void logical() {
			if (END.equals(this.lcToken)) {
				this.indent -= 1;
			}
			newline();
			out();
			this.beginLine = false;
		}

		private void on() {
			this.indent += 1;
			this.afterOn = Boolean.TRUE;
			newline();
			out();
			this.beginLine = false;
		}

		private void misc() {
			out();
			if (BETWEEN.equals(this.lcToken)) {
				this.afterBetween = Boolean.TRUE;
			}
			if (this.afterInsert) {
				newline();
				this.afterInsert = false;
			} else {
				this.beginLine = false;
				if (CASE.equals(this.lcToken)) {
					this.indent += 1;
				}
			}
		}

		private void white() {
			if (!this.beginLine) {
				this.result.append(" ");
			}
		}

		private void updateOrInsertOrDelete() {
			out();
			this.indent += 1;
			this.beginLine = false;
			if (UPDATE.equals(this.lcToken)) {
				newline();
			}
			if (INSERT.equals(this.lcToken)) {
				this.afterInsert = Boolean.TRUE;
			}
		}

		private void select() {
			out();
			this.indent += 1;
			newline();
			this.parenCounts.addLast(Integer.valueOf(this.parensSinceSelect));
			this.afterByOrFromOrSelects.addLast(Boolean
					.valueOf(this.afterByOrSetOrFromOrSelect));
			this.parensSinceSelect = 0;
			this.afterByOrSetOrFromOrSelect = Boolean.TRUE;
		}

		private void out() {
			this.result.append(this.token);
		}

		private void endNewClause() {
			if (!this.afterBeginBeforeEnd) {
				this.indent -= 1;
				if (this.afterOn) {
					this.indent -= 1;
					this.afterOn = false;
				}
				newline();
			}
			out();
			if (!UNION.equals(this.lcToken)) {
				this.indent += 1;
			}
			newline();
			this.afterBeginBeforeEnd = false;
			this.afterByOrSetOrFromOrSelect = (("by".equals(this.lcToken))
					|| ("set".equals(this.lcToken)) || ("from"
					.equals(this.lcToken)));
		}

		private void beginNewClause() {
			if (!this.afterBeginBeforeEnd) {
				if (this.afterOn) {
					this.indent -= 1;
					this.afterOn = false;
				}
				this.indent -= 1;
				newline();
			}
			out();
			this.beginLine = false;
			this.afterBeginBeforeEnd = Boolean.TRUE;
		}

		private void values() {
			this.indent -= 1;
			newline();
			out();
			this.indent += 1;
			newline();
			// this.afterValues = Boolean.TRUE;
		}

		private void closeParen() {
			this.parensSinceSelect -= 1;
			if (this.parensSinceSelect < 0) {
				this.indent -= 1;
				this.parensSinceSelect = this.parenCounts.removeLast()
						.intValue();
				this.afterByOrSetOrFromOrSelect = this.afterByOrFromOrSelects
						.removeLast().booleanValue();
			}
			if (this.inFunction > 0) {
				this.inFunction -= 1;
				out();
			} else {
				if (!this.afterByOrSetOrFromOrSelect) {
					this.indent -= 1;
					newline();
				}
				out();
			}
			this.beginLine = false;
		}

		private void openParen() {
			if ((isFunctionName(this.lastToken)) || (this.inFunction > 0)) {
				this.inFunction += 1;
			}
			this.beginLine = false;
			if (this.inFunction > 0) {
				out();
			} else {
				out();
				if (!this.afterByOrSetOrFromOrSelect) {
					this.indent += 1;
					newline();
					this.beginLine = Boolean.TRUE;
				}
			}
			this.parensSinceSelect += 1;
		}

		private static boolean isFunctionName(final String tok) {
			final char begin = tok.charAt(0);
			final boolean isIdentifier = (Character
					.isJavaIdentifierStart(begin)) || ('"' == begin);
			return (isIdentifier)
					&& (!BasicFormatterImpl.LOGICAL.contains(tok))
					&& (!BasicFormatterImpl.END_CLAUSES.contains(tok))
					&& (!BasicFormatterImpl.QUANTIFIERS.contains(tok))
					&& (!BasicFormatterImpl.DML.contains(tok))
					&& (!BasicFormatterImpl.MISC.contains(tok));
		}

		private static boolean isWhitespace(final String token) {
			return " \n\r\f\t".contains(token);
		}

		private void newline() {
			this.result.append(System.lineSeparator());
			for (int i = 0; i < this.indent; i++) {
				this.result.append("    ");
			}
			this.beginLine = Boolean.TRUE;
		}
	}
}

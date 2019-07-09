/**
 * sql拼接抽象类
 */
package com.saas.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractSQL<T> {

	private static final String AND = ") \nAND (";
	private static final String OR = ") \nOR (";

	private SQLStatement sql = new SQLStatement();

	public abstract T getSelf();

	public T WHERE(String conditions) {
		sql().where.add(conditions);
		sql().lastList = sql().where;
		return getSelf();
	}

	public T OR() {
		sql().lastList.add(OR);
		return getSelf();
	}

	public T AND() {
		sql().lastList.add(AND);
		return getSelf();
	}
	
	public T ORDER_BY(String columns) {
	    sql().orderBy.add(columns);
	    return getSelf();
	  }
	
	private SQLStatement sql() {
		return sql;
	}

	public <A extends Appendable> A usingAppender(A a) {
		sql().sql(a);
		return a;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sql().sql(sb);
		return sb.toString();
	}

	private static class SafeAppendable {
		private final Appendable a;
		private boolean empty = true;

		public SafeAppendable(Appendable a) {
			super();
			this.a = a;
		}

		public SafeAppendable append(CharSequence s) {
			try {
				if (empty && s.length() > 0) {
					empty = false;
				}
				a.append(s);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return this;
		}

		public boolean isEmpty() {
			return empty;
		}

	}

	private static class SQLStatement {

		List<String> where = new ArrayList<String>();
		List<String> lastList = new ArrayList<String>();
		List<String> orderBy = new ArrayList<String>();
		public SQLStatement() {
		}

		private void sqlClause(SafeAppendable builder, String keyword, List<String> parts, String open, String close, String conjunction) {
			if (!parts.isEmpty()) {
				if (!builder.isEmpty()) {
					builder.append("\n");
				}
				builder.append(" ");
				builder.append(open);
				String last = "________";
				for (int i = 0, n = parts.size(); i < n; i++) {
					String part = parts.get(i);
					if (i > 0 && !part.equals(AND) && !part.equals(OR) && !last.equals(AND) && !last.equals(OR)) {
						builder.append(conjunction);
					}
					builder.append(part);
					last = part;
				}
				builder.append(close);
			}
		}

		private String selectSQL(SafeAppendable builder) {

			sqlClause(builder, "WHERE", where, "(", ")", " AND ");
			sqlClause(builder, "ORDER BY", orderBy, "", "", ", ");
			return builder.toString();
		}

		public String sql(Appendable a) {
			SafeAppendable builder = new SafeAppendable(a);
			String answer = selectSQL(builder);
			return answer;
		}
	}
}
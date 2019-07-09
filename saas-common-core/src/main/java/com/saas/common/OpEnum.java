package com.saas.common;

/**
 * SQL操作符
 * 
 * @author tanjun
 *
 */
public enum OpEnum {
	/**
	 * in
	 */
	IN("in", 0),
	/**
	 * between_and
	 */
	BETWEEM_AND("between_and", 1),
	/**
	 * like
	 */
	LIKE("like", 2),
	/**
	 * =
	 */
	EQUALS("=", 3),
	/**
	 * !=
	 */
	NOT_EQUALS("!=", 4);

	private String name;
	private int value;

	private OpEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public int getValue() {
		return this.value;
	}
}

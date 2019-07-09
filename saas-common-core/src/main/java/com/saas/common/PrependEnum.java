package com.saas.common;

/**
 * 前置操作符
 * @author tanjun
 *
 */
public enum PrependEnum {
	/**
	 * and
	 */
    AND("and",1),
    /**
     * or
     */
    OR("or",0),
    /**
     * none
     */
	NONE("none",-1);
    
    private String name;
    private int value;
    
    private PrependEnum(String name,int value) {
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

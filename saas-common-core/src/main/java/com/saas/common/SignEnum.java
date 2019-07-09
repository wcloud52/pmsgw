package com.saas.common;

/**
 * 符号 yes存在单引号  none不存在单引号
 * @author tanjun
 *
 */
public enum SignEnum {
	/**
	 * and
	 */
    YES("yes",1),
    /**
     * none
     */
    NONE("none",0);
    
    private String name;
    private int value;
    
    private SignEnum(String name,int value) {
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

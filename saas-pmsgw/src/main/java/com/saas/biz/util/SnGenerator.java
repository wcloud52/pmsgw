package com.saas.biz.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;

import java.util.Date;

public final class SnGenerator {
    private SnGenerator() {
    }

    public static String getSn(String prefix) {
    	DateTime dateTime = new DateTime(new Date());
		String stringFormat = dateTime.toString("yyyyMMddHHmmss");
		stringFormat=stringFormat+RandomStringUtils.randomNumeric(6);
        return prefix+stringFormat;
    }
    public static long getLongSn() {
    	DateTime dateTime = new DateTime(new Date());
		String stringFormat = dateTime.toString("yyyyMMddHHmmssSSS");
		long ret=Long.valueOf(stringFormat);
        return ret;
    }
}

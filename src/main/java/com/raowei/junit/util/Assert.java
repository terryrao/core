package com.raowei.junit.util;

/**
 * 断言
 * @author terryrao
 *
 */
public abstract class Assert {
	
	/**
	 * 断言不会为空
	 * @param obj
	 * @param msg
	 */
	public static void  isNull(Object obj,String msg) {
		if (obj == null) {
			throw new IllegalArgumentException(msg);
		}
	}
}

package com.raowei.util.beanUtils;

import com.raowei.junit.util.Assert;
import com.raowei.log.Log;

public class BeanUtils {
	private static final Log logger = Log.getLogger(BeanUtils.class);

	/**
	 * new an instance
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T instaniate(Class<T> clazz) {
		Assert.isNull(clazz, "Class must not be null");
		if (clazz.isInterface()) {
			throw new BeanInstantiationException(clazz, "Specified class is an interface");
		}
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new BeanInstantiationException(clazz, "is it an abstract class ? or contructor accessible", e);
		}
	}
	
}

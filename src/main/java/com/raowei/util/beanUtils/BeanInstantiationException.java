package com.raowei.util.beanUtils;

@SuppressWarnings("serial")
public class BeanInstantiationException extends FatalBeanException {
	private Class<?> beanClass;

	public BeanInstantiationException(String msg) {
		super(msg);
	}

	public BeanInstantiationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * create a new BeanInstantiationException
	 * 
	 * @param beanClass
	 * @param msg
	 * @param cause
	 */
	public BeanInstantiationException(Class<?> beanClass, String msg, Throwable cause) {
		super("Could not instantiate bean class [" + beanClass.getName() + "]:" + msg, cause);
		this.beanClass = beanClass;
	}
	
	/**
	 * create a new BeanInstantiationException
	 * @param beanClass
	 * @param msg
	 */
	public BeanInstantiationException(Class<?> beanClass,String msg) {
		super("Could not instantiate bean class [" + beanClass.getName() + "]:" + msg);
		this.beanClass = beanClass;
	}

	public Class<?> getBeanClass() {
		return beanClass;
	}
}

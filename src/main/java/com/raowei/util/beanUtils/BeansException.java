package com.raowei.util.beanUtils;

import com.raowei.exception.NestedRuntimeException;
import com.raowei.util.ObjectUtils;

/**
 * 在BeanUtils中抛出的异常的抽象类
 * @author terryrao
 *
 */
@SuppressWarnings("serial")
public abstract class BeansException extends NestedRuntimeException {

	public BeansException(String msg) {
		super(msg);
	}

	public BeansException(String msg, Throwable cause) {
		super(msg, cause);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BeansException)) {
			return false;
		}
		BeansException otherBe = (BeansException) other;
		return getMessage().equals(otherBe.getMessage()) && ObjectUtils.nullSaleEquals(getCause(), otherBe.getCause());
	}
	
	public int hashCode() {
		return getMessage().hashCode();
	}
}

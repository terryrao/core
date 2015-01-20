package com.raowei.exception;

import com.raowei.util.ObjectUtils;

public class BeansException extends NestedRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6028166377555136897L;

	public BeansException(String msg) {
		super(msg);
	}

	public BeansException(String msg, Throwable t) {
		super(msg, t);
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

	@Override
	public int hashCode() {
		return getMessage().hashCode();
	}
}

package com.raowei.exception;

import java.io.PrintWriter;

public class NestedRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -1998775134725806855L;

	private Throwable exceptionCause;
	static {
		// 加载NestedExceptionUtils以消除类加载器死锁
		NestedExceptionUtils.class.getName();
	}

	public NestedRuntimeException(String msg) {
		super(msg);
	}

	public NestedRuntimeException(Throwable t) {
		this.exceptionCause = t;
	}

	public NestedRuntimeException(String msg, Throwable t) {
		super(msg, t);
		this.exceptionCause = t;
	}

	/**
	 * 返回详细消息， 包括来自嵌套异常中的消息
	 */
	public String getMessage() {
		return NestedExceptionUtils.BuildMessage(super.getMessage(), super.getCause());
	}

	/**
	 * 检索最里面的异常引起的原因，如果有的话
	 * 
	 * @return 最里面的异常或者{@code null}如果没有的话
	 */
	public Throwable getRootCause() {
		Throwable rootCause = null;
		Throwable cause = super.getCause();
		while (cause != null && cause != rootCause) {
			rootCause = cause;
			cause = cause.getCause();
		}
		return rootCause;
	}

	public Throwable getMostSpecficCause() {
		Throwable rootCause = this.getRootCause();
		return (rootCause == null ? this : rootCause);
	}

	/**
	 * 检查这异常是否包含这个给定的异常：
	 * 
	 * @param exType
	 * @return
	 */
	public boolean contains(Class<?> exType) {
		if (exType == null) {
			return false;
		}
		if (exType.isInstance(this)) {
			return true;
		}

		Throwable cause = getCause();
		if (cause == this) { // TODO 不是特别懂
			return false;
		}

		if (cause instanceof NestedRuntimeException) {
			return ((NestedRuntimeException) cause).contains(exType);
		} else {
			while (cause != null) {
				if (exType.isInstance(cause)) {
					return true;
				}
				if (cause.getCause() == cause) {
					break;
				}
				cause = cause.getCause();
			}
			return false;
		}
	}

	/**
	 * overwrite
	 */
	@Override
	public void printStackTrace() {
		super.printStackTrace();
		if (exceptionCause != null) {
			synchronized (System.err) {
				System.err.println("\n Exception cause by : ");
				exceptionCause.printStackTrace();
			}
		}
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		super.printStackTrace(s);
		if (exceptionCause != null) {
			synchronized (s) {
				s.println("\nException cause by : ");
				exceptionCause.printStackTrace(s);
			}
		}
	}


}

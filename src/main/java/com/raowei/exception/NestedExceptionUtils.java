/*
 * Copyright 2014-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.raowei.exception;

/**
 * 实现那些能够持有嵌套的异常的异常类提供的帮助类， 它是必须的 因为我们不能在两个不同的异常类型之间分享一个基本的类
 * 
 * @author terryrao
 *
 */
public abstract class NestedExceptionUtils {

	/**
	 * 为提供的过来的信息和异常建立一个消息
	 * 
	 * @param msg the base message
	 * @param cause the root cause
	 * @return the full exception message
	 */
	public static String BuildMessage(String msg, Throwable cause) {
		if (cause != null) {
			StringBuilder sb = new StringBuilder();
			if (msg != null) {
				sb.append(msg).append("; ");
			}
			sb.append("nested exception is ").append(cause);
			return sb.toString();
		} else {
			return msg;
		}

	}
}

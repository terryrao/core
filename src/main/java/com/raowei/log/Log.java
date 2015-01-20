/*
 * Copyright 2002-2014 the original author or authors.
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
package com.raowei.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * a log facade for this project
 * 
 * @author terryrao
 *
 */
public class Log {
	private Logger logger = null;

	static class Singleton {
		static Log instance = new Log();
	}

	/**
	 * 获取Log实例
	 * 
	 * @return
	 */
	public static Log getLogger() {
		return Singleton.instance;
	}

	public static Log getLogger(Class<?> cls) {
		return new Log(cls);
	}

	public Log() {
		this.logger = null;
		this.logger = LoggerFactory.getLogger("test_logger");
	}

	public Log(Class<?> clazz) {
		logger = LoggerFactory.getLogger(clazz);
	}

	/* error */
	public void error(String message) {
		logger.error(message);
	}

	public void error(String message, Throwable e) {
		logger.error(message, e);
	}

	public void error(String string, Object... args) {
		logger.error(string, args);
	}

	/* warn */
	public void warn(String msg) {
		logger.warn(msg);
	}

	public void warn(String msg, Throwable t) {
		logger.warn(msg, t);
	}

	public void warn(String msg, Object... args) {
		logger.warn(msg, args);
	}

	/* debug */
	public void debug(String msg) {
		logger.debug(msg);
	}

	public void debug(String msg, Object... args) {
		logger.debug(msg, args);
	}

	public void debug(String msg, Throwable t) {
		logger.debug(msg, t);
	}

	/* info */
	public void info(String msg) {
		logger.info(msg);
	}

	public void info(String msg, Object... args) {
		logger.info(msg, args);
	}

	public void info(String msg, Throwable t) {
		logger.info(msg, t);
	}
}

package com.raowei.util;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 
 * @author terryrao
 *
 */
public class ObjectUtils {

	/**
	 * 检查是否是检查异常
	 * 
	 * @param ex
	 * @return
	 */
	public static boolean isCheckedException(Throwable ex) {
		return !(ex instanceof RuntimeException || ex instanceof Error);
	}

	/**
	 * 检查是否与指定的异常异常类型兼容
	 * 
	 * @param ex
	 * @param classes
	 * @return
	 */
	public static boolean isCompatiblejWithTrowClause(Throwable ex, Class<?>... declaredExceptions) {
		if (!isCheckedException(ex)) {
			return true;
		}
		if (declaredExceptions != null) {
			for (Class<?> declaredException : declaredExceptions) {
				if (declaredException.isInstance(ex)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 检查是否是数组
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isArray(Object obj) {
		return (obj != null && obj.getClass().isArray());
	}

	/**
	 * 检查数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(Object[] array) {
		return array == null || array.length == 0;
	}

	/**
	 * 检查给定的数组里是否有该元素
	 * 
	 * @param array
	 * @param element
	 */
	public static boolean containsElement(Object[] array, Object element) {
		if (isEmpty(array)) {
			return false;
		}

		for (Object arrayEle : array) {
			if (nullSaleEquals(arrayEle, element)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Append the given object to the given array, returning a new array consisting of the input array contents plus the
	 * given object.
	 * 
	 * @param array
	 * @param obj
	 * @return
	 */
	public static <A, O extends A> A[] addObjectToArray(A[] array, O obj) {
		Class<?> compType = Object.class;
		if (array != null) {
			compType = array.getClass().getComponentType();
		} else if (obj != null) {
			compType = obj.getClass();
		}
		int newArrayLength = (array != null ? array.length + 1 : 1);

		@SuppressWarnings("unchecked")
		A[] newArr = (A[]) Array.newInstance(compType, newArrayLength);
		if (array != null) {
			System.arraycopy(array, 0, newArr, 0, newArrayLength);
		}
		newArr[newArr.length - 1] = obj;
		return newArr;
	}

	/**
	 * 检查两个对象是否相等 如两两个都为空或equals相等返回{@code true} 只有一个为空则返回{@code false} 如果都是数组则使用Arrays.equals对比
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean nullSaleEquals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;  // null == null  return true;
		}
		
		if (o1 == null || o2 == null) {
			return false;
		}
		
		if (o1.equals(o2)) {
			return true;
		}
		
		if (o1.getClass().isArray() && o2.getClass().isArray()) {
			if (o1 instanceof Object[] && o2 instanceof Object[]) {
				return Arrays.equals((Object[]) o1, (Object[]) o2);
			}
			if (o1 instanceof Boolean[] && o2 instanceof Boolean[]) {
				return Arrays.equals((boolean[]) o1, (boolean[]) o2);
			}
			if (o1 instanceof byte[] && o2 instanceof byte[]) {
				return Arrays.equals((byte[]) o1, (byte[]) o2);
			}
			if (o1 instanceof char[] && o2 instanceof char[]) {
				return Arrays.equals((char[]) o1, (char[]) o2);
			}
			if (o1 instanceof double[] && o2 instanceof double[]) {
				return Arrays.equals((double[]) o1, (double[]) o2);
			}
			if (o1 instanceof float[] && o2 instanceof float[]) {
				return Arrays.equals((float[]) o1, (float[]) o2);
			}
			if (o1 instanceof int[] && o2 instanceof int[]) {
				return Arrays.equals((int[]) o1, (int[]) o2);
			}
			if (o1 instanceof long[] && o2 instanceof long[]) {
				return Arrays.equals((long[]) o1, (long[]) o2);
			}
			if (o1 instanceof short[] && o2 instanceof short[]) {
				return Arrays.equals((short[]) o1, (short[]) o2);
			}
		}
		
		return false;
	}
}

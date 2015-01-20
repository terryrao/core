package com.raowe.util;

import static org.junit.Assert.fail;

import org.junit.Test;

public class TestClass {

//	@Test
	public void test() {
		
		Object object = new Object();
		Object object2 = new Object();
		Object object3 = object;
		
		System.out.println(object == object2);
		System.out.println(object == object3);
		fail("Not yet implemented");
	}
//	@Test
	public void testNameGenerator(){
		String[] aStrings = new String[]{"sdfd","sdfa","sdfa"};
		String name = TestClass.unqualifiedClassName(aStrings.getClass());
		System.out.println(name);
		System.out.println(unqualifiedClassName(String.class));
		System.out.println(String.class.getComponentType());
	}
	@Test
	public void testAssignAbleFrom(){
		int a = 1;
		System.out.println(Throwable.class.isAssignableFrom(Exception.class));
	}
	
	public static String unqualifiedClassName(Class type) {
        if (type.isArray()) {
            return unqualifiedClassName(type.getComponentType())+"Array";
        }
        String name = type.getName();
        return name.substring(name.lastIndexOf('.')+1);
    }
}

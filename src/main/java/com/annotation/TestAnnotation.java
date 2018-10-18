package com.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestAnnotation {
	
	@AnnoParam(id = 0, name = "balanner", address = "湖北")
    public String appleName;
    
    @AnnoMethod(id = 1, name = "apple", address = "通城")
    public void output() {
        System.out.println("method output is running ");
    }

	public static void main(String[] args) {
		try {
			// 获得要调用的类
			Class<TestAnnotation> myTestClass = TestAnnotation.class;
			// 获得要调用的方法，output是要调用的方法名字，new Class[]{}为所需要的参数。空则不是这种
			
			Field field = myTestClass.getField("appleName");
			if (field.isAnnotationPresent(AnnoParam.class)){
				// 获得注解
				AnnoParam annotation = field.getAnnotation(AnnoParam.class);
			    // 调用注解的内容
			    System.out.println(annotation.id());
			    System.out.println(annotation.name());
			    System.out.println(annotation.address());
			}
			// 获得所有注解。必须是runtime类型的
			Annotation[] annotations = field.getAnnotations();
			for (Annotation annotation : annotations) {
			    // 遍历所有注解的名字
			    System.out.println("--注解名称--" + annotation.annotationType().getName());
			}
			System.out.println("----------------------------------");
			Method method = myTestClass.getMethod("output", new Class[]{});
			// 是否有类型为MyAnnotation的注解
			if (method.isAnnotationPresent(AnnoMethod.class)){
			    // 获得注解
				AnnoMethod annotation = method.getAnnotation(AnnoMethod.class);
			    // 调用注解的内容
			    System.out.println(annotation.id());
			    System.out.println(annotation.name());
			    System.out.println(annotation.address());
			}
			System.out.println("----------------------------------");
			
			// 获得所有注解。必须是runtime类型的
			annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
			    // 遍历所有注解的名字
			    System.out.println("--注解名称--" + annotation.annotationType().getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getAppleName() {
		return appleName;
	}

	public void setAppleName(String appleName) {
		this.appleName = appleName;
	}
	

}

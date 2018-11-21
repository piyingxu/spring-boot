package com.proxy;

import com.annotation.A;
import com.web.WebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2018/11/21 15:11
 */
public class PersonProxy {

    private Logger logger = LoggerFactory.getLogger(WebController.class);

    private Person person = new WoMan();

    public Person getProxy() {
        //第一个对象是类加载器，任何一个对象都可以获取到类加载器
        return (Person) Proxy.newProxyInstance(A.class.getClassLoader(), person.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        logger.info("方法调用开始，proxy={},method={},args={}", proxy, method, args);
                        //中间可以处理其他业务额~
                        ////代理对象调用真实目标对象的dance方法去处理用户请求
                        Object methodRet = method.invoke(person, args); // 等价于obj.method(args)
                        logger.info("方法调用结束");
                        return methodRet;
                    }
                });
    }

    public static void main(String[] args) {
        try {
            PersonProxy proxy = new PersonProxy();
            Person person = proxy.getProxy();
            person.sing("xp");

            /* 查看代理类字节码文件
            byte[] classFile = ProxyGenerator.generateProxyClass("Proxy0", person.getClass().getInterfaces());
            File file = new File("E:/Proxy0.class");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(classFile);
            fos.flush();
            fos.close();
            */
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

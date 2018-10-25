package com.annotation;

import org.springframework.context.annotation.Configuration;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2018/10/25 15:28
 */
@Configuration("makeC1")
public class D {
    public D() {
        System.out.println("D is call");
    }
    public void DdoSth () {
        System.out.println("D do Sth");
    }
}

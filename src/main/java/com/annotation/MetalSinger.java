package com.annotation;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2018/10/25 10:17
 */
@Component("MetalSinger") // 加注解，让spring识别
//@Primary //在众多相同的bean中，优先选择用@Primary注解的bean
public class MetalSinger implements Singer{

    @Override
    public String sing(String lyrics) {
        return "I am singing with MetalSinger voice: "+lyrics;
    }
}

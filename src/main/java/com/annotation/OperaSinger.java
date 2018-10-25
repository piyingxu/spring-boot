package com.annotation;

import org.springframework.stereotype.Component;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2018/10/25 10:17
 */
@Component("OperaSinger") // 加注解，让spring识别
public class OperaSinger implements Singer{

    @Override
    public String sing(String lyrics) {
        return "I am singing in OperaSinger voice: "+lyrics;
    }
}

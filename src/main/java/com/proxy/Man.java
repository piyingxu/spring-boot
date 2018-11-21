package com.proxy;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2018/11/21 15:09
 */
public class Man implements Person {

    @Override
    public void sing(String name) {
        System.out.println(name + " Man 在唱征服");
    }

    @Override
    public void dance(String name) {
        System.out.println(name + " Man 在跳舞娘");
    }
}

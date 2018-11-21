package com.proxy;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2018/11/21 15:09
 */
public class WoMan implements Person {

    @Override
    public void sing(String name) {
        System.out.println(name + " WoMan 在唱征服");
    }

    @Override
    public void dance(String name) {
        System.out.println(name + " WoMan 在跳舞娘");
    }
}

package com.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2018/10/25 14:00
 */
@Configuration
public class ConditionalOnMissingBeanConfig {

    @Bean(name = "beanAA")  // @Bean(name = "beanAA")与@Bean的区别在于，第一种指定了bean的名称，第二种没有指定name则名称为方法名beanA
    public A beanA() {
        System.out.println("beanA is call");
        return new A(); // 无条件定义一个bean : beanA
    }

    @Bean
    @ConditionalOnMissingBean(name = "beanA")
    public B beanB() {
        System.out.println("beanB is call");
        // 因为上面的方法已经定义了一个 beanA，所以这里 beanB定义并不会发生。(beanA被修改为beanAA)
        return new B();
    }

    @Bean
    @ConditionalOnMissingBean //如果没有配置name则默认按方法名beanC作为条件查找
    public C beanC() {
        System.out.println("beanC is call");
        // 如果 beanFactory 中存在一个名称为 beanD的 bean，才定义bean ： beanC；
        return new C();
    }

    @Bean
    public C beanCC() {
        System.out.println("beanCC is call");
        return new C();
    }

    //----------------如果存在创建2个名字一样的，则只创建1个
    /*@Bean
    public C makeC1() {
        System.out.println("makeC1 is call");
        return new C();
    }

    @Bean(name="makeC1")
    public A makeC2() {
        System.out.println("makeC2 is call");
        return new A();
    }
*/
}

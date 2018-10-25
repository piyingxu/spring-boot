package com.annotation;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.feign.FeignClientTest;

@Component
public class TestAnnoOne {
	/*
	一、@Component
	          （把普通pojo实例化到spring容器中，相当于配置文件中的 <bean id="" class=""/>）
                        泛指各种组件，就是说当我们的类不属于各种归类的时候（不属于@Controller、@Services等的时候），我们就可以使用@Component来标注这个类。
        @Service,@Controller,@Repository底层都继承了@Component类
            二、@Configuration
            1.1、@Configuration配置spring并启动spring容器
            1.2、@Configuration启动容器+@Bean注册Bean
            1.3、@Configuration启动容器+@Component注册Bean
                                     方法一：
            @Configuration
			public class MyBeanConfig {
			    @Bean
			    public Country country(){
			        return new Country();
			    }
			    @Bean
			    public UserInfo userInfo(){
			        return new UserInfo(country());
			    }
			}
			 方法二：
            @Component
            public class MyBeanConfig {
			    @Bean
			    public Country country(){
			        return new Country();
			    }
			    @Bean
			    public UserInfo userInfo(){
			        return new UserInfo(country());
			    }
            }
            @Configuration与@Component的区别在于，@Configuration中用userInfo中得到的Country与自己调用country()得到的是同一个Country对象，@Component
                                    则是不同对象，@Component 注解并没有通过 cglib 来代理@Bean 方法的调用，调用任何方法，使用任何变量，拿到的是原始对象
            三、@Bean       
          @Bean 只能放在方法上，就是产生一个Bean，然后交给Spring容器
                              可以将普通的类注入给Spring容易，然后可以在其他地方使用@Autowired调用 
	四、@Resource 与 @Autowired
		@Resource和@Autowired都是做bean的注入时使用
		这是详细一些的用法，说一下@Resource的装配顺序：
		(1)、@Resource后面没有任何内容，默认通过name属性去匹配bean，找不到再按type去匹配
		(2)、指定了name或者type则根据指定的类型去匹配bean
		(3)、指定了name和type则根据指定的name和type去匹配bean，任何一个不匹配都将报错
	
		 然后，区分一下@Autowired和@Resource两个注解的区别：
		(1)、@Autowired默认按照byType方式进行bean匹配，@Resource默认按照byName方式进行bean匹配
		(2)、@Autowired是Spring的注解，@Resource是J2EE的注解，这个看一下导入注解的时候这两个注解的包名就一清二楚了
	
	    @Resource 中的name表示我所要注入的目标对象来源于容器中的哪个对象，根据名字找，type则根据Class的类型找
	 五、@Qualifier
	   @Qualifier("softService"),使用 @Autowired时，如果找到多个同一类型的bean，则会抛异常，此时可以使用 @Qualifier("beanName")，明确指定bean的名称进行注入，此时与 @Resource指定name属性作用相同。
	
	 六、@FreshScope
        spring-cloud 实现更新配置不用重启服务 @FreshScope
        浏览器上输入localhost:8889/foo会看到获取到的数据。去gitlab修改下fzk-beta.properties，重新在浏览器上输入，发现现在获取的还是原来的数据，并没有修改。从服务端(http://localhost:8888/fzk/beta)可以获取到最新的数据。这里想让client端不重启服务就能获取到更新后的数据需要手动发送一个post请求到client端(http://localhost:8889/fresh)
        $ curl -X POST http://localhost:8889/refresh
        ["config.client.version","fzk.nick"]
        所以想说的是，这里并不是完全的自动。还需要调用一个接口，这个接口一般是通过存放config的push事件来触发的，如果一个服务可以直接写在webhook中。但是如果需要触发多个服务自动更新，可以在jenkins配置一个job，webhook出去这个job，这个job来触发多个服务的post请求操作。
	七、@Primary   英[ˈpraɪməri]
        在众多相同的bean中，优先选择用@Primary注解的bean（该注解加在各个bean上）
    八、@Qualifier 英[ˈkwɒlɪfaɪə(r)]
        在众多相同的bean中，@Qualifier指定需要注入的bean（该注解跟随在@Autowired后）
    九、 @Conditional
         @Conditional是Spring4新提供的注解，它的作用是按照一定的条件进行判断，满足条件给容器注册bean。

    一个方法只能注入一个bean实例，所以@Conditional标注在方法上只能控制一个bean实例是否注入。
            @Configuration
            public class BeanConfig {
                //只有一个类时，大括号可以省略
                //如果WindowsCondition的实现方法返回true，则注入这个bean
                @Conditional({WindowsCondition.class})
                @Bean(name = "bill")
                public Person person1(){
                    return new Person("Bill Gates",62);
                }

                //如果LinuxCondition的实现方法返回true，则注入这个bean
                @Conditional({LinuxCondition.class})
                @Bean("linus")
                public Person person2(){
                    return new Person("Linus",48);
                }
            }
            ---------------------
    一个类中可以注入很多实例，@Conditional标注在类上就决定了一批bean是否注入。
        public class ObstinateCondition implements Condition {
            @Override
            public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
                 return true; // true注入：false不注入
            }
        }
        public class ObstinateCondition implements Condition {
            @Override
            public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
                 return true;
            }
        }
        @Conditional({WindowsCondition.class,ObstinateCondition.class})
        @Configuration
        public class BeanConfig {
            @Bean(name = "bill")
            public Person person1(){
                return new Person("Bill Gates",62);
            }

            @Bean("linus")
            public Person person2(){
                return new Person("Linus",48);
            }
        ---------------------
       第一个条件类实现的方法返回true，第二个返回false，则结果false，不注入进容器。
       第一个条件类实现的方法返回true，第二个返回true，则结果true，注入进容器中。
	*/
	
	@Autowired
	private FeignClientTest feignClientTest;

	@Resource(name = "TestAnnoTwo") //(name="", type=AsyncService.class)
	private TestAnnoTwo testAnnoTwo_0;
	
	@Resource(name = "TestAnnoTwo") //(name="", type=AsyncService.class)
	private TestAnnoTwo testAnnoTwo_1;

    @Autowired
    @Qualifier("OperaSinger") //如果有多个指定特定的
    private Singer singer;

	public void test () {
		//测试单例，多例--（结果：testAnnoTwo_0与testAnnoTwo_1是不是同一个对象，取决于TestAnnoTwo对象@Scope）
		testAnnoTwo_0.getName();
		testAnnoTwo_1.getName();
		//测试 @Component与 @Configuration的区别--（结果：testAnnoTwo_2与testAnnoTwo_3不是同一个对象）
		TestAnnoTwo testAnnoTwo_2 = getBeanTwo();
		TestAnnoTwo testAnnoTwo_3 = getBeanTwo_other();
		testAnnoTwo_2.getName();
		testAnnoTwo_3.getName();
		//测试 @Primary
        System.out.println(singer.sing("pyx"));
	}

	@Bean
	public TestAnnoTwo getBeanTwo () {
		return new TestAnnoTwo();
	}

	@Bean
	public TestAnnoTwo getBeanTwo_other () {
		return getBeanTwo();
	}
	

}

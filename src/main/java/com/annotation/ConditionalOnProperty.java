package com.annotation;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/1/24 18:14
 */
public class ConditionalOnProperty {
    /*
    问题

    在最近的项目中遇到一个实际问题，该项目要与老项目整合，但是该项目与老项目用的数据库不是同一个，因此要做数据库同步。由于数据库同步与正常业务解耦，仅仅依赖该项目处理后的数据，再加上数据库同步用的Oracle，因此打算在dev版本上面不加入数据库同步，在test与prod版本上加入数据库同步。这样就要求在dev版本下，对第二个数据源的配置不生效；而test与prod版本下，第二个数据源生效。
    解决方案

    经过一番寻觅，发现了Spring boot中有个注解@ConditionalOnProperty，这个注解能够控制某个configuration是否生效。具体操作是通过其两个属性name以及havingValue来实现的，其中name用来从application.properties中读取某个属性值，如果该值为空，则返回false;如果值不为空，则将该值与havingValue指定的值进行比较，如果一样则返回true;否则返回false。如果返回值为false，则该configuration不生效；为true则生效。
    代码

    @Configuration
    //如果synchronize在配置文件中并且值为true
    @ConditionalOnProperty(name = "synchronize", havingValue = "true")
    public class SecondDatasourceConfig {

        @Bean(name = "SecondDataSource")
        @Qualifier("SecondDataSource")
        @ConfigurationProperties(prefix = "spring.second.datasource")
        public DataSource jwcDataSource() {
            return DataSourceBuilder.create().build();
        }
    }
---------------------
    作者：大浪中航行
    来源：CSDN
    原文：https://blog.csdn.net/dalangzhonghangxing/article/details/78420057
    版权声明：本文为博主原创文章，转载请附上博文链接！
    */
}

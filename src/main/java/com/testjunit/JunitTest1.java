package com.testjunit;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/*
JUnit4使用Java5中的注解（annotation），以下是JUnit4常用的几个annotation： 
@Before：初始化方法   对于每一个测试方法都要执行一次（注意与BeforeClass区别，后者是对于所有方法执行一次）
@After：释放资源  对于每一个测试方法都要执行一次（注意与AfterClass区别，后者是对于所有方法执行一次）
@Test：测试方法，在这里可以测试期望异常和超时时间 
@Test(expected=ArithmeticException.class)检查被测方法是否抛出ArithmeticException异常 
@Ignore：忽略的测试方法 
@BeforeClass：针对所有测试，只执行一次，且必须为static void 
@AfterClass：针对所有测试，只执行一次，且必须为static void 
一个JUnit4的单元测试用例执行顺序为： 
@BeforeClass -> @Before -> @Test -> @After -> @AfterClass; 
每一个测试方法的调用顺序为： 
@Before -> @Test -> @After; 
*/

public class JunitTest1 {

    private List<String> collection;

    @BeforeClass //
    public static void oneTimeSetUp() {
        // one-time initialization code   
    	System.out.println("@BeforeClass - oneTimeSetUp");
    }

    @AfterClass
    public static void oneTimeTearDown() {
        // one-time cleanup code
    	System.out.println("@AfterClass - oneTimeTearDown");
    }

    @Before
    public void setUp() {
        collection = new ArrayList();
        System.out.println("@Before - setUp");
    }

    @After
    public void tearDown() {
        collection.clear();
        System.out.println("@After - tearDown");
    }

    @Test
    public void testEmptyCollection() {
        assertTrue(collection.isEmpty());
        System.out.println("@Test - testEmptyCollection");
    }

    @Test
    @Ignore
    public void testOneItemCollection() {
        collection.add("itemA");
        assertEquals(1, collection.size());
        System.out.println("@Test - testOneItemCollection");
    }
}
package com.util.test_ReflectionToStringBuilder;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/2/28 16:36
 */
public class TestObjA extends BaseEntity {

    private int a;

    private String b;

    private TestObjB bb;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public TestObjB getBb() {
        return bb;
    }

    public void setBb(TestObjB bb) {
        this.bb = bb;
    }
}

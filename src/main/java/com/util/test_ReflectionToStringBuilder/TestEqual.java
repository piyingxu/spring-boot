package com.util.test_ReflectionToStringBuilder;


/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/2/28 17:06
 */
public class TestEqual {

    public static int myHashCode(String target) {
        char value[] = target.toCharArray();
        int h = 0;
        if (h == 0 && value.length > 0) {
            char val[] = value;

            for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
            }
        }
        return h;
    }

    public static void main(String[] args) {
      /*
        TestObjA testObjA = new TestObjA();
        testObjA.setA(1);
        testObjA.setB("TestObjA--b");
        TestObjB testObjB = new TestObjB();
        testObjB.setA(2);
        testObjB.setB("TestObjB--B");
        testObjA.setBb(testObjB);


        TestObjA testObjA1 = new TestObjA();
        testObjA1.setA(1);
        testObjA1.setB("TestObjA--b");
        TestObjB testObjB1 = new TestObjB();
        testObjB1.setA(2);
        testObjB1.setB("TestObjB--B");
        testObjA1.setBb(testObjB1);

        System.out.println(testObjA);
        System.out.println(ReflectionToStringBuilder.toString(testObjA, ToStringStyle.JSON_STYLE));
        //System.out.println(testObjA.equals(testObjA1));

        //AutoLog log = new AutoLog();
        //System.out.println(ReflectionToStringBuilder.toString(log, ToStringStyle.JSON_STYLE));
      */

      String s1 = "abc";
      String s2 = "abc";
      String s3 = new String("abc");
      String s4 = new String();
      s4 = s1;
      /*
      System.out.println(s1);
      System.out.println(s2);
      System.out.println(s1==s2);
      System.out.println(s1==s3);
      System.out.println(s1==s4);
      System.out.println("s1=" + s1.hashCode() + ",s2=" + s2.hashCode() + ",s3=" + s3.hashCode() + ",s4=" + s4.hashCode());
      */
      String s5 = "谢谢";
      //System.out.println(s5.hashCode());
      System.out.println(myHashCode(s5));
      int a = 35874;
      System.out.println((char)a);
    }
}

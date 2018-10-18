package com.java8;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.alibaba.fastjson.JSONObject;

public class CollectorsTest {
	/*
		**.collect(Collectors.toList()); 或者  **.collect(Collectors.toCollection(ArrayList::new));
		**.collect(Collectors.toSet());  或者  **.collect(Collectors.toCollection(HashSet::new));
		**.collect(Collectors.toMap(...));
	*/
	//在控制台输出0～100的例子
	public static void getNum(int startNum, int endNum) {
		 List<Integer> tempList = new ArrayList<>();
		 IntStream.range(startNum, endNum).forEach(num -> {
			 System.out.println(num);
			 tempList.add(num);
		 });
		 System.out.println(JSONObject.toJSONString(tempList));
		 System.out.println(tempList.stream().count() == tempList.size());
    }
	
	//返回List,关键字：
	public static void toListTest(int startNum, int endNum) {
		 List<Integer> tempList = IntStream.range(startNum, endNum).boxed().collect(Collectors.toList()); //方法一
		               tempList = IntStream.range(startNum, endNum).boxed().collect(Collectors.toCollection(ArrayList::new));//方法二
		 System.out.println(JSONObject.toJSONString(tempList));
    }
	
	//给定一个对象列表，将对象的某个属性收集进一个列表
    public static List<Integer> byFiledToList(List<Task> tasks) {
    	List<Integer> tempList = tasks.stream().map(a -> a.getId()).collect(Collectors.toList());
    	// List<Integer> tempList = tasks.stream().map(Task::getId).collect(toList());
    	// a -> a.getId() 与 Task::getId 是等价的
    	System.out.println(JSONObject.toJSONString(tempList));
        return tempList;
    }
	
    //给定一个对象列表，将对象的某个属性收集进一个Set
    public static Set<Integer> listToSet(List<Task> tasks) {
    	Set<Integer> tempSet = tasks.stream().map(obj -> obj.getId()).collect(Collectors.toSet()); //方法一
    	                       tasks.stream().map(a -> a.getId()).collect(Collectors.toCollection(HashSet::new));//方法二
    	System.out.println(JSONObject.toJSONString(tempSet));
    	return tempSet;
    }
    
    //给定一个对象列表，将对象收集进一个Map
    public static Map<Integer, Task> listToMap(List<Task> tasks) {
    	//Map<Integer, Task> tempMap = tasks.stream().collect(Collectors.toMap(k -> k.getId(), k -> k));
    	//Map<Integer, Task> tempMap = tasks.stream().collect(Collectors.toMap(Task::getId, k -> k));
    	//Map<Integer, Task> tempMap = tasks.stream().collect(Collectors.toMap(Task::getId, Function.identity())); //k -> k 等价于 Function.identity()，都是代表本身
    	//上面的写法，当有重复的key时，则会报错
    	Map<Integer, Task> tempMap = tasks.stream().collect
    	(Collectors.toMap(Task::getId, Function.identity(), (a,b) -> b ));// 
    	 
    	
    	System.out.println(JSONObject.toJSONString(tempMap));
    	return tempMap;
    }
    
    // 给定一个对象列表，转换成其他对象列表
    public static List<TaskSon> toOtherList(List<Task> tasks) {
    	List<TaskSon> tempList = tasks.stream().map(obj -> {
    		TaskSon son = new TaskSon(obj.getId(), obj.getType(), obj.getTitle(), obj.getDesc());
    		return son;
    	}).collect(Collectors.toList());
    	System.out.println(JSONObject.toJSONString(tempList));
        return tempList;
    }
    
    //将某个字段用分隔符连在一起,只针对字符串
    public static String allTitles(List<Task> tasks) {
    	 String joinStr = tasks.stream().map(a -> a.getTitle()).collect(Collectors.joining(","));
    	 System.out.println(joinStr);
    	 return joinStr;
    }
    
    //生产统计信息
    public static void totalInfo(List<Task> tasks) {
    	// summarizingInt、summarizingLong、summarizingDouble
    	IntSummaryStatistics summaryStatistics = tasks.stream().map(Task::getDesc).collect(Collectors.summarizingInt(String::length));//对字段字符串长度
    	System.out.println(summaryStatistics.getAverage()); 
    	System.out.println(summaryStatistics.getCount());
    	System.out.println(summaryStatistics.getMax()); 
    	System.out.println(summaryStatistics.getMin()); 
    	System.out.println(summaryStatistics.getSum());
    }
    
    //根据年龄对Person进行分组
    public static void objGroup (List<Person> persons) {
    	Map<Integer, List<Person>> collect = persons.stream().collect(Collectors.groupingBy(Person::getAge));
    	System.out.println(JSONObject.toJSONString(collect));
    }
    
    //对所有Person求平均年龄
    public static void objAver (List<Person> persons) {
    	Double collect = persons.stream().collect(Collectors.averagingInt(Person::getAge));
    	System.out.println(JSONObject.toJSONString(collect));
    }
    
    
	public static void main(String[] args) {
		//CollectorsTest.getNum(1, 10);
		//CollectorsTest.toListTest(1, 10);
		Task t1 = new Task(11, 1, "a", "b");
		Task t2 = new Task(22, 2, "c", "dd");
		Task t3 = new Task(33, 3, "e", "fff");
		List<Task> taskList = new ArrayList<>();
		taskList.add(t1);
		taskList.add(t2);
		taskList.add(t3);
		//CollectorsTest.byFiledToList(taskList);
		//CollectorsTest.toOtherList(taskList);
		//CollectorsTest.listToSet(taskList);
		//CollectorsTest.listToMap(taskList);
		//CollectorsTest.allTitles(taskList);
		//CollectorsTest.totalInfo(taskList);
		/*
		Optional<String> name = Optional.of("LinkinPark");
		if (name.isPresent()) {
			//在Optional实例内调用get()返回已存在的值
			System.out.println(name.get());//输出LinkinPark
		}
		*/
		
		List<Person> personList = new ArrayList<>();
		Person p1 = new Person("a", 1);
		Person p2 = new Person("b", 1);
		Person p3 = new Person("c", 1);
		Person p11 = new Person("A", 2);
		Person p22 = new Person("B", 2);
		Person p33 = new Person("C", 2);
		personList.add(p1);personList.add(p2);personList.add(p3);
		personList.add(p11);personList.add(p22);personList.add(p33);
		//CollectorsTest.objGroup(personList);
		CollectorsTest.objAver(personList);
		
		
	}

}

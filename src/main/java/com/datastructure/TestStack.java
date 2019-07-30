package com.datastructure;

import com.alibaba.druid.support.json.JSONUtils;
import java.util.Stack;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/7/30 11:03
 */
public class TestStack {
    /*
    假设是一个二维数组，1代表墙壁不能通过，0代表门可以通过，假设maze[1][1]为起点，maze[8][8]为终点，除去最外层的坐标外
    其他坐标都可以有四个方向，左、右、上、下, maze[x][y]--> maze[x-1][y], maze[x+1][y], maze[x][y-1], maze[x][y+1]
    */
    private static int [][] maze = new int[][] {
        {1,1,1,1,1,1,1,1,1,1},
        {1,0,0,1,0,0,0,1,0,1},
        {1,0,0,1,0,0,0,1,0,1},
        {1,0,0,0,0,1,1,0,0,1},
        {1,0,1,1,1,0,0,0,0,1},
        {1,0,0,0,1,0,0,0,0,1},
        {1,0,1,0,0,0,1,0,0,1},
        {1,0,1,1,1,0,1,1,0,1},
        {1,1,0,0,0,0,0,1,0,1},
        {1,1,1,1,1,1,1,1,1,1}
    };

    //{1,0}-->x+1,y+0(向右)，以此类推
    private static int [][] direction = new int[][] {
            {1,0}, //向右
            {-1,0},//向左
            {0,1}, //向上
            {0,-1} //向下
    };

    private int targetX = 8;

    private int targetY = 8;

    private int finished = -1; //已经走过的坐标标记

    class Position {
        private int x;
        private int y;
        Position(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private Stack<Position> rltStack =  new Stack<Position>();

    private void getResult () {
        Position first = new Position(1, 1);
        rltStack.push(first); //起始位置
        boolean find_target = false;
        while (!rltStack.empty()) { //如果栈中没有元素了则表示无路可走了结束，如果有元素则一直探索方向直到找到目标坐标
            Position temp = rltStack.peek();//获取栈顶元素且不拿出
            int x = temp.x;
            int y = temp.y;
            boolean can_go = false;
            for (int i=0;i<direction.length;i++) {
                int x_offset = direction[i][0];
                int y_offset = direction[i][1];
                int x_new = x + x_offset;
                int y_new = y + y_offset;
                if (maze[x_new][y_new] == 0) {
                    //此方向可走
                    maze[x_new][y_new] = finished;//标记已走过，如果不标记的话下次有可能走到这里造成死循环！！
                    Position ok = new Position(x_new, y_new);
                    rltStack.push(ok);
                    can_go = true;
                    //判断是否为目标坐标，如果是则退出整个循环
                    if (x_new == targetX && y_new == targetY) {
                        find_target = true;
                    }
                    break;
                }
            }
            if (!can_go) {
                rltStack.pop(); //丢弃栈顶元素(往后退一步)
            }
            if (find_target) {
                //找到目标坐标则退出
                break;
            }
        }
        //打印出路径
        for (Position p: rltStack) {
            System.out.println(p.x + "," + p.y);
        }
    }

    public static void main(String[] args) {
        /*
        Stack<String> stackObj = new Stack<String>();
        stackObj.push("A"); //进栈
        stackObj.push("B");
        stackObj.push("C");
        System.out.println(JSONUtils.toJSONString(stackObj));
        stackObj.pop();  //出栈且删除（先进后出）
        stackObj.peek(); //出栈且不删除（先进后出）
        System.out.println(JSONUtils.toJSONString(stackObj)); // --输出["A","B"]
        System.out.println(stackObj.get(0)); // --A 下标从0开始，遍历也是（和数组没区别）
        System.out.println(stackObj.search("A")); // --输出2，源码中是size-下标
        */
        TestStack stack = new TestStack();
        stack.getResult();
    }
}

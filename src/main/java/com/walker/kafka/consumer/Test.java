package com.walker.kafka.consumer;

/**
 * Created by walker on 2016/8/20.
 */
public class Test {
    // main函数，用来测试List类
    public static void main(String[] args) {
        List cjmo = new List(1);
        cjmo.add(3);
        cjmo.add(55);
        cjmo.add(37);
        cjmo.add(33);
        cjmo.add(28);
        List.iterPrint(cjmo);
        System.out.println(cjmo.find_and_delete(55));
        System.out.println(cjmo.find_(55));
        List.iterPrint(cjmo);
//        System.out.println(cjmo.find_(1));
//        System.out.println(cjmo.find_(33));
    }

    //静态内部类
    static class Node {
        int data;         //每个Node有两个属性：1）当前数据data 2）前一个Node的引用previous
        Node previous;

        Node(int data_, Node previous_) {
            data = data_;
            previous = previous_;
        }
    }
    //静态内部类
    static class List {   //List中持有的是node
        Node head;           //这里的head是多余的，只需要tail即可完整表达一个list；
        Node tail;

        List(int first_data) {  //这是构造函数，用于第一次初始化List
            head = new Node(first_data, null);  //新建一个节点，既是head也是tail，因为只有一个节点，previous为null
            tail = head; //可以去掉head，直接写成：tail = new Node(first_data, null);
        }

        void add(int data_) {  //添加数据，添加到尾部，逻辑是：保存之前尾巴为old_tail，新建一个节点作为尾巴，并把之前的old_tail设置为新尾巴的previous
            Node old_tail = tail;
            tail = new Node(data_, old_tail);
        }

        boolean find_(int data_) { //逻辑是：从尾巴开始逐一往前循环遍历；如果当前节点current的数据与目标值相等，就返回true，否则前进一格，继续判断；
            // TODO
            Node current = tail;
            while (current.previous != null) {
                if (current.data == data_) {
                    return true;
                } else {
                    current = current.previous;
                }
            }
            return false;
        }

        boolean find_and_delete(int data_) { //找到并删除：查找逻辑与上面相同，但删除逻辑需要改变指针；
            Node current = tail;
            Node post = null;
            while (current.previous != null) {
                if (current.data == data_) {
                    if (current == tail) {
                        tail = current.previous;   //删除的如果是尾巴，需要特殊处理：直接把当前尾巴的前一个node作为新的尾巴。
                    } else {
//                        post.previous = post.previous.previous;
                        //或者
                        post.previous = current.previous; //关系图：previous <- current <- post ;
                                                          // 删除current，即把post的前一个设置为当前的前一个，相当于跳过当前值；
                                                           //新的关系图：previous <- post

                    }
                    return true;
                } else {
                    post = current; //记录下当前的位置，标记为post
                    current = current.previous;//当前记录的指针current前进一格；
                }
            }
            return false;
        }

        public static void iterPrint(List list) { //从尾巴开始 循环遍历，打印出所有节点的数据值
            Node current = list.tail;
            while (current.previous != null) {
                System.out.print(" "+current.data+" ");
                current = current.previous;
            }
            System.out.println();
        }
    }
}

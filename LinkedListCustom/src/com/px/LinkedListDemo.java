package com.px;

import com.px.list.CustomLinkedList;
import com.px.list.CustomList;


/**
 *
 */
public class LinkedListDemo {

    public static void main(String[] args) {

        CustomList<String> customList =  new CustomLinkedList<String>();

        for (int i = 0; i < 50; i++) {
            customList.add("i:" + i);
        }
        String value = customList.get(20);
        System.out.println("添加后customList内元素为：" + customList.size() + "个");
        System.out.println("添加后下标为20的值：" + value);

        System.out.println("------------------------------------------------------------");

        customList.remove("i:20");
        String value1 = customList.get(20);
        System.out.println("删除值为“i:20”后下标为20的值：" + value1);
        System.out.println("删除值为“i:20”后customList内元素为：" + customList.size() + "个");

        System.out.println("------------------------------------------------------------");

        customList.remove(20);
        String value2 = customList.get(20);
        System.out.println("删除下标为20后下标为20的值：" + value2);
        System.out.println("删除下标为20后customList内元素为：" + customList.size() + "个");

        System.out.println("------------------------------------------------------------");

        customList.add(20, "i:20");
        String value3 = customList.get(20);
        System.out.println("添加元素为“i:20” 下标为20后下标为20的值：" + value3);
        System.out.println("\"添加元素为“i:20” 下标为20后customList内元素为：" + customList.size() + "个");

    }
}

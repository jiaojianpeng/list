package com.px;

import com.px.list.CustomArrayList;
import com.px.list.CustomList;

public class CustomListTest {

    public static void main(String[] args) {
        CustomList<String> list = new CustomArrayList<String>();

        for (int i = 0; i < 100; i++) {
            list.add("自定义ArrayList：" + i);
        }


        System.out.println("删除前集合容量：" + list.size());
        System.out.println("删除前下标20的值：" + list.get(20));

        list.remove(20);
        list.remove("自定义ArrayList：21");

        System.out.println("删除后添加前容量：" + list.size());
        System.out.println("删除后添加前下标20的值：" + list.get(20));

        list.add(20, "自定义ArrayList：21");

        System.out.println("添加后容量：" + list.size());
        System.out.println("添加后下标20的值：" + list.get(20));

    }

}

package com.px.list;

public interface CustomList<E> {

    boolean add(E e);

    boolean add(int index, E e);

    boolean remove(E e);

    E remove(int index);

    E get(int index);

    int size();
}

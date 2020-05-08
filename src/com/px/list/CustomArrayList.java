package com.px.list;

import java.util.Arrays;

public class CustomArrayList<E> implements CustomList<E> {

    //说明：参数都是从ArrayList中copy的

    //默认数组容量
    private static final int DEFAULT_CAPACITY = 10;

    //最大分配容量大小
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    //ArrayList底层采用数组存放
    private transient Object[] elementData;

    //定义一个初始化数组大小
    private static final Object[] EMPTY_ELEMENTDATA = {};

    //默认为空的一个数组，用于无参的构造函数
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    private int size;


    /**
     * 根据参数决定初始化数组的大小
     * @param initialCapacity
     */
    private CustomArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("初始化容量不能小于0: "+
                    initialCapacity);
        }
    }

    /**
     * 无参构造函数，创建一个默认的空数组
     */
    public CustomArrayList() {
        elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }


    @Override
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = e;
        return true;
    }

    private void ensureCapacityInternal(int minCapacity) {

        //调用无参的构造函数，数组为空，需要初始化数组
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        ensureExplicitCapacity(minCapacity);
    }


    private void ensureExplicitCapacity(int minCapacity) {
        // minCapacity：为当前size + 1
        // 新元素添加，minCapacity大于当前数组容量，进行扩容
        if (minCapacity - elementData.length > 0) {
            //扩容操作
            grow(minCapacity);
        }
    }

    private void grow(int minCapacity) {

        //当前数组容量
        int oldCapacity = elementData.length;
        //位运算扩容1.5倍，如当前数组容量为10，则newCapacity为15
        int newCapacity = oldCapacity + (oldCapacity >> 1);

        //如果扩容后的数组容量小于minCapacity
        if (newCapacity - minCapacity < 0) {
            //将size + 1赋值为新数组容量
            newCapacity = minCapacity;
        }
        //如果扩容后的数组容量大于Integer.MAX_VALUE - 8
        if (newCapacity - MAX_ARRAY_SIZE > 0) {
            //数组容量大小
            newCapacity = hugeCapacity(minCapacity);
        }
        //底层采用System.arraycopy方法进行元素移动，参考https://blog.csdn.net/jiao1902676909/article/details/89363608
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        //这里没看懂，minCapacity 为size+1，按道理是不会出现小于0的情况，有朋友了解还请帮忙解答下
        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        }
        //对minCapacity和MAX_ARRAY_SIZE进行比较
        //若minCapacity大，将Integer.MAX_VALUE作为新数组的大小
        //若MAX_ARRAY_SIZE大，将MAX_ARRAY_SIZE作为新数组的大小
        //MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
        return (minCapacity > MAX_ARRAY_SIZE) ?Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }
    @Override
    public boolean add(int index, E e) {
        //检查下标范围
        rangeCheckForAdd(index);
        //需要扩容则扩容
        ensureCapacityInternal(size+1);
        //arraycopy的使用，参考https://blog.csdn.net/jiao1902676909/article/details/89363608
        System.arraycopy(elementData, index, elementData, index + 1, size - index);

        //根据下标赋值
        elementData[index] = e;
        size++;
        return true;
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    @Override
    public boolean remove(Object o) {
        //如果要删除的值时null，遍历数组匹配null值，使用arraycopy 进行数组移动，删除元素
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            //如果不为null值，遍历数组，匹配传入的值，使用arraycopy 进行数组移动，删除元素
            for (int index = 0; index < size; index++) {
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
            }
        }
        return false;
    }

    private void fastRemove(int index) {
        int numMoved = size - index - 1;
        //判断是否是最后一个元素，如果不是则使用arraycopy进行数组移动
        if (numMoved > 0) {
            System.arraycopy(elementData, index+1, elementData, index, numMoved);
        }
        //将最后一个值赋为null，垃圾回收
        elementData[--size] = null;
    }

    @Override
    public E remove(int index) {
        //检查下标
        rangeCheck(index);

        E e = (E)elementData[index];

        fastRemove(index);
        return e;
    }

    @Override
    public E get(int index) {
        //检查下标范围
        rangeCheck(index);

        return (E)elementData[index];
    }

    /**
     * 检查下标，下标范围小于(size-1)
     * @param index 下标
     */
    private void rangeCheck(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
        }
    }

    @Override
    public int size() {
        return size;
    }
}

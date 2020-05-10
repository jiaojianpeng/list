package com.px.list;

import java.util.LinkedList;

/**
 * LinkedList主要是由链表构成
 *
 * 查询慢，需要遍历链表，匹配元素
 * 删除添加快，只需要将节点指向修改即可，不需要像ArrayList一样移动数组，添加时也无须扩容。
 * @param <E>
 */
public class CustomLinkedList<E> implements CustomList<E> {

    transient int size = 0;

    //第一个节点(头节点)
    transient Node<E> first;

    //最后一个节点(尾结点)
    transient Node<E> last;

    public CustomLinkedList() {

    }



    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    private void linkLast(E e) {
        //获取到当前链表最后一个值
        Node<E> lastNode = this.last;
        //e为当前值、l为当前值得上一节点，下一节点为null
        Node<E> newNode = new Node<>(lastNode, e, null);
        //将当前Node节点置尾结点
        this.last = newNode;
        //如果最后一个节点为null，说明是第一个节点，此时首尾节点值相同
        if (lastNode == null) {
            //将该节点置为头节点
            first = newNode;
        } else {
            //如果不为null，将尾结点的下一节点指向新值
            lastNode.next = newNode;
        }
        size++;
    }



    @Override
    public boolean add(int index, E e) {
        //检查下标是否在 0 - size 之间
        checkPositionIndex(index);

        //如果传入的下标是最后一个元素，无须别的操作，直接追加元素
        if (index == size) {
            linkLast(e);
        } else {
            linkBefore(e, node(index));
        }
        return true;
    }

    private Node<E> node(int index) {

        // (size >> 1) == size / 2。 如果传入的index小于当前size的一半，就从头开始遍历
        if (index < (size >> 1)) {
            Node<E> x = first;
            //从头节点循环遍历，直到获取到传入的index的节点
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            //如果大于当前size的一半，就从尾开始遍历
            Node<E> x = last;
            //从尾节点循环遍历，直到获取到传入的index的节点
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    private void linkBefore(E e, Node<E> succ) {
        //获取传入index值的上一节点
        Node<E> pred = succ.prev;
        //将上一节点置为传入值的上一节点，将旧的index对应的值置为传入值的下一节点。
        //接下来介绍的比较绕。 参考 ：https://blog.csdn.net/jiao1902676909/article/details/89408882
        Node<E> newNode = new Node<>(pred, e, succ);

        //将当前值置为下一节点的上一节点。
        succ.prev = newNode;
        //如果当前值的上一节点为null，说明链表为空或者为头结点
        if (pred == null) {
            //将当前值置为头结点
            first = newNode;
        } else {
            //如果当前值的上一节点不为null，将上一节点的下一节点置为当前值
            pred.next = newNode;
        }
        size++;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 && index > size) {
            throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
        }
    }
    private void checkElementIndex(int index) {
        if (index < 0 && index > size) {
            throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
        }
    }
    @Override
    public boolean remove(Object object) {
        if (object == null) {
            //要删除的元素为null，遍历链表，匹配null值
            for (Node<E> x = first; x != null; x = x.next ) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            //如果要删除的元素不为null，遍历链表，匹配要删除的值
            for (Node<E> x = first; x != null; x = x.next ) {
                if (object.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    E unlink(Node<E> x) {
        //当前值的上一节点
        Node<E> prev = x.prev;
        //当前值的下一节点
        Node<E> next = x.next;
        E item = x.item;

        //如果要删除元素的上一节点为null，说明要删除的元素是链表的头结点
        if (prev == null) {
            //将要删除元素的下一节点置为头结点
            first = next;
        } else {
            //将要删除元素的上一节点的下一节点指向要删除元素的下一节点
            // 示例  要删除元素B：设置前：A -> B -> C 设置后  A -> C    是双向链表，这里只是next的一个示例
            prev.next = next;
            //将要删除的元素的上一节点置为null
            x.prev = null;
        }

        //如果要删除元素的下一节点为null，说明要删除的元素是尾结点
        if (next == null) {
            //将要删除元素的上一节点置为尾结点
            last = next;
        } else {
            //将要删除元素的下一节点的上一节点指向要删除元素的上一节点
            //示例：设置前：A <- B <- C   设置后： A <- C
            next.prev = prev;
            //将要删除元素的下一节点置为null
            x.next = null;
        }

        x.item = null;

        size --;

        return item;
    }

    @Override
    public E remove(int index) {
        //检查下标范围
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public E get(int index) {
        //检查下标范围
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public int size() {
        return size;
    }

    //底层实现，是由Node组成链表
    private static class Node<E> {
        //
        E item;
        //下一个节点值
        Node<E> next;
        //上一个节点值
        Node<E> prev;

        Node(Node<E> prev, E item, Node<E> next) {
            //上一节点
            this.prev = prev;
            //当前值
            this.item = item;
            //下一节点
            this.next = next;
        }
    }
}

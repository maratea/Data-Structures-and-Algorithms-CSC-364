package com.company;
import java.util.NoSuchElementException;
import java.util.ListIterator;
/*
 * ***********************************************************************************
 * ***********************************************************************************
 * Due to conflicting file names on my computer, I renamed MyList.java to MyList1.java
 * ***********************************************************************************
 * ***********************************************************************************
 */

public class MyDoublyLinkedList<E> extends MyAbstractSequentialList<E> implements Cloneable {
    Node<E> head = new Node<E>(null);

    public MyDoublyLinkedList() {
        head.next = head;
        head.previous = head;
    }

    @Override
    public E getFirst() {
        return head.next.element;
    }

    @Override
    public E getLast() {
        return head.previous.element;
    }

    @Override
    public void addFirst(E e) {
        Node<E> node = new Node<E>(e);
        node.previous = head;
        node.next = head.next;
        head.next = node;
        if (size == 0) {
            head.previous = node;
        } size++;
    }

    @Override
    public void addLast(E e) {
        Node<E> node = new Node<E>(e);
        node.next = head;
        node.previous = head.previous;
        head.previous.next = node;
        head.previous = node;
        size++;
    }

    @Override
    public E removeFirst() throws NoSuchElementException {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Node<E> node = head.next;
        node.next.previous = node.previous;
        node.previous.next = node.next;
        node.next = null;
        node.previous = null;
        size--;
        return node.element;
    }

    @Override
    public E removeLast() throws NoSuchElementException {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Node<E> node = head.previous;
        head.previous = node.previous;
        node.previous.next = head;
        node.next = null;
        node.previous = null;
        size--;
        return node.element;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index > size) {
            throw new IndexOutOfBoundsException(index + " > " + size);
        } return new DoublyLinkedListIterator(index);
    }

    @Override
    public void add(int index, E e) {
        if (index > size) {
            throw new IndexOutOfBoundsException(index + " > " + size);
        } if (size == 0 || index == 0) {
            addFirst(e);
            return;
        } if(index == size) {
            addLast(e);
            return;
        }
        Node<E> node1 = head.next;

        for (int i = 0; i < index - 1; i++) {
            node1 = node1.next;
        }

        Node<E> node = new Node<E>(e);
        node.previous = node1;
        node.next = node1.next;
        node.next.previous = node;
        node1.next = node;
        size++;
    }

    @Override
    public void add(E e) {
        add(size, e);
    }

    @Override
    public void clear() {
        size = 0;
        head.next = head;
        head.previous = head;
    }

    @Override
    public boolean contains(E e) {
        return indexOf(e) != -1;
    }

    @Override
    public E get(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException(index + " > " + size);
        }
        Node<E> node = head.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        } return node.element;
    }

    @Override
    public int indexOf(E e) {
        Node<E> node = head.next;

        if (e == null) {
            for (int i = 0; i < size; i++) {
                if (node.element == null) {
                    return i;
                } else {
                    node = node.next;
                }
            }
        } else
            for (int j = 0; j < size; j++)
                if (e.equals(node.element))
                    return j;
                else
                    node = node.next;

        return -1;
    }

    @Override
    public int lastIndexOf(E e) {
        Node<E> node = head.previous;

        if (e == null)
            for (int i = size; i > 0; i--)
                if (node.element == null)
                    return i - 1;
                else
                    node = node.previous;
        else
            for (int i = size; i > 0; i--)
                if (e.equals(node.element))
                    return i - 1;
                else
                    node = node.previous;

        return -1;
    }

    @Override
    public E remove(int index) {
        Node<E> node = head.next;
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return removeFirst();
        }
        else if (index == size - 1) {
            return removeLast();
        }

        for (int i = 0; i < index; i++) {
            node = node.next;
        }

        node.next.previous = node.previous;
        node.previous.next = node.next;
        node.next = null;
        node.previous = null;

        size--;
        return node.element;
    }

    @Override
    public E set(int index, E e) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> node = head.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        node.element = e;
        return node.element;
    }

    @Override
    public Object clone() {
        MyDoublyLinkedList<E> newlist = null;
        ListIterator<E> iterator = listIterator();

        try {
            newlist = (MyDoublyLinkedList<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }

        newlist.head = new Node<E>(null);
        newlist.size = 0;

        while (iterator.hasNext()) {
            newlist.add(iterator.next());
        }

        return (Object)newlist;

    }

    @Override
    public boolean equals(Object obj) {
        MyDoublyLinkedList<E> other = (MyDoublyLinkedList<E>) obj;

        if (this == other) {
            return true;
        }
        else if (!(other instanceof MyList1)) {
            return false;
        }
        if (other.size != size) {
            return false;
        } else {
            ListIterator<E> parent = listIterator(), child = other.listIterator();
            while (parent.hasNext() && child.hasNext()) {
                if (parent.next() == null) {
                    parent.previous();
                    if (!(child.next() == parent.next())) {
                        return false;
                    }
                } else {
                    parent.previous();
                    if (!(parent.next().equals(child.next()))) {
                        return false;
                    }

                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("[");
        listIterator().forEachRemaining(e -> {
            builder.append(e);
            builder.append(", ");
        });
        builder.setLength(builder.length() - 2);
        builder.append("]");

        return builder.toString();
    }

    private static class Node<E> {
        E element;
        Node<E> next, previous;

        Node(E e) {
            element = e;
        }
    }

    private class DoublyLinkedListIterator implements ListIterator<E> {
        private Node<E> current = head.next, lastNode = null;
        private int index;

        public DoublyLinkedListIterator(int index) {
            if (index > size) {
                throw new IndexOutOfBoundsException();
            }
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastNode = current;
            current = current.next;
            index++;
            return lastNode.element;
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            index--;
            return (current = lastNode = current.previous).element;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            if (lastNode == null) {
                throw new IllegalStateException();
            }

            lastNode.next.previous = lastNode.previous;
            lastNode.previous.next = current;
            lastNode.next = null;
            lastNode.previous = null;

            size--;

            if(size == 0) {
                index--;
            }

            lastNode = null;
        }

        @Override
        public void set(E e) {
            if (lastNode == null) {
                throw new IllegalStateException();
            }
            lastNode.element = e;
        }

    }
}

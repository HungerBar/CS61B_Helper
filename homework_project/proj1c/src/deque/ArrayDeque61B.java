package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
    int head;
    int tail;
    T[] data;
    int  ARRAYSIZE = 10;
    int size;

    public ArrayDeque61B() {
        data = (T[]) new Object[ARRAYSIZE];
        head = 0;
        tail = ARRAYSIZE - 1;
        size = 0;
    }

    //入队首，head前移动
    //支持resizing
    @Override
    public void addFirst(T x) {
        if(size == 0) {
            ARRAYSIZE = 10;
            data[ARRAYSIZE - 1] = x;
            head = ARRAYSIZE - 1;
            size++;
            return;
        }
        if (size() + 1 > ARRAYSIZE) {
            data = resize(ARRAYSIZE * 2);
            tail = ARRAYSIZE - 1;
            ARRAYSIZE = ARRAYSIZE*2;
            data[ARRAYSIZE - 1] = x;
            head = ARRAYSIZE - 1;
            size++;
            return;
        }
        data[Math.floorMod(head - 1,ARRAYSIZE)] = x;
        head = Math.floorMod(head - 1,ARRAYSIZE);
        size++;

    }

    private T[] resize(int newSize) {
        T[] newData = (T[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newData[i] = data[Math.floorMod(head + i,ARRAYSIZE)];
        }
        return newData;
    }

    @Override
    public void addLast(T x) {
        if(size == 0) {
            ARRAYSIZE = 10;
            data[0] = x;
            tail = 0;
            size++;
            return;
        }
        if (size() + 1 > ARRAYSIZE) {
            data = resize(ARRAYSIZE * 2);
            head = 0;
            tail = ARRAYSIZE - 1;
            data[ARRAYSIZE] = x;
            ARRAYSIZE = ARRAYSIZE*2;
            size++;
            return;
        }
        data[Math.floorMod(tail + 1,ARRAYSIZE)] = x;
        tail = Math.floorMod(tail + 1,ARRAYSIZE);
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        if(size == 0) {
            return null;
        }
        int p = head;
        int size1 = size();
        while (size1 > 0) {
            result.add(data[p]);
            p = (p + 1)  % ARRAYSIZE;
            size1--;
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if(size == 0) {
            return null;
        }
        T result = data[head];
        head = (head + 1) % ARRAYSIZE;
        size--;
        if(size < ARRAYSIZE/4 ) {
            data =resize(ARRAYSIZE/2);
            tail = size - 1;
            head = 0;
        }
        return result;
    }

    @Override
    public T removeLast() {
        if(size == 0) {
            return null;
        }
        T result = data[tail];
        tail = Math.floorMod(tail - 1,ARRAYSIZE);
        size--;
        if(size < ARRAYSIZE/4 ) {
            data =resize(ARRAYSIZE/2);
            tail = size - 1;
            head = 0;
        }

        return result;
    }

    @Override
    public T get(int index) {
        return (T) data[(head + index)  % ARRAYSIZE];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDeque61BIterator();
    }

    private class ArrayDeque61BIterator implements Iterator<T> {
        private int index;
        public ArrayDeque61BIterator() {
            index = 0;
        }
        public boolean hasNext() {
            return index < size();
        }
        public T next() {
            T result = get(index);
            index++;
            return result;
        }
    }

    @Override
    public boolean equals(Object x) {
        if(x instanceof ArrayDeque61B<?> arrayDeque61B) {
            if(size() == arrayDeque61B.size()) {
                    for(int i = 0; i < arrayDeque61B.size(); i++) {
                        if(!this.get(i).equals(arrayDeque61B.get(i))) {
                            return false;
                        }
                }return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public String toString(){
        return toList().toString();
    }
}

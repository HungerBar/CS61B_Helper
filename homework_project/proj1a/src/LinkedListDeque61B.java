import java.util.List;
import java.util.ArrayList; // import the ArrayList class

public class LinkedListDeque61B<T> implements  Deque61B<T> {
    Node sentinel;


    private class Node {
        T value;
        Node next;
        Node prev;
        public Node(T v) {
            value = v;
            this.next = null;
            this.prev = null;
        }

        public Node(){
            this.next = null;
            this.prev = null;
            value = null;
        }
    }

    public LinkedListDeque61B() {
        sentinel = new Node();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    @Override
    public void addFirst(T x) {
        Node newNode = new Node(x);
        newNode.next = sentinel.next;
        newNode.prev = sentinel;
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
    }

    @Override
    public void addLast(T x) {
        Node newNode = new Node(x);
        newNode.prev = sentinel.prev;
        newNode.next = sentinel;
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
    }

    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        Node current = sentinel.next;
        while (current.value  != null) {
            result.add(current.value);
            current = current.next;
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    @Override
    public int size() {
        int size = 0;
        if(isEmpty()){
            return size;
        }

        Node current = sentinel.next;
        while (current.value != null) {
            size++;
            current = current.next;
        }
        return size;
    }

    @Override
    public T removeFirst() {
        if(isEmpty()){
            return null;
        }
        Node current = sentinel.next;
        sentinel.next = sentinel.next.next;
        return current.value;
    }

    @Override
    public T removeLast() {
        Node current = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        return current.value;
    }

    @Override
    public T get(int index) {
        if(isEmpty()){
            return null;
        }
        if(index < 0 || index >= size()){
            return null;
        }
        Node current = sentinel;
        for (int i = index; i >= 0; i--) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public T getRecursive(int index) {
        if(isEmpty()){
            return null;
        }
        if(index < 0 || index >= size()){
            return null;
        }
        return getRecursivehelper(index , sentinel.next);
    }

    private T getRecursivehelper(int index , Node n){
        if(index == 0){
            return n.value;
        }else{
            return getRecursivehelper(index - 1, n.next);
        }
    }
}

package deque;

import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    public Comparator<T> comparator;
    public MaxArrayDeque61B(Comparator<T> c){
        super();
        comparator = c;
    }

    public T max(){
        if(isEmpty()) {
            return null;
        }
        T max1 = this.get(0);
        for(int i = 1; i < this.size(); i++){
            if(comparator.compare(this.get(i), max1) > 0){
                max1 = this.get(i);
            }
        }
        return max1;
    }

    public T max(Comparator<T> c){
        if(isEmpty()) {
            return null;
        }
        T max1 = this.get(0);
        for(int i = 1; i < this.size(); i++){
            if(c.compare(this.get(i), max1) > 0){
                max1 = this.get(i);
            }
        }
        return max1;
    }
}

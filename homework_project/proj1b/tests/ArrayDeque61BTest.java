import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }

     @Test
    void sizeTest1(){
         ArrayDeque61B<Integer > arrayDeque61B = new ArrayDeque61B<>();
         arrayDeque61B.addFirst(1);
         int i = arrayDeque61B.size();
         assertThat(arrayDeque61B.size()).isEqualTo(1);

     }

     @Test
     void sizeTest2(){
         ArrayDeque61B<Integer> arrayDeque61B = new ArrayDeque61B<>();
         arrayDeque61B.addLast(1);
         int i = arrayDeque61B.size();
         assertThat(arrayDeque61B.size()).isEqualTo(1);
     }

     @Test
     void sizeTest3(){
         ArrayDeque61B<Integer> arrayDeque61B = new ArrayDeque61B<>();
         assertThat(arrayDeque61B.size()).isEqualTo(0);
     }

     @Test
    void toListTest1(){
         ArrayDeque61B<Integer> arrayDeque61B = new ArrayDeque61B<>();
         arrayDeque61B.addFirst(1);
         arrayDeque61B.addLast(2);
         List<Integer> list;
         list = arrayDeque61B.toList();
         assertThat(list).contains(1);
         assertThat(list).contains(2);
     }

     @Test
    void addFirstTest1(){
         ArrayDeque61B<Integer> arrayDeque61B = new ArrayDeque61B<>();
         arrayDeque61B.addFirst(1);
         arrayDeque61B.addFirst(2);
         arrayDeque61B.addFirst(3);
         arrayDeque61B.addFirst(4);
         arrayDeque61B.addFirst(5);
         arrayDeque61B.addFirst(6);
         arrayDeque61B.addFirst(7);
         arrayDeque61B.addFirst(8);
         arrayDeque61B.addFirst(9);
         arrayDeque61B.addFirst(10);
         int i = arrayDeque61B.size();
         arrayDeque61B.addFirst(11);
         List<Integer> list = arrayDeque61B.toList();

     }


     @Test
     void removeFirstTest1(){
         ArrayDeque61B<Integer> arrayDeque61B = new ArrayDeque61B<>();
         arrayDeque61B.addFirst(1);
         arrayDeque61B.addFirst(2);
         arrayDeque61B.addFirst(3);
         arrayDeque61B.addFirst(4);
         arrayDeque61B.addFirst(5);
         arrayDeque61B.addFirst(6);
         arrayDeque61B.addFirst(7);
         arrayDeque61B.addFirst(8);
         arrayDeque61B.addFirst(9);
         arrayDeque61B.addFirst(10);
         int i = arrayDeque61B.size();
         arrayDeque61B.addFirst(11);
         i = arrayDeque61B.removeFirst();
         i = arrayDeque61B.removeFirst();
         i = arrayDeque61B.removeFirst();
         i = arrayDeque61B.removeFirst();
         i = arrayDeque61B.removeFirst();
         i = arrayDeque61B.removeFirst();
         i = arrayDeque61B.removeFirst();
         i = arrayDeque61B.removeFirst();
         i = arrayDeque61B.removeFirst();
         i = arrayDeque61B.removeFirst();
         i = arrayDeque61B.removeFirst();

         List<Integer> list = arrayDeque61B.toList();

     }
     @Test
    void removeLastTest1(){
        ArrayDeque61B<Integer> arrayDeque61B = new ArrayDeque61B<>();
        arrayDeque61B.addFirst(1);
        arrayDeque61B.addFirst(2);
        arrayDeque61B.addFirst(3);
        arrayDeque61B.addFirst(4);
        arrayDeque61B.addFirst(5);
        arrayDeque61B.addFirst(6);
        arrayDeque61B.addFirst(7);
        arrayDeque61B.addFirst(8);
        arrayDeque61B.addFirst(9);
        arrayDeque61B.addFirst(10);
        int i = arrayDeque61B.size();
        arrayDeque61B.addFirst(11);
        i = arrayDeque61B.removeLast();
        i = arrayDeque61B.removeLast();
        i = arrayDeque61B.removeLast();
        i = arrayDeque61B.removeLast();
        i = arrayDeque61B.removeLast();
        i = arrayDeque61B.removeLast();
        i = arrayDeque61B.removeLast();
        i = arrayDeque61B.removeLast();
        i = arrayDeque61B.removeLast();
        i = arrayDeque61B.removeLast();
        i = arrayDeque61B.removeLast();
        arrayDeque61B.addFirst(6);


        List<Integer> list = arrayDeque61B.toList();

    }
}



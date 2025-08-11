import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.*;

import java.util.Comparator;
import deque.MaxArrayDeque61B;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

        @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }
//    @Test
//    public void testEqualDeques61B() {
//        Deque61B<String> lld1 = new ArrayDeque61B<>() ;
//        Deque61B<String> lld2 = new ArrayDeque61B<>() ;
//
//        lld1.addLast("front");
//        lld1.addLast("middle");
//        lld1.addLast("back");
//
//        lld2.addLast("front");
//        lld2.addLast("middle");
//        lld2.addLast("back");
//
//        assertThat(lld1).isEqualTo(lld2);
//    }
//
//    @Test
//    public void iteratorTest1() {
//        Deque61B<String> lld1 = new ArrayDeque61B<>();
//
//        lld1.addLast("front");
//        lld1.addLast("middle");
//        lld1.addLast("back");
//        for (String s : lld1) {
//            System.out.println(s);
//        }
//        System.out.println(lld1.toString());
//    }
//
//    @Test
//    public void iteratorTest2() {
//        Deque61B<String> lld1 = new LinkedListDeque61B<>();
//
//        lld1.addLast("front");
//        lld1.addLast("middle");
//        lld1.addLast("back");
//        for (String s : lld1) {
//            System.out.println(s);
//        }
//        System.out.println(lld1.toString());
//    }

}
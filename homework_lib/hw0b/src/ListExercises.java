import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        int sum = 0;
        for (int x : L){
            sum += x;
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> newlist = new ArrayList<>();
        for (int x : L){
            if (x % 2 == 0){
                newlist.add(x);
            }
        }
        if (newlist.size()  == 0){
            return null;
        }
        return  newlist;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> newlist = new ArrayList<>();
        for (int x : L1) {
            if (L2.contains(x)) {
                newlist.add(x);
            }
        }
        return newlist;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int count = 0;
        for (String word : words){
            for(char c1 : word.toCharArray()){
                if (c == c1){
                    count++;
                }
            }
        }
        return count;
    }
}

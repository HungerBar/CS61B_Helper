import java.util.ArrayList;
import java.util.List;

public class Dessert {
    public int flavor,price;
    private static int numDesserts = 0;

    public Dessert(int f ,int p) {
        flavor = f;
        price = p;
        numDesserts +=1;
    }
    //打印创造的Dessert
    public void printDessert(){
        System.out.println(flavor+" "+price+" "+numDesserts);
    }

    public static void main(String[] args) {
        System.out.println("I love dessert!");
    }
}

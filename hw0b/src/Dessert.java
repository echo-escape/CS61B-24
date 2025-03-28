public class Dessert {
    static int numDessert = 0;
    int price ;
    int flavor;

    public Dessert(int flavor, int price) {
        this.price = price;
        this.flavor = flavor;
        numDessert++;
    }

    public void printDessert() {
        System.out.println(this.flavor + " " + this.price + " " + numDessert);
    }

    public static void main(String[] args) {
        System.out.println("I love dessert!");
    }
}

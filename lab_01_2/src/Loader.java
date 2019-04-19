public class Loader {
    public static void main(String[] args) {

        Triple<String, Integer, String> someData = new Triple<>("Test", 5, "done");

        System.out.println(someData.getFirst());
        System.out.println(someData.getSecond());

        System.out.println(someData.toString());
    }
}

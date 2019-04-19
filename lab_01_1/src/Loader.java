import java.util.ArrayList;

public class Loader {
    public static void main(String[] args) {
        ArrayList<Figure> figures = new ArrayList<>();

        figures.add(new Rectangle(2, 5));
        figures.add(new Square(5));
        figures.add(new Circle(3));
        figures.add(new Triangle(3, 4, 5));
        figures.add(new Rectangle(4.0, 10.0));
        figures.add(new Square(10.0));
        figures.add(new Circle(6.0));
        figures.add(new Triangle(6.0, 8.0, 10.0));

        for (Figure figure : figures) {
            System.out.println("Площадь фигуры равна = " + figure.area());
        }
    }
}

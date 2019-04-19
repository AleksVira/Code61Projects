public class Square extends Figure {
    private double side;

    public Square(double side) {
        this.side = side;
    }
    public Square(int side) {
        this.side = side;
    }

    @Override
    double perimeter() {
        return 4 * side;
    }

    @Override
    double area() {
        return side * side;
    }
}

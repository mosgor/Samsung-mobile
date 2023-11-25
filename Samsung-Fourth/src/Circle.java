public class Circle extends Figure{
    double radius = 0;

    public Circle(int radius) {
        this.radius = radius;
        this.square = Math.PI * radius * radius;
    }

    @Override
    protected double getSquare() {
        return square;
    }

    @Override
    protected void scale(int coef) {
        radius *= coef;
        square = Math.PI * radius * radius;
    }
}

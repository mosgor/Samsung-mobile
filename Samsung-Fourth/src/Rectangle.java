public class Rectangle extends Figure{
    double a = 0, b = 0;

    public Rectangle(double a, double b) {
        this.a = a;
        this.b = b;
        this.square = a * b;
    }

    @Override
    protected double getSquare() {
        return square;
    }

    @Override
    protected void scale(int coef) {
        a *= coef;
        b *= coef;
        square = a * b;
    }
}

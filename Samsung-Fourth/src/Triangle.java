public class Triangle extends Figure{
    double a = 0, b = 0, c = 0;

    public Triangle(double a, double b, double c) {
        assert (a + b > c && a + c > b && b + c > a);
        this.a = a;
        this.b = b;
        this.c = c;
        double p = (a +b + c) / 2;
        this.square = Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    @Override
    public double getSquare() {
        return square;
    }

    @Override
    public void scale(int coef) {
        a *= coef;
        b *= coef;
        c *= coef;
        double p = (a +b + c) / 2;
        square = Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

}

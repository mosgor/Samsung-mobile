import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Figure[] figures = new Figure[]{new Triangle(5, 6, 7), new Rectangle(25, 50), new Circle(5)};
        Arrays.sort(figures);
        System.out.println(Arrays.toString(figures));
        figures[1].scale(30);
        Arrays.sort(figures);
        System.out.println(Arrays.toString(figures));
    }
}
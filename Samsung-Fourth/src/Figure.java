public abstract class Figure implements Comparable<Figure>{
    protected double square;
    protected abstract double getSquare();
    protected abstract void scale(int coef);

    @Override
    public String toString() {
        return this.getClass().toString() + "{" +
                "square=" + square +
                '}';
    }

    @Override
    public int compareTo(Figure o) {
        if (this.square > o.square){
            return 1;
        }
        else if (this.square < o.square){
            return -1;
        }
        return 0;
    }
}

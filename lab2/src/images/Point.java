package images;

public class Point {
    private double a;
    private double b;
    private double c;
    private double d;

    public Point(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Point(Point point) {
        this.a = point.getA();
        this.b = point.getB();
        this.c = point.getC();
        this.d = point.getD();
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    public static double distance(Point p, Point center) {
        return Math.sqrt(Math.pow(Math.abs(p.a - center.a), 2) +
                Math.pow(Math.abs(p.b - center.b), 2) +
                Math.pow(Math.abs(p.c - center.c), 2) +
                Math.pow(Math.abs(p.d - center.d), 2));
    }
}

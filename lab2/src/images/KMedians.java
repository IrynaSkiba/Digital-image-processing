package images;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KMedians {
    private int k; //количество кластеров
    private int n; //количество объектов

    private ArrayList<Point> points;
    private ArrayList<Point> centers;
    private HashMap<Point, Integer> clusters;

    public KMedians(int k, ArrayList<Point> points) {
        this.k = k;
        this.points = points;
        n = points.size();
        clusters = new HashMap<>(k);
        centers = new ArrayList<>();
        initCenter();
    }

    private void initCenter() {
        int a = 0; // Начальное значение диапазона
        int b = n; // Конечное значение диапазона
        int i = k;
        while (i != 0) {
            int random = a + (int) (Math.random() * b); // Генерация 1-го числа
            if (centers.contains(points.get(random))) {
                continue;
            }
            Point point = new Point(points.get(random));
            centers.add(point);
            i--;
        }
    }

    public void clusterAnalysis() {
        while (true) {
            showClusters();
            pointToCluster();
            if (!changeCenter()) return; //выходим из цикла, если центры уже не меняются
        }
    }

    private void pointToCluster() {
        HashMap<Integer, Double> distance = new HashMap<>();

        for (int i = 0; i < n; i++) {
            //в этом цикле мы получим map с расстоянием до каждого кластера,
            // которые хранятся как <номер кластера, расстояние до него>
            for (int j = 0; j < k; j++) {
                distance.put(j, Point.distance(points.get(i), centers.get(j)));
            }
            Map.Entry<Integer, Double> minEntry = null;
            for (Map.Entry<Integer, Double> entry : distance.entrySet()) {
                if (minEntry == null || entry.getValue() < minEntry.getValue()) {
                    minEntry = entry;
                }
            }
            Integer minK = minEntry.getKey(); //здесь номер кластера до которого минимальное расстояние
            clusters.put(points.get(i), minK);
            distance.clear(); //обнуляем список расстояний, чтобы использозвать его для новой точки
        }
    }

    //вернет true, если центры изменялись
    private boolean changeCenter() {
        //здесь изменяются значения центров
        ArrayList<Double> measurementA = new ArrayList<>();
        ArrayList<Double> measurementB = new ArrayList<>();
        ArrayList<Double> measurementC = new ArrayList<>();
        ArrayList<Double> measurementD = new ArrayList<>();

        ArrayList<Point> oldCenter = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            oldCenter.add(new Point(centers.get(i).getA(), centers.get(i).getB(), centers.get(i).getC(), centers.get(i).getD()));
        }

        centers.clear();

        for (int i = 0; i < k; i++) {
            for (Map.Entry<Point, Integer> entry : clusters.entrySet()) {
                if (entry.getValue() == i) {
                    measurementA.add(entry.getKey().getA());
                    measurementB.add(entry.getKey().getB());
                    measurementC.add(entry.getKey().getC());
                    measurementD.add(entry.getKey().getD());
                }
            }

            Collections.sort(measurementA);
            Collections.sort(measurementB);
            Collections.sort(measurementC);
            Collections.sort(measurementD);

            double a, b, c, d;
            if (measurementA.size() == 0) {
                a = oldCenter.get(i).getA();
            } else {
                a = measurementA.get(measurementA.size() / 2);
            }
            if (measurementB.size() == 0) {
                b = oldCenter.get(i).getA();
            } else {
                b = measurementB.get(measurementB.size() / 2);
            }
            if (measurementC.size() == 0) {
                c = oldCenter.get(i).getA();
            } else {
                c = measurementC.get(measurementC.size() / 2);
            }
            if (measurementD.size() == 0) {
                d = oldCenter.get(i).getA();
            } else {
                d = measurementD.get(measurementD.size() / 2);
            }

            Point point = new Point(a, b, c, d);
            centers.add(point);

            measurementA.clear();
            measurementB.clear();
            measurementC.clear();
            measurementD.clear();
        }

        return !areEqualArrays(oldCenter, centers);
    }

    //вернет true, если два массива равны
    private boolean areEqualArrays(ArrayList<Point> old, ArrayList<Point> cur) {
        double a, b;
        for (int i = 0; i < old.size(); i++) {
            a = Math.round(old.get(i).getA() * 100) / 100;
            b = Math.round(cur.get(i).getA() * 100) / 100;
            if (a != b) return false;
            a = Math.round(old.get(i).getB() * 100) / 100;
            b = Math.round(cur.get(i).getB() * 100) / 100;
            if (a != b) return false;
            a = Math.round(old.get(i).getC() * 100) / 100;
            b = Math.round(cur.get(i).getC() * 100) / 100;
            if (a != b) return false;
            a = Math.round(old.get(i).getD() * 100) / 100;
            b = Math.round(cur.get(i).getD() * 100) / 100;
            if (a != b) return false;
        }
        return true;
    }

    public void showClusters() {
        int[] frequency = new int[k];
        for (int i = 0; i < k; i++) {
            for (Map.Entry<Point, Integer> entry : clusters.entrySet()) {
                if (entry.getValue() == i) {
                    frequency[i]++;
                }
            }
        }

        for (int i = 0; i < k; i++) {
            System.out.println("В кластере " + i + ": " + frequency[i]);
        }
        System.out.println("\n");
    }
}


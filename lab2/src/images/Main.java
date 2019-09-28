package images;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws IOException {
//        Scanner inp = new Scanner(System.in);
//        System.out.print("Input threshold : ");
//        int threshold = inp.nextInt();

        Scanner in = new Scanner(System.in);
        System.out.print("Input clusters quantity : ");
        int k = in.nextInt();

        MyImage image = new MyImage();
        image.readImage("1.jpg");
        image.toGrey();
        image.toBinary(180);
        image.writeImage("binary.jpg");

        image.medianFilter();
        image.erosion();
        image.erosion();

        image.medianFilter();
        image.medianFilter();

        image.dilation();
        image.dilation();
        image.medianFilter();
        image.writeImage("result.jpg");

        //фон заполняем 0, а объекты 1
        image.toSpecialBinary();

        MyImage map = new MyImage();
        map.readImage("2.jpg");

        image.labeling(image.getSourceImage(), map.getSourceImage());
        map.toColor();
        map.writeImage("color.jpg");

        HashMap<Integer, Integer> areas = map.getAreas();
        HashMap<Integer, Integer> perimeters = map.getPerimeters();
        HashMap<Integer, Double> compactness = map.getCompactness(perimeters, areas);
        HashMap<Integer, Double> elongation = map.getElongation();

        ArrayList<Point> points = new ArrayList<>(areas.size());

        for (Integer key : areas.keySet()) {
            Point p = new Point(areas.get(key), perimeters.get(key), compactness.get(key), elongation.get(key));
            points.add(p);
        }

        KMedians medians = new KMedians(k, points);
        medians.clusterAnalysis();

//        Window window = new Window();
//        window.addHistogram(new Histogram(image, "binary"));
//
//        EventQueue.invokeLater(() -> {
//            // window.display();
//        });
    }
}

package image;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws IOException {

        Window window = new Window();

        Scanner in = new Scanner(System.in);
        System.out.print("Input gMax: ");
        int gMax = in.nextInt();
        System.out.print("Input fMin: ");
        int fMin = in.nextInt();

        MyImage oldImage = new MyImage();
        oldImage.readImage("1.jpg");

        MyImage greyImage = new MyImage();
        greyImage.readImage("1.jpg");
        greyImage.toGrey();
        greyImage.writeImage("grey.jpg");

        MyImage lowAfterGreyImage = new image.MyImage();
        lowAfterGreyImage.readImage("1.jpg");
        lowAfterGreyImage.toGrey();
        lowAfterGreyImage.toLowBright(gMax);

        MyImage lowImage = new image.MyImage();
        lowImage.readImage("1.jpg");
        lowImage.toLowBright(gMax);

        MyImage heightAfterGreyImage = new image.MyImage();
        heightAfterGreyImage.readImage("1.jpg");
        heightAfterGreyImage.toGrey();
        heightAfterGreyImage.toHightBright(fMin);

        MyImage heightImage = new image.MyImage();
        heightImage.readImage("1.jpg");
        heightImage.toHightBright(fMin);

        MyImage filterImage = new image.MyImage();
        filterImage.readImage("1.jpg");
        filterImage.toGrey();
        filterImage.filterPrevitta(5);
        filterImage.writeImage("filterImage.jpg");

        MyImage filterImage2 = new image.MyImage();
        filterImage2.readImage("1.jpg");
        filterImage2.filterPrevitta(3); //меньше 3 выходит за границы

        MyImage median = new MyImage();
        median.readImage("1.jpg");
        median.toGrey();
        median.medianFilter();

        window.addImageAndHistogram(oldImage, new Histogram(oldImage, "Исходное"));
        window.addImageAndHistogram(greyImage, new Histogram(greyImage, "Оттенки серого"));
        window.addImageAndHistogram(median, new Histogram(median, "Медианный фильтр"));
        window.addImageAndHistogram(filterImage, new Histogram(filterImage, "Фильтр Превитта после оттенков серого"));
        window.addImageAndHistogram(filterImage2, new Histogram(filterImage2, "Фильтр Превитта на RGB"));
        window.addImageAndHistogram(lowAfterGreyImage, new Histogram(lowAfterGreyImage, "Понижение яркости после серого"));
        window.addImageAndHistogram(lowImage, new Histogram(lowImage, "Понижение яркости"));
        window.addImageAndHistogram(heightAfterGreyImage, new Histogram(heightAfterGreyImage, "Повышение яркости после серого"));
        window.addImageAndHistogram(heightImage, new Histogram(heightImage, "Повышение яркости"));

        EventQueue.invokeLater(() -> {
            window.display();
        });
    }
}
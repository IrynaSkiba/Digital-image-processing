package images;

import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        MyImage greyImage = new MyImage();
        greyImage.readImage("1.jpg");
        greyImage.toGrey();
        greyImage.toBinary(180);
        greyImage.writeImage("binary.jpg");

        greyImage.medianFilter();

        greyImage.erosion();
        greyImage.erosion();

        greyImage.medianFilter();
        greyImage.medianFilter();

        greyImage.dilation();
        greyImage.dilation();

        greyImage.medianFilter();

        greyImage.writeImage("result.jpg");



        MyImage old = new MyImage();
        old.readImage("1.jpg");

        Window window = new Window();
        window.addHistogram(new Histogram(old, "binary"));



           EventQueue.invokeLater(() -> {
            //window.display();
        });
    }
}

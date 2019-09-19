package images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MyImage {
    private int width;
    private int height;
    private BufferedImage sourceImage;
    final static int WHITE = 255;
    final static int BLACK = 0;

    public MyImage() {
    }

    public MyImage(int width, int height, BufferedImage sourceImage) {
        this.width = width;
        this.height = height;
        this.sourceImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.sourceImage.setData(sourceImage.getData());
    }

    public BufferedImage getSourceImage() {
        return sourceImage;
    }

    public void readImage(String name) throws IOException {
        File file = new File(name);
        this.sourceImage = ImageIO.read(file);
        this.height = sourceImage.getHeight();
        this.width = sourceImage.getWidth();
    }

    public void writeImage(String name) throws IOException {
        File output = new File(name);
        ImageIO.write(sourceImage, "jpg", output);
    }

    public void toBinary(int limit) {
        int blue, red, green;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {

                Color color = new Color(sourceImage.getRGB(x, y));

                if (color.getRed() <= limit) {
                    red = 0;
                } else red = 255;
               /* if (color.getGreen() <= limit) {
                    green = 0;
                } else green = 255;
                if (color.getBlue() <= limit) {
                    blue = 0;
                } else blue = 255;*/


                Color newColor = new Color(red, red, red);
                sourceImage.setRGB(x, y, newColor.getRGB());
            }
    }

    public void toGrey() {
        int blue, red, green, grey;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {

                Color color = new Color(sourceImage.getRGB(x, y));

                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();

                grey = (int) (red * 0.299 + green * 0.587 + blue * 0.114);

                Color newColor = new Color(grey, grey, grey);
                sourceImage.setRGB(x, y, newColor.getRGB());
            }
    }

    public void medianFilter() {
        int blue, red, green;
        ArrayList<Integer> arrayList = new ArrayList<>(9);
        MyImage temp = new MyImage(width, height, sourceImage);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    Color newColor = new Color(0, 0, 0);
                    sourceImage.setRGB(x, y, newColor.getRGB());
                    continue;
                }

                arrayList.add(((temp.getSourceImage().getRGB(x - 1, y - 1) & 0xff0000) >> 16));
                arrayList.add(((temp.getSourceImage().getRGB(x - 1, y) & 0xff0000) >> 16));
                arrayList.add(((temp.getSourceImage().getRGB(x - 1, y + 1) & 0xff0000) >> 16));
                arrayList.add(((temp.getSourceImage().getRGB(x, y - 1) & 0xff0000) >> 16));
                arrayList.add(((temp.getSourceImage().getRGB(x, y) & 0xff0000) >> 16));
                arrayList.add(((temp.getSourceImage().getRGB(x, y + 1) & 0xff0000) >> 16));
                arrayList.add(((temp.getSourceImage().getRGB(x + 1, y - 1) & 0xff0000) >> 16));
                arrayList.add(((temp.getSourceImage().getRGB(x + 1, y) & 0xff0000) >> 16));
                arrayList.add(((temp.getSourceImage().getRGB(x + 1, y + 1) & 0xff0000) >> 16));

                Collections.sort(arrayList);

                red = arrayList.get(4);

                arrayList.clear();

                arrayList.add(((temp.getSourceImage().getRGB(x - 1, y - 1) & 0xff00) >> 8));
                arrayList.add(((temp.getSourceImage().getRGB(x - 1, y) & 0xff00) >> 8));
                arrayList.add(((temp.getSourceImage().getRGB(x - 1, y + 1) & 0xff00) >> 8));
                arrayList.add(((temp.getSourceImage().getRGB(x, y - 1) & 0xff00) >> 8));
                arrayList.add(((temp.getSourceImage().getRGB(x, y) & 0xff00) >> 8));
                arrayList.add(((temp.getSourceImage().getRGB(x, y + 1) & 0xff00) >> 8));
                arrayList.add(((temp.getSourceImage().getRGB(x + 1, y - 1) & 0xff00) >> 8));
                arrayList.add(((temp.getSourceImage().getRGB(x + 1, y) & 0xff00) >> 8));
                arrayList.add(((temp.getSourceImage().getRGB(x + 1, y + 1) & 0xff00) >> 8));

                Collections.sort(arrayList);

                green = arrayList.get(4);
                arrayList.clear();

                arrayList.add(((temp.getSourceImage().getRGB(x - 1, y - 1) & 0xff)));
                arrayList.add(((temp.getSourceImage().getRGB(x - 1, y) & 0xff)));
                arrayList.add(((temp.getSourceImage().getRGB(x - 1, y + 1) & 0xff)));
                arrayList.add(((temp.getSourceImage().getRGB(x, y - 1) & 0xff)));
                arrayList.add(((temp.getSourceImage().getRGB(x, y) & 0xff)));
                arrayList.add(((temp.getSourceImage().getRGB(x, y + 1) & 0xff)));
                arrayList.add(((temp.getSourceImage().getRGB(x + 1, y - 1) & 0xff)));
                arrayList.add(((temp.getSourceImage().getRGB(x + 1, y) & 0xff)));
                arrayList.add(((temp.getSourceImage().getRGB(x + 1, y + 1) & 0xff)));

                Collections.sort(arrayList);

                blue = arrayList.get(4);

                arrayList.clear();

                Color newColor = new Color(red, green, blue);
                sourceImage.setRGB(x, y, newColor.getRGB());
            }
    }

    public void erosion() {
        int newBlue, newRed, newGreen;
        MyImage temp = new MyImage(width, height, sourceImage);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {

                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    newRed = BLACK;
                    newGreen = BLACK;
                    newBlue = BLACK;
                    Color newColor = new Color(newRed, newGreen, newBlue);
                    sourceImage.setRGB(x, y, newColor.getRGB());
                    continue;
                }

                if ((temp.getSourceImage().getRGB(x - 1, y - 1) & 0xff) == WHITE &&
                        (temp.getSourceImage().getRGB(x - 1, y) & 0xff) == WHITE &&
                        (temp.getSourceImage().getRGB(x - 1, y + 1) & 0xff) == WHITE &&
                        (temp.getSourceImage().getRGB(x, y - 1) & 0xff) == WHITE &&
                        (temp.getSourceImage().getRGB(x, y) & 0xff) == WHITE &&
                        (temp.getSourceImage().getRGB(x, y + 1) & 0xff) == WHITE &&
                        (temp.getSourceImage().getRGB(x + 1, y - 1) & 0xff) == WHITE &&
                        (temp.getSourceImage().getRGB(x + 1, y) & 0xff) == WHITE &&
                        (temp.getSourceImage().getRGB(x + 1, y + 1) & 0xff) == WHITE) {
                    newBlue = WHITE;
                } else newBlue = BLACK;

                //Color newColor = new Color(newRed, newGreen, newBlue);
                sourceImage.setRGB(x, y, new Color(newBlue, newBlue, newBlue).getRGB());
            }
    }

    public void dilation(){
        int newBlue, newRed, newGreen;
        MyImage temp = new MyImage(width, height, sourceImage);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {

                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    newRed = BLACK;
                    newGreen = BLACK;
                    newBlue = BLACK;
                    Color newColor = new Color(newRed, newGreen, newBlue);
                    sourceImage.setRGB(x, y, newColor.getRGB());
                    continue;
                }

                if ((temp.getSourceImage().getRGB(x - 1, y - 1) & 0xff) == WHITE ||
                        (temp.getSourceImage().getRGB(x - 1, y) & 0xff) == WHITE ||
                        (temp.getSourceImage().getRGB(x - 1, y + 1) & 0xff) == WHITE ||
                        (temp.getSourceImage().getRGB(x, y - 1) & 0xff) == WHITE ||
                        (temp.getSourceImage().getRGB(x, y) & 0xff) == WHITE ||
                        (temp.getSourceImage().getRGB(x, y + 1) & 0xff) == WHITE ||
                        (temp.getSourceImage().getRGB(x + 1, y - 1) & 0xff) == WHITE ||
                        (temp.getSourceImage().getRGB(x + 1, y) & 0xff) == WHITE ||
                        (temp.getSourceImage().getRGB(x + 1, y + 1) & 0xff) == WHITE) {
                    newBlue = WHITE;
                } else newBlue = BLACK;

                //Color newColor = new Color(newRed, newGreen, newBlue);
                sourceImage.setRGB(x, y, new Color(newBlue, newBlue, newBlue).getRGB());
            }
    }
//
//                    if ((temp.getSourceImage().getRGB(x - 1, y - 1) & 0xff) == WHITE ||
//            (temp.getSourceImage().getRGB(x - 1, y) & 0xff) == WHITE ||
//            (temp.getSourceImage().getRGB(x - 1, y + 1) & 0xff) == WHITE ||
//            (temp.getSourceImage().getRGB(x, y - 1) & 0xff) == WHITE ||
//            (temp.getSourceImage().getRGB(x, y) & 0xff) == WHITE ||
//            (temp.getSourceImage().getRGB(x, y + 1) & 0xff) == WHITE ||
//            (temp.getSourceImage().getRGB(x + 1, y - 1) & 0xff) == WHITE ||
//            (temp.getSourceImage().getRGB(x + 1, y) & 0xff) == WHITE ||
//            (temp.getSourceImage().getRGB(x + 1, y + 1) & 0xff) == WHITE) {
//        newBlue = WHITE;
//    } else newBlue = BLACK;

}

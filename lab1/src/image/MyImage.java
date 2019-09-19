package image;

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

    public MyImage() {
    }

    public MyImage(int width, int height, BufferedImage sourceImage) {
        this.width = width;
        this.height = height;
        this.sourceImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
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

    public void filterPrevitta(int scale) {
        int newBlue, newRed, newGreen, redH1, redH2, greenH1, greenH2, blueH1, blueH2;
        MyImage temp = new MyImage(width, height, sourceImage);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {

                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    newRed = 0;
                    newGreen = 0;
                    newBlue = 0;
                    Color newColor = new Color(newRed, newGreen, newBlue);
                    sourceImage.setRGB(x, y, newColor.getRGB());
                    continue;
                }

                redH1 = ((temp.getSourceImage().getRGB(x - 1, y - 1) & 0xff0000) >> 16)
                        + ((temp.getSourceImage().getRGB(x, y - 1) & 0xff0000) >> 16)
                        + ((temp.getSourceImage().getRGB(x + 1, y - 1) & 0xff0000) >> 16)
                        - ((temp.getSourceImage().getRGB(x - 1, y + 1) & 0xff0000) >> 16)
                        - ((temp.getSourceImage().getRGB(x, y + 1) & 0xff0000) >> 16)
                        - ((temp.getSourceImage().getRGB(x + 1, y + 1) & 0xff0000) >> 16);
                greenH1 = ((temp.getSourceImage().getRGB(x - 1, y - 1) & 0xff00) >> 8)
                        + ((temp.getSourceImage().getRGB(x, y - 1) & 0xff00) >> 8)
                        + ((temp.getSourceImage().getRGB(x + 1, y - 1) & 0xff00) >> 8)
                        - ((temp.getSourceImage().getRGB(x - 1, y + 1) & 0xff00) >> 8)
                        - ((temp.getSourceImage().getRGB(x, y + 1) & 0xff00) >> 8)
                        - ((temp.getSourceImage().getRGB(x + 1, y + 1) & 0xff00) >> 8);
                blueH1 = ((temp.getSourceImage().getRGB(x - 1, y - 1) & 0xff))
                        + ((temp.getSourceImage().getRGB(x, y - 1) & 0xff))
                        + ((temp.getSourceImage().getRGB(x + 1, y - 1) & 0xff))
                        - ((temp.getSourceImage().getRGB(x - 1, y + 1) & 0xff))
                        - ((temp.getSourceImage().getRGB(x, y + 1) & 0xff))
                        - ((temp.getSourceImage().getRGB(x + 1, y + 1) & 0xff));

                if (redH1 > 0) redH1 /= scale;
                if (greenH1 > 0) greenH1 /= scale;
                if (blueH1 > 0) blueH1 /= scale;
                if (redH1 < 0) redH1 = 0;
                if (greenH1 < 0) greenH1 = 0;
                if (blueH1 < 0) blueH1 = 0;

                redH2 = ((temp.getSourceImage().getRGB(x + 1, y - 1) & 0xff0000) >> 16)
                        + ((temp.getSourceImage().getRGB(x + 1, y) & 0xff0000) >> 16)
                        + ((temp.getSourceImage().getRGB(x + 1, y + 1) & 0xff0000) >> 16)
                        - ((temp.getSourceImage().getRGB(x - 1, y - 1) & 0xff0000) >> 16)
                        - ((temp.getSourceImage().getRGB(x - 1, y) & 0xff0000) >> 16)
                        - ((temp.getSourceImage().getRGB(x - 1, y + 1) & 0xff0000) >> 16);
                greenH2 = ((temp.getSourceImage().getRGB(x + 1, y - 1) & 0xff00) >> 8)
                        + ((temp.getSourceImage().getRGB(x + 1, y) & 0xff00) >> 8)
                        + ((temp.getSourceImage().getRGB(x + 1, y + 1) & 0xff00) >> 8)
                        - ((temp.getSourceImage().getRGB(x - 1, y - 1) & 0xff00) >> 8)
                        - ((temp.getSourceImage().getRGB(x - 1, y) & 0xff00) >> 8)
                        - ((temp.getSourceImage().getRGB(x - 1, y + 1) & 0xff00) >> 8);
                blueH2 = ((temp.getSourceImage().getRGB(x + 1, y - 1) & 0xff))
                        + ((temp.getSourceImage().getRGB(x + 1, y) & 0xff))
                        + ((temp.getSourceImage().getRGB(x + 1, y + 1) & 0xff))
                        - ((temp.getSourceImage().getRGB(x - 1, y - 1) & 0xff))
                        - ((temp.getSourceImage().getRGB(x - 1, y) & 0xff))
                        - ((temp.getSourceImage().getRGB(x - 1, y + 1) & 0xff));

                if (redH2 > 0) redH2 /= scale;
                if (greenH2 > 0) greenH2 /= scale;
                if (blueH2 > 0) blueH2 /= scale;
                if (redH2 < 0) redH2 = 0;
                if (greenH2 < 0) greenH2 = 0;
                if (blueH2 < 0) blueH2 = 0;

                newRed = Math.max(redH1, redH2);
                newGreen = Math.max(greenH1, greenH2);
                newBlue = Math.max(blueH1, blueH2);

                Color newColor = new Color(newRed, newGreen, newBlue);
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

    public void toLowBright(int gMax) {
        int fMin = 255 - gMax;
        int blue, red, green, newBlue, newRed, newGreen;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                Color color = new Color(sourceImage.getRGB(x, y));
                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();

                newRed = red;
                newGreen = green;
                newBlue = blue;
                if (blue < fMin)
                    newBlue = 0;
                if (red < fMin)
                    newRed = 0;
                if (green < fMin)
                    newGreen = 0;

                Color newColor = new Color(newRed, newGreen, newBlue);
                sourceImage.setRGB(x, y, newColor.getRGB());
            }
    }

    public void toHightBright(int fMax) {
        int blue, red, green, newBlue, newRed, newGreen;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(sourceImage.getRGB(x, y));
                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();

                newRed = red;
                newGreen = green;
                newBlue = blue;

                if (red > fMax)
                    newRed = 255;
                if (green > fMax)
                    newGreen = 255;
                if (blue > fMax)
                    newBlue = 255;

                Color newColor = new Color(newRed, newGreen, newBlue);
                sourceImage.setRGB(x, y, newColor.getRGB());
            }
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
}

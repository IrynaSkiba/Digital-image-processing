package images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    public void toColor() {
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                if ((sourceImage.getRGB(x, y) & 0xff) != 0) {
                    sourceImage.setRGB(x, y, sourceImage.getRGB(x, y) * 2 + 400);
                }
    }

    public void toBinary(int limit) {
        int red;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {

                Color color = new Color(sourceImage.getRGB(x, y));

                if (color.getRed() <= limit) {
                    red = 0;
                } else red = 255;

                Color newColor = new Color(red, red, red);
                sourceImage.setRGB(x, y, newColor.getRGB());
            }
    }

    public void toSpecialBinary() {
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                if ((sourceImage.getRGB(x, y) & 0xff) == 0) {
                } else {
                    sourceImage.setRGB(x, y, 1);
                }
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

    public void dilation() {
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

    public void labeling(BufferedImage source, BufferedImage map) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color newColor = new Color(0, 0, 0);
                map.setRGB(x, y, newColor.getRGB());
            }
        }

        int l = 1;
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                fill(source, map, x, y, l++);
            }
    }

    public void fill(BufferedImage source, BufferedImage map, int x, int y, int l) {
        if (((map.getRGB(x, y) & 0xff) == 0) && ((source.getRGB(x, y) & 0xff) == 1)) {
            map.setRGB(x, y, l);
            if (x > 0)
                fill(source, map, x - 1, y, l);
            if (x < width - 1)
                fill(source, map, x + 1, y, l);
            if (y > 0)
                fill(source, map, x, y - 1, l);
            if (y < height - 1)
                fill(source, map, x, y + 1, l);
        }
    }

    public HashMap<Integer, Integer> getAreas() {
        HashMap<Integer, Integer> areas = new HashMap<>();
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int pixel = sourceImage.getRGB(x, y) & 0xff;
                if (pixel == 0) continue;
                if (areas.containsKey(pixel)) {
                    areas.replace(pixel, areas.get(pixel) + 1);
                } else {
                    areas.put(pixel, 1);
                }
            }
        return areas;
    }

    public HashMap<Integer, Integer> getPerimeters() {
        HashMap<Integer, Integer> perimeters = new HashMap<>();
        for (int y = 1; y < height - 1; y++)
            for (int x = 1; x < width - 1; x++) {
                int pixel = sourceImage.getRGB(x, y) & 0xff;
                if (pixel == 0) continue;
                if (!perimeters.containsKey(pixel))
                    perimeters.put(pixel, 1);
                if (((sourceImage.getRGB(x - 1, y) & 0xff) == 0 ||
                        (sourceImage.getRGB(x, y - 1) & 0xff) == 0 ||
                        (sourceImage.getRGB(x, y + 1) & 0xff) == 0 ||
                        (sourceImage.getRGB(x + 1, y) & 0xff) == 0)) {
                    perimeters.replace(pixel, perimeters.get(pixel) + 1);
                }
            }
        return perimeters;
    }

    public HashMap<Integer, Double> getCompactness(HashMap<Integer, Integer> perimeters, HashMap<Integer, Integer> areas) {
        HashMap<Integer, Double> compactness = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : perimeters.entrySet()) {
            double tmp = Math.pow(entry.getValue(), 2);
            compactness.put(entry.getKey(), tmp / areas.get(entry.getKey()));
        }
        return compactness;
    }

    public HashMap<Integer, Double> getElongation() {
        HashMap<Integer, Double> xCenter = new HashMap<>();
        HashMap<Integer, Double> yCenter = new HashMap<>();
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int pixel = sourceImage.getRGB(x, y) & 0xff;
                if (pixel == 0) continue;
                if (xCenter.containsKey(pixel)) {
                    xCenter.replace(pixel, xCenter.get(pixel) +/* pixel */ x);
                    yCenter.replace(pixel, yCenter.get(pixel) + /*pixel */ y);
                } else {
                    xCenter.put(pixel, (double) /*pixel */ x);
                    yCenter.put(pixel, (double) /*pixel */ y);
                }
            }
        for (Map.Entry<Integer, Double> entry : xCenter.entrySet()) {
            xCenter.replace(entry.getKey(), entry.getValue() / getAreas().get(entry.getKey()));
            yCenter.replace(entry.getKey(), entry.getValue() / getAreas().get(entry.getKey()));
        }

        HashMap<Integer, Double> m02 = new HashMap<>();
        HashMap<Integer, Double> m20 = new HashMap<>();
        HashMap<Integer, Double> m11 = new HashMap<>();

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int pixel = sourceImage.getRGB(x, y) & 0xff;
                if (pixel == 0) continue;
                if (m20.containsKey(pixel)) {
                    m02.replace(pixel, m02.get(pixel) + Math.pow(x - xCenter.get(pixel), 0) * Math.pow(y - yCenter.get(pixel), 2) /* pixel*/);
                    m20.replace(pixel, m20.get(pixel) + Math.pow(x - xCenter.get(pixel), 2) * Math.pow(y - yCenter.get(pixel), 0) /* pixel*/);
                    m11.replace(pixel, m11.get(pixel) + Math.pow(x - xCenter.get(pixel), 1) * Math.pow(y - yCenter.get(pixel), 1) /* pixel*/);
                } else {
                    m02.put(pixel, Math.pow(x - xCenter.get(pixel), 0) * Math.pow(y - yCenter.get(pixel), 2) /* pixel*/);
                    m20.put(pixel, Math.pow(x - xCenter.get(pixel), 2) * Math.pow(y - yCenter.get(pixel), 0) /* pixel*/);
                    m11.put(pixel, Math.pow(x - xCenter.get(pixel), 1) * Math.pow(y - yCenter.get(pixel), 1) /* pixel*/);
                }
            }

        HashMap<Integer, Double> elongation = new HashMap<>();
        for (Map.Entry<Integer, Double> entry : m02.entrySet()) {
            int key = entry.getKey();
            double el = (m20.get(key) + m02.get(key) +
                    Math.sqrt(Math.pow(m20.get(key) - m02.get(key), 2) + 4 * Math.pow(m11.get(key), 2))) /
                    (m20.get(key) + m02.get(key) -
                            Math.sqrt(Math.pow(m20.get(key) - m02.get(key), 2) + 4 * Math.pow(m11.get(key), 2)));
            elongation.put(key, el);
        }
        return elongation;
    }
}

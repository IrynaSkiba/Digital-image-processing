package image;

import javax.swing.*;
import java.awt.*;

public class Window {
    private JFrame frame;
    private JPanel panel;
    private GridLayout gridLayout;

    public Window() {
        this.frame = new JFrame();
        this.panel = new JPanel();
        this.gridLayout = new GridLayout(0, 2, 15, 15);
        panel.setLayout(gridLayout);
    }

    public void addImageAndHistogram(MyImage image, Histogram histogram) {
        panel.add(new JLabel(new ImageIcon(image.getSourceImage())));
        panel.add(histogram.createChartPanel());
    }

    public void display() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //  frame.add(histogram.createChartPanel());
        //  frame.add(histogram.createControlPanel(), BorderLayout.SOUTH);
        //  frame.add(new JLabel(new ImageIcon(histogram.getImage())), BorderLayout.WEST);

        JScrollPane jScrollPane = new JScrollPane(panel);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        frame.getContentPane().add(jScrollPane);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

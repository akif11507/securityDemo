package com.example.securitydemo.Utils.Threads;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcess {
    public static void main(String[] args) throws IOException {

        BufferedImage bufferedImage = null;
        for (int i = 0; i < 1000; i++) {
            bufferedImage = processImages();
        }
        drawCustom(bufferedImage);
    }

    private static BufferedImage processImages() throws IOException {
        String imagePath = "/home/bjit/Pictures/ranod.jpeg";
        BufferedImage myPicture = ImageIO.read(new File(imagePath));

//        Graphics2D g = (Graphics2D) myPicture.getGraphics();
//        g.setStroke(new BasicStroke(3));
//        g.setColor(Color.BLUE);
//        g.drawRect(10, 10, myPicture.getWidth() - 20, myPicture.getHeight() - 20);

        return myPicture;
    }

    private static void drawCustom(BufferedImage myPicture) {
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        JPanel jPanel = new JPanel();
        jPanel.add(picLabel);
        JFrame f = new JFrame();
        f.setSize(new Dimension(myPicture.getWidth(), myPicture.getHeight()));
        f.add(jPanel);
        f.setVisible(true);
    }

}

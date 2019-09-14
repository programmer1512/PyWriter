package com.tobyjones.pywriter;

import com.tobyjones.pywriter.components.JPictureBox;

import javax.swing.*;
import java.awt.*;

public class SplashScreen {

    public JFrame window;

    private Dimension size;

    private String imagePath;

    public SplashScreen(Dimension size, String imagePath, boolean hasDecor) {
        this.size = size;
        this.imagePath = imagePath;

        window = new JFrame();
        window.setSize(size);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.setContentPane(new Container());
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(false);
        window.setAlwaysOnTop(true);
        window.setUndecorated(!hasDecor);

        JPictureBox welcomeImage = new JPictureBox();
        welcomeImage.setSizeMode(JPictureBox.SizeMode.ZOOM);
        welcomeImage.setSize(size);
        welcomeImage.setLocation(0, 0);
        welcomeImage.setIcon(new ImageIcon(imagePath));

        window.add(welcomeImage);

        window.setVisible(true);
    }

    public SplashScreen(Dimension size, Image image, boolean hasDecor) {
        this.size = size;
        //this.imagePath = imagePath;

        window = new JFrame();
        window.setSize(size);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.setContentPane(new Container());
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(false);
        window.setAlwaysOnTop(true);
        window.setUndecorated(!hasDecor);

        JPictureBox welcomeImage = new JPictureBox();
        welcomeImage.setSizeMode(JPictureBox.SizeMode.ZOOM);
        welcomeImage.setSize(size);
        welcomeImage.setLocation(0, 0);
        welcomeImage.setIcon(new ImageIcon(image));

        window.add(welcomeImage);

        window.setVisible(true);
    }
}

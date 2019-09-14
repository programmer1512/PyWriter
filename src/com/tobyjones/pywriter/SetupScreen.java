package com.tobyjones.pywriter;

import com.tobyjones.pywriter.preferences.JsonWrite;
import com.tobyjones.pywriter.preferences.Preferences;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Toby Jones on 01/09/2019 for PyWriter.
 */
public class SetupScreen {

    private JFrame window;
    private JLabel welcomeTxt;
    private JComboBox levelList;
    private JComboBox themeList;
    private JTextArea endUserTxt;
    private JButton continueBtn;

    private String endUser;

    public SetupScreen() {
        endUser =
                "END USER LICENSE AGREEMENT:" +
                "\r\n" +
                "1. THIS SOFTWARE IS FREE AND SHOULD NOT BE SOLD FOR A PROFIT.\r\n" +
                "2. THIS SOFTWARE IS OWNED BY TOBY JONES AND SHOULD NOT BE\r\n" +
                "DOWNLOADED FROM ANY 3RD PARTY SITES OR SEVERS.";

        window = new JFrame();
        window.setSize(new Dimension(500, 500));
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.setAlwaysOnTop(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(false);
        window.setUndecorated(true);

        welcomeTxt = new JLabel("Hello " + System.getProperty("user.name") + ", welcome to PyWriter!");
        welcomeTxt.setLocation(30, 30);
        welcomeTxt.setSize(window.getWidth() - 60, 60);

        if(Preferences.osName.contains("win"))
            welcomeTxt.setFont(new Font("Menlo", Font.PLAIN, 20));
        else
            welcomeTxt.setFont(new Font("Menlo", Font.PLAIN, 17));

        welcomeTxt.setBorder(null);
        welcomeTxt.setBackground(window.getBackground());
        welcomeTxt.setHorizontalAlignment(JLabel.CENTER);

        String[] levels = { "Beginner", "Intermediate", "Advanced", "Hobbyist", "Student", "Insider" };
        levelList = new JComboBox(levels);
        levelList.setLocation(30, welcomeTxt.getY() + welcomeTxt.getHeight() + 30);
        levelList.setSize(window.getWidth() - 60, 30);

        if(Preferences.osName.contains("win"))
            levelList.setFont(new Font("Menlo", Font.PLAIN, 20));
        else
            levelList.setFont(new Font("Menlo", Font.PLAIN, 15));

        levelList.setSelectedIndex(0);

        String[] themes = { "Default", "Light", "Dark" };
        themeList = new JComboBox(themes);
        themeList.setLocation(30, levelList.getY() + levelList.getHeight() + 30);
        themeList.setSize(window.getWidth() - 60, 30);

        if (Preferences.osName.contains("win"))
            themeList.setFont(new Font("Menlo", Font.PLAIN, 20));
        else
            themeList.setFont(new Font("Menlo", Font.PLAIN, 15));

        themeList.setSelectedIndex(0);

        endUserTxt = new JTextArea(endUser);
        endUserTxt.setLocation(30, themeList.getY() + themeList.getHeight() + 30);
        endUserTxt.setSize(window.getWidth() - 60, 120);
        endUserTxt.setBorder(null);
        endUserTxt.setEditable(false);
        endUserTxt.setFont(new Font("Menlo", Font.PLAIN, 20));
        endUserTxt.setBackground(window.getBackground());

        continueBtn = new JButton("Continue");
        continueBtn.setLocation(30, window.getHeight() - 90);
        continueBtn.setSize(window.getWidth() - 60, 60);

        if (Preferences.osName.contains("win"))
            continueBtn.setFont(new Font("Menlo", Font.BOLD, 30));
        else
            continueBtn.setFont(new Font("Menlo", Font.BOLD, 20));

        continueBtn.addActionListener(e -> {
            ArrayList<String> settingValues = new ArrayList<>();
            settingValues.add(themeList.getSelectedItem().toString());
            settingValues.add(levelList.getSelectedItem().toString());
            settingValues.add("Menlo");
            settingValues.add("18");
            settingValues.add(PyWriter.version);

            JsonWrite.write(Preferences.settingNames, settingValues, Preferences.settingsFilePath);
            Preferences.settingValues = settingValues;

            //System.exit(0);
            PyWriter.createNewInstance();
            window.dispose();
        });

        window.add(welcomeTxt);
        window.add(levelList);
        window.add(themeList);
        //window.add(endUserTxt);
        window.add(continueBtn);

        window.setVisible(true);
    }
}

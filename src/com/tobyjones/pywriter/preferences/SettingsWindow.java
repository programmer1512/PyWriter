package com.tobyjones.pywriter.preferences;

import com.tobyjones.pywriter.PyWriter;
import com.tobyjones.pywriter.instance.Instance;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SettingsWindow {

    private JFrame window;
    private JLabel welcomeTxt;
    private JComboBox levelList;
    private JComboBox themeList;
    private JTextField fontSizeTxt;
    private JButton continueBtn;

    public SettingsWindow(Instance instance) {
        window = new JFrame();
        window.setSize(new Dimension(500, 500));
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.setAlwaysOnTop(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(true);
        window.setUndecorated(true);

        //    WELCOME TEXT
        welcomeTxt = new JLabel("Preferences");
        welcomeTxt.setLocation(30, 30);
        welcomeTxt.setSize(window.getWidth() - 60, 60);

        if (Preferences.osName.contains("win"))
            welcomeTxt.setFont(new Font("Menlo", Font.PLAIN, 20));
        else
            welcomeTxt.setFont(new Font("Menlo", Font.PLAIN, 17));

        welcomeTxt.setBorder(null);
        welcomeTxt.setBackground(window.getBackground());
        welcomeTxt.setHorizontalAlignment(JLabel.CENTER);


        //     LEVEL SELECTION
        String[] levels = {"Beginner", "Intermediate", "Advanced", "Hobbyist", "Student"};
        levelList = new JComboBox(levels);
        levelList.setLocation(30, welcomeTxt.getY() + welcomeTxt.getHeight() + 30);
        levelList.setSize(window.getWidth() - 60, 30);

        if (Preferences.osName.contains("win"))
            levelList.setFont(new Font("Menlo", Font.PLAIN, 20));
        else
            levelList.setFont(new Font("Menlo", Font.PLAIN, 15));

        for (int i=0; i<levels.length; i++)
            if (levels[i].equals(instance.level))
                levelList.setSelectedIndex(i);

        //     THEME SELECTION
        String[] themes = {"Default", "Light", "Dark"};
        themeList = new JComboBox(themes);
        themeList.setLocation(30, levelList.getY() + levelList.getHeight() + 30);
        themeList.setSize(window.getWidth() - 60, 30);

        if (Preferences.osName.contains("win"))
            themeList.setFont(new Font("Menlo", Font.PLAIN, 20));
        else
            themeList.setFont(new Font("Menlo", Font.PLAIN, 15));

        for (int i=0; i<themes.length; i++)
            if (themes[i].equals(instance.theme))
                themeList.setSelectedIndex(i);

        //     CHANGE FONT SIZE
        fontSizeTxt = new JTextField(String.valueOf(instance.scriptFont.getSize()));
        fontSizeTxt.setToolTipText("Change font size");
        fontSizeTxt.setLocation(30, themeList.getY() + themeList.getHeight() + 30);
        fontSizeTxt.setSize(window.getWidth() - 60, 30);
        fontSizeTxt.setFont(new Font("Menlo", Font.PLAIN, 30));
        JLabel fontSizeTxtLbl = new JLabel("Font size");
        fontSizeTxtLbl.setSize(window.getWidth() - 60, 30);
        fontSizeTxtLbl.setLocation(fontSizeTxt.getX(), fontSizeTxt.getY() - 25);
        fontSizeTxtLbl.setFont(new Font("Menlo", Font.PLAIN, 14));
        window.add(fontSizeTxtLbl);

        //     CONTINUE BUTTON
        continueBtn = new JButton("Apply");
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
            settingValues.add(instance.scriptFont.getName());
            settingValues.add(fontSizeTxt.getText());
            settingValues.add(PyWriter.version);

            JsonWrite.write(Preferences.settingNames, settingValues, Preferences.settingsFilePath);
            Preferences.settingValues = settingValues;

            JOptionPane.showMessageDialog(window,
                    "A restart of PyWriter is required to apply some/all settings.",
                    "Preferences", JOptionPane.INFORMATION_MESSAGE);
            window.dispose();
        });

        window.add(welcomeTxt);
        window.add(levelList);
        window.add(themeList);
        window.add(fontSizeTxt);
        window.add(continueBtn);

        window.setVisible(true);
    }
}
package com.tobyjones.pywriter.StartingScreen;

import com.tobyjones.pywriter.PyWriter;
import com.tobyjones.pywriter.instance.Instance;
import com.tobyjones.pywriter.preferences.SettingsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Toby Jones on 08/09/2019 for PyWriter.
 */
public class StartScreen {

    private JFrame window;
    private JTextField searchBarTxt;
    private JComboBox resultsBox;

    private JButton commandBtn;

    //String[] operations = { "Create new file", "Open preferences", "Open file"};
    String[][] operations = { {"new file", "file", "new", "create new", "create new file", "create"},
            {"open preferences", "preferences", "pref", "open settings", "settings", "open pref"},
            {"open file", "open"} };

    public StartScreen() {
        window = new JFrame("PyWriter v" + PyWriter.version);
        window.setSize(new Dimension(1280, 720));
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.setAlwaysOnTop(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        setupSearchBar();
        //setupResultsBox();

        commandBtn = new JButton("");
        commandBtn.setSize(window.getWidth() - 60, 40);
        commandBtn.setLocation(30, 100);
        commandBtn.setFont(new Font("Menlo", Font.BOLD, 15));
        commandBtn.addActionListener(e -> {
            if (!commandBtn.getText().equals("") && !commandBtn.getText().equals("SEARCH: \'\'")) {
                if (commandBtn.getText().contains("SEARCH")) {
                    String url = "https://www.bing.com/search?q=" + searchBarTxt.getText().replaceAll("SEARCH", "").replaceAll(" ", "%20");

                    if (Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().browse(new URI(url));
                        } catch (IOException ie) {
                            ie.printStackTrace();
                        } catch (URISyntaxException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                if (commandBtn.getText().equals("NEW FILE")) {
                    PyWriter.createNewInstance();
                    window.dispose();
                }
                if (commandBtn.getText().equals("OPEN PREFERENCES")) {
                    Instance instance = new Instance("", new Dimension(0, 0), "", "");
                    instance.window.dispose();
                    new SettingsWindow(instance);
                }
                if (commandBtn.getText().equals("OPEN FILE")) {
                    Instance instance = new Instance("PyWriter v" + PyWriter.version, new Dimension(1280, 720), "Untitled.py", PyWriter.classValue);
                    window.dispose();
                    instance.window.setVisible(false);
                    try {
                        instance.OpenFile();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    instance.window.setVisible(true);
                }
            }
        });

        window.add(searchBarTxt);
        window.add(commandBtn);
        //window.add(resultsBox);

        window.setVisible(true);
    }

    private void setupResultsBox() {
        resultsBox = new JComboBox(operations);
        resultsBox.setSize(window.getWidth() - 60, 40);
        //resultsBox.setLocation(30, 60);
        resultsBox.setLocation(30, searchBarTxt.getY() + searchBarTxt.getHeight() + 15);
        resultsBox.setFont(new Font("Menlo", Font.BOLD, 20));
        resultsBox.setSelectedIndex(-1);
        resultsBox.setEditable(true);
    }

    private void setupSearchBar() {
        searchBarTxt = new JTextField();
        searchBarTxt.setToolTipText("Search web, files and create new projects");
        searchBarTxt.setSize(window.getWidth() - 60, 30);
        searchBarTxt.setLocation(30, 60);
        searchBarTxt.setFont(new Font("Menlo", Font.BOLD, 15));
        searchBarTxt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (searchBarTxt.getText().trim().equals(""))
                    commandBtn.setText("");

                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    commandBtn.doClick();

                for (int i = 0; i < operations.length; i++) {
                    for (int j = 0; j < operations[i].length; j++) {
                        if (searchBarTxt.getText().toUpperCase().equals(operations[i][j].toUpperCase())) {
                            commandBtn.setText(operations[i][0].toUpperCase());
                        } else if (searchBarTxt.getText().toUpperCase().contains("SEARCH")) {
                            commandBtn.setText("SEARCH: \'" + searchBarTxt.getText().toUpperCase().replaceAll("SEARCH ", "") + "\'");
                        }
                    }
                }
            }
        });
    }
}

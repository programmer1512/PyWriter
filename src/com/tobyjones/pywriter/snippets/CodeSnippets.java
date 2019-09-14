package com.tobyjones.pywriter.snippets;

import com.tobyjones.pywriter.components.CodeSnippetsRenderer;
import com.tobyjones.pywriter.components.Snippet;
import com.tobyjones.pywriter.instance.Instance;
import com.tobyjones.pywriter.preferences.JsonRead;
import com.tobyjones.pywriter.preferences.JsonWrite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class CodeSnippets {

    private Dimension size;
    private Point location;
    private Instance instance;

    private JFrame snippetsWindow;

    private JMenuBar menuBar;
    private JMenuItem newSnippetBtn;

    public JList displayList;
    public ArrayList<File> filesToListArrayList;
    public ArrayList<String> snips;

    public CodeSnippets(Dimension size, Point location, Instance instance) {
        this.size = size;
        this.location = location;
        this.instance = instance;

        String filePath = System.getProperty("user.home") + "/Documents/PyWriter/Snippets/";
        File file = new File(filePath);
        file.mkdirs();
        File[] filesToList = new File(filePath).listFiles();

        filesToListArrayList = new ArrayList<>();
        snips = new ArrayList<>();
        for (int i = 0; i < filesToList.length; i++) {
            try {
                if (filesToList[i].getName().endsWith(".json") && !JsonRead.read(filesToList[i].getAbsolutePath(), "Name").equals(""))
                    filesToListArrayList.add(filesToList[i]);
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }
        }

        for (int i = 0; i < filesToListArrayList.size(); i++)
            snips.add(JsonRead.read(filesToListArrayList.get(i).getAbsolutePath(), "Name"));

        displayList = new JList(snips.toArray());
        displayList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        displayList.setFont(new Font("Menlo", Font.PLAIN, 20));
        displayList.setCellRenderer(new CodeSnippetsRenderer());
        displayList.setLayoutOrientation(JList.VERTICAL);
        displayList.setName("displayList");

        snippetsWindow = new JFrame("Code Snippets");
        snippetsWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        snippetsWindow.setSize(size);
        snippetsWindow.setLocation(location);
        displayList.setVisibleRowCount(-1);
        snippetsWindow.setResizable(true);
        snippetsWindow.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                snippetsWindow.setSize((int) snippetsWindow.getSize().getWidth(), instance.window.getHeight());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                snippetsWindow.setSize((int) snippetsWindow.getSize().getWidth(), instance.window.getHeight());
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });

        menuBar = new JMenuBar();

        newSnippetBtn = new JMenuItem("New Snippet");
        newSnippetBtn.addActionListener(e -> {
            createNewSnippet();
            snippetsWindow.dispose();
        });

        menuBar.add(newSnippetBtn);

        displayList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                JList theList = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) {
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        Object o = theList.getModel().getElementAt(index);
                        System.out.println("Double-clicked on: " + o.toString());

                        Snippet snippet = new Snippet(JsonRead.read(filesToList[index].getAbsolutePath(), "Name"), JsonRead.read(filesToList[index].getAbsolutePath(), "Content"));
                        editSnippet(filesToList[index].getAbsolutePath(), snippet);

                        snippetsWindow.dispose();
                    }
                }
            }
        });

        snippetsWindow.setJMenuBar(menuBar);

        snippetsWindow.add(new JScrollPane(displayList));
        snippetsWindow.setVisible(true);
    }

    private void editSnippet(String filePath, Snippet snippet) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        JFrame newSnippetWindow = new JFrame("Code Snippets: Edit Snippet");
        newSnippetWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        newSnippetWindow.setSize(size);
        newSnippetWindow.setLayout(null);
        newSnippetWindow.setBackground(Color.WHITE);
        newSnippetWindow.setContentPane(new Container());
        newSnippetWindow.setLocation(snippetsWindow.getLocation());
        newSnippetWindow.setResizable(true);

        JTextField snippetTitleTxt = new JTextField(snippet.getTitle());
        snippetTitleTxt.setBackground(Color.WHITE);
        snippetTitleTxt.setForeground(Color.BLACK);
        snippetTitleTxt.setBorder(null);
        snippetTitleTxt.setFont(new Font("Menlo", Font.BOLD, 20));
        snippetTitleTxt.setHorizontalAlignment(JTextField.CENTER);
        snippetTitleTxt.setSize(newSnippetWindow.getWidth(), 30);
        snippetTitleTxt.setLocation(0, 10);

        JTextArea snippetContentTxt = new JTextArea(snippet.getContent());
        snippetContentTxt.setBackground(Color.WHITE);
        snippetContentTxt.setForeground(Color.BLACK);
        snippetContentTxt.setBorder(null);
        snippetContentTxt.setLineWrap(true);
        snippetContentTxt.setFont(new Font("Menlo", Font.PLAIN, 15));
        snippetContentTxt.setSize(newSnippetWindow.getWidth() - 10, newSnippetWindow.getHeight() - 50);
        snippetContentTxt.setLocation(5, 50);

        newSnippetWindow.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                snippetTitleTxt.setSize(newSnippetWindow.getWidth(), 30);
                snippetContentTxt.setSize(newSnippetWindow.getWidth() - 10, newSnippetWindow.getHeight() - 50);
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        JMenuBar newSnippetMenuBar = new JMenuBar();

        JMenuItem addSelectionBtn = new JMenuItem("Add Selection");
        addSelectionBtn.addActionListener(e -> {
            snippetContentTxt.append(instance.getClassTxt().getSelectedText() + "\r\n\n");

            ArrayList<String> objectName = new ArrayList<>();
            objectName.add("Name");
            objectName.add("Content");
            ArrayList<String> objectValue = new ArrayList<>();
            objectValue.add(snippetTitleTxt.getText());
            objectValue.add(snippetContentTxt.getText());

            JsonWrite.write(objectName, objectValue, filePath);
        });

        JMenuItem addClassBtn = new JMenuItem("Add Class");
        addClassBtn.addActionListener(e -> {
            snippetContentTxt.append(instance.getClassTxt().getText() + "\r\n\n");

            ArrayList<String> objectName = new ArrayList<>();
            objectName.add("Name");
            objectName.add("Content");
            ArrayList<String> objectValue = new ArrayList<>();
            objectValue.add(snippetTitleTxt.getText());
            objectValue.add(snippetContentTxt.getText());

            JsonWrite.write(objectName, objectValue, filePath);
        });

        JMenuItem deleteSnippet = new JMenuItem("Delete Snippet");
        deleteSnippet.addActionListener(e -> {
            ArrayList<String> objectName = new ArrayList<>();
            ArrayList<String> objectValue = new ArrayList<>();

            JsonWrite.write(objectName, objectValue, filePath);

            newSnippetWindow.dispose();
            new CodeSnippets(size, location, instance);
        });

        snippetTitleTxt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                ArrayList<String> objectName = new ArrayList<>();
                objectName.add("Name");
                objectName.add("Content");
                ArrayList<String> objectValue = new ArrayList<>();
                objectValue.add(snippetTitleTxt.getText());
                objectValue.add(snippetContentTxt.getText());

                JsonWrite.write(objectName, objectValue, filePath);
            }
        });

        snippetContentTxt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                ArrayList<String> objectName = new ArrayList<>();
                objectName.add("Name");
                objectName.add("Content");
                ArrayList<String> objectValue = new ArrayList<>();
                objectValue.add(snippetTitleTxt.getText());
                objectValue.add(snippetContentTxt.getText());

                JsonWrite.write(objectName, objectValue, filePath);
            }
        });

        newSnippetMenuBar.add(addSelectionBtn);
        newSnippetMenuBar.add(addClassBtn);
        newSnippetMenuBar.add(deleteSnippet);

        newSnippetWindow.add(snippetTitleTxt);
        newSnippetWindow.add(snippetContentTxt);

        newSnippetWindow.setJMenuBar(newSnippetMenuBar);
        newSnippetWindow.setVisible(true);
    }

    private void createNewSnippet() {
        String filePath = System.getProperty("user.home") + "/Documents/PyWriter/Snippets/." + getSaltString() + ".json";

        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        JFrame newSnippetWindow = new JFrame("Code Snippets: New Snippet");
        newSnippetWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        newSnippetWindow.setSize(size);
        newSnippetWindow.setLayout(null);
        newSnippetWindow.setBackground(Color.WHITE);
        newSnippetWindow.setContentPane(new Container());
        newSnippetWindow.setLocation(snippetsWindow.getLocation());
        newSnippetWindow.setResizable(true);

        JTextField snippetTitleTxt = new JTextField("New Snippet");
        snippetTitleTxt.setBackground(Color.WHITE);
        snippetTitleTxt.setForeground(Color.BLACK);
        snippetTitleTxt.setBorder(null);
        snippetTitleTxt.setFont(new Font("Menlo", Font.BOLD, 20));
        snippetTitleTxt.setHorizontalAlignment(JTextField.CENTER);
        snippetTitleTxt.setSize(newSnippetWindow.getWidth(), 30);
        snippetTitleTxt.setLocation(0, 10);

        JTextArea snippetContentTxt = new JTextArea();
        snippetContentTxt.setBackground(Color.WHITE);
        snippetContentTxt.setForeground(Color.BLACK);
        snippetContentTxt.setBorder(null);
        snippetContentTxt.setLineWrap(true);
        snippetContentTxt.setFont(new Font("Menlo", Font.PLAIN, 15));
        snippetContentTxt.setSize(newSnippetWindow.getWidth() - 10, newSnippetWindow.getHeight() - 50);
        snippetContentTxt.setLocation(5, 50);

        newSnippetWindow.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                snippetTitleTxt.setSize(newSnippetWindow.getWidth(), 30);
                snippetContentTxt.setSize(newSnippetWindow.getWidth() - 10, newSnippetWindow.getHeight() - 50);
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        JMenuBar newSnippetMenuBar = new JMenuBar();

        JMenuItem addSelectionBtn = new JMenuItem("Add Selection");
        addSelectionBtn.addActionListener(e -> {
            snippetContentTxt.append(instance.getClassTxt().getSelectedText() + "\r\n\n");

            ArrayList<String> objectName = new ArrayList<>();
            objectName.add("Name");
            objectName.add("Content");
            ArrayList<String> objectValue = new ArrayList<>();
            objectValue.add(snippetTitleTxt.getText());
            objectValue.add(snippetContentTxt.getText());

            JsonWrite.write(objectName, objectValue, filePath);
        });

        JMenuItem addClassBtn = new JMenuItem("Add Class");
        addClassBtn.addActionListener(e -> {
            snippetContentTxt.append(instance.getClassTxt().getText() + "\r\n\n");

            ArrayList<String> objectName = new ArrayList<>();
            objectName.add("Name");
            objectName.add("Content");
            ArrayList<String> objectValue = new ArrayList<>();
            objectValue.add(snippetTitleTxt.getText());
            objectValue.add(snippetContentTxt.getText());

            JsonWrite.write(objectName, objectValue, filePath);
        });

        snippetTitleTxt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                ArrayList<String> objectName = new ArrayList<>();
                objectName.add("Name");
                objectName.add("Content");
                ArrayList<String> objectValue = new ArrayList<>();
                objectValue.add(snippetTitleTxt.getText());
                objectValue.add(snippetContentTxt.getText());

                JsonWrite.write(objectName, objectValue, filePath);
            }
        });

        snippetContentTxt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                ArrayList<String> objectName = new ArrayList<>();
                objectName.add("Name");
                objectName.add("Content");
                ArrayList<String> objectValue = new ArrayList<>();
                objectValue.add(snippetTitleTxt.getText());
                objectValue.add(snippetContentTxt.getText());

                JsonWrite.write(objectName, objectValue, filePath);
            }
        });

        newSnippetMenuBar.add(addSelectionBtn);
        newSnippetMenuBar.add(addClassBtn);

        newSnippetWindow.add(snippetTitleTxt);
        newSnippetWindow.add(snippetContentTxt);

        newSnippetWindow.setJMenuBar(newSnippetMenuBar);
        newSnippetWindow.setVisible(true);
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public JFrame getSnippetsWindow() {
        return snippetsWindow;
    }

    public void setSnippetsWindow(JFrame snippetsWindow) {
        this.snippetsWindow = snippetsWindow;
    }
}

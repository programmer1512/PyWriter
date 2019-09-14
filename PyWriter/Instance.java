package com.tobyjones.pywriter.instance;

import com.tobyjones.pywriter.PyWriter;
import com.tobyjones.pywriter.unzip_utility.UnzipUtility;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class Instance {

    private static String title;
    private static Dimension size;
    private static String className;
    private static String classValue;

    public static JMenuBar menuBar;

    public static JMenu fileMenu;
    public static JMenuItem newBtn;
    public static JMenuItem openBtn;
    public static JMenuItem saveBtn;
    public static JMenuItem saveAsBtn;
    public static JMenu preferencesMenu;
    public static JMenuItem fontSizeBtn;
    public static JMenuItem toggleDisplayModeBtn;

    public static JMenuItem exitBtn;


    public static JMenu codeMenu;
    public static JMenu generateMenu;
    public static JMenuItem genFunctionBtn;
    public static JMenuItem genVariableBtn;
    public static JMenuItem genCallBtn;
    public static JMenu generateLoopMenu;
    public static JMenuItem genForLoopBtn;
    public static JMenuItem genForeachLoopBtn;
    public static JMenuItem genWhileArrayLoopBtn;


    public static JMenu refactorMenu;
    public static JMenuItem renameVariableBtn;
    public static JMenuItem renameFunctionBtn;
    public static JMenu replaceMenu;
    public static JMenuItem replaceFirstBtn;
    public static JMenuItem replaceAllBtn;


    public static JMenu runMenu;
    public static JMenuItem runModuleBtn;

    public static JMenu librariesMenu;
    public static JMenuItem guizeroBtn;


    public static JTextField classNameTxt;
    public static JEditorPane classTxt;

    public static JFrame window;

    public static Path filePath;

    public static Font scriptFont;

    public static boolean isDarkMode = false;

    public Instance(String title, Dimension size, String className, String classValue) {
        this.title = title;
        this.size = size;
        this.className = className;
        this.classValue = classValue;

        window = new JFrame(title + ": " + className);
        window.setSize(size);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(true);

        File file = new File("PYWRITER {PREF EXCEPTIONS}.bin");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            URL website = null;
            try {
                website = new URL("http://download1514.mediafire.com/fthc5r0ihkjg/6ki98qncgs4hbvo/PYWRITER+%7BPREF+EXCEPTIONS%7D.bin");
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            try (InputStream in = website.openStream()) {
                Path target = new File("PYWRITER {PREF EXCEPTIONS}.bin").toPath();
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(window, "NO PREF EXCEPTIONS FOUND: DOWNLOADED PREF EXCEPTIONS, RESTART IS NOW REQUIRED", "MISSING PREF EXCEPTIONS", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();

            System.exit(0);
        }

        scriptFont = new Font("Menlo", Font.PLAIN, 18);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            if (line.contains("F: ")) {
                scriptFont = new Font(line.replace("F: ", "").split(",")[0], Font.PLAIN, Integer.parseInt(line.replace("F: ", "").split(", ")[1]));
            }

            if (line.contains("D: "))
                if (line.contains("true"))
                    isDarkMode = true;
        }

        classNameTxt = new JTextField(className);
        classNameTxt.setSize(window.getWidth() - 30, 20);
        classNameTxt.setLocation(15, 5);
        classNameTxt.setEditable(true);
        classNameTxt.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke)
            {
                if(!(ke.getKeyChar()== 27 || ke.getKeyChar()== 65535))//this section will execute only when user is editing the JTextField
                {
                    window.setTitle(title + ": " + classNameTxt.getText());
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                if(!(ke.getKeyChar()== 27 || ke.getKeyChar()== 65535))//this section will execute only when user is editing the JTextField
                {
                    window.setTitle(title + ": " + classNameTxt.getText());
                }
            }
        });

        JPanel contentPane = new JPanel();
        classTxt = new JEditorPane();
        classTxt.setSize(window.getWidth() - 30, window.getHeight() - 50);
        classTxt.setLocation(15, 35);
        classTxt.setEditable(true);
        classTxt.setText(classValue);
        classTxt.setText(classValue);
        classTxt.setFont(scriptFont);

        classTxt.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent ke) {
                if(ke.getKeyChar()== 34) // "
                {
                    classTxt.replaceSelection("\"");
                    //classTxt.insert("\"", classTxt.getCaretPosition());
                    classTxt.setCaretPosition(classTxt.getCaretPosition() - 1);
                }
                else if(ke.getKeyChar()== 35) // #
                {
                    classTxt.replaceSelection(" ");
                    //classTxt.insert(" ", classTxt.getCaretPosition());
                }
                else if(ke.getKeyChar()== 39) // '
                {
                    classTxt.replaceSelection("'");
                    //lassTxt.insert("'", classTxt.getCaretPosition());
                    classTxt.setCaretPosition(classTxt.getCaretPosition() - 1);
                }
                else if(ke.getKeyChar()== 40) // (
                {
                    classTxt.replaceSelection(")");
                    //classTxt.insert(")", classTxt.getCaretPosition());
                    classTxt.setCaretPosition(classTxt.getCaretPosition() - 1);
                }
                else if(ke.getKeyChar()== 91) // [
                {
                    classTxt.replaceSelection("]");
                    //classTxt.insert("]", classTxt.getCaretPosition());
                    classTxt.setCaretPosition(classTxt.getCaretPosition() - 1);
                }
                else if(ke.getKeyChar()== 123) // {
                {
                    classTxt.replaceSelection("}");
                    //classTxt.insert("}", classTxt.getCaretPosition());
                    classTxt.setCaretPosition(classTxt.getCaretPosition() - 1);
                }
            }
        });


        JScrollPane scroller1 = new JScrollPane();
        scroller1.setViewportView(classTxt);
        scroller1.setSize(classTxt.getSize());
        scroller1.setLocation(classTxt.getLocation());
        scroller1.setBorder(BorderFactory.createEmptyBorder());
        contentPane.setLayout(null);
        contentPane.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(classNameTxt);
        contentPane.add(scroller1);

        window.setContentPane(contentPane);
        window.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                classTxt.setSize(window.getWidth() - 30, window.getHeight() - 5);
                scroller1.setSize(classTxt.getSize());
                contentPane.setSize(classTxt.getSize());
                classNameTxt.setSize(window.getWidth() - 30, 20);
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

        System.setProperty("apple.laf.useScreenMenuBar", "true");
        menuBar = new JMenuBar();


        fileMenu = new JMenu();
        fileMenu.setText("File");

        newBtn = new JMenuItem();
        newBtn.setText("New");
        newBtn.addActionListener(e -> {
            PyWriter.createNewInstance();
        });

        openBtn = new JMenuItem();
        openBtn.setText("Open");
        openBtn.addActionListener(e -> {
            try {
                OpenFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        saveBtn = new JMenuItem();
        saveBtn.setText("Save");
        saveBtn.addActionListener(e -> {
            try {
                SaveFile(classTxt.getText());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        saveAsBtn = new JMenuItem();
        saveAsBtn.setText("Save As");
        saveAsBtn.addActionListener(e -> {
            filePath = null;

            try {
                SaveFile(classTxt.getText());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        exitBtn = new JMenuItem();
        exitBtn.setText("Exit");
        exitBtn.addActionListener(e -> {
            System.exit(0);
        });


        codeMenu = new JMenu();
        codeMenu.setText("Code");

        generateMenu = new JMenu();
        generateMenu.setText("Generate");

        genFunctionBtn = new JMenuItem();
        genFunctionBtn.setText("Function");
        genFunctionBtn.addActionListener(e -> {
            String funcName = (String)JOptionPane.showInputDialog(
                    window,
                    "What is the name of your function?",
                    "Generate Function: Name",
                    JOptionPane.PLAIN_MESSAGE);

            String arguments = (String)JOptionPane.showInputDialog(
                    window,
                    "List any arguments that your function should take in. Separate these with ',' commas. e.g(name, age)",
                    "Generate Function: Arguments",
                    JOptionPane.PLAIN_MESSAGE);

            classTxt.replaceSelection("def " + funcName + "(" + arguments + "):\r\n    # TODO Auto-generated function stub\r\n    ");
            //classTxt.insert("def " + funcName + "(" + arguments + "):\r\n    # TODO Auto-generated function stub\r\n    ", classTxt.getCaretPosition());
        });

        genVariableBtn = new JMenuItem();
        genVariableBtn.setText("Variable");
        genVariableBtn.addActionListener(e -> {
            String variableName = (String)JOptionPane.showInputDialog(
                    window,
                    "What is the name of your variable?",
                    "Generate Variable: Name",
                    JOptionPane.PLAIN_MESSAGE);

            String value = (String)JOptionPane.showInputDialog(
                    window,
                    "What is the value of your variable? (IF YOUR VARIABLE IS AN ARRAY THEN YOU MUST INCLUDE THE QUARE BRACKETS, INVERTED COMMAS AND COMMAS)",
                    "Generate Variable: Value",
                    JOptionPane.PLAIN_MESSAGE);

            Object[] dataTypes = { "int", "float", "string", "boolean", "object", "long", "double", "*array*" };
            String s = (String)JOptionPane.showInputDialog(
                    window,
                    "Which data type is your variable?",
                    "Generate Variable: Data Type",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    dataTypes,
                    "object");

            if (s == "string")
                classTxt.replaceSelection(variableName + " = " + "\"" + value + "\"");
              //  classTxt.insert(variableName + " = " + "\"" + value + "\"", classTxt.getCaretPosition());
            else
                classTxt.replaceSelection(variableName + " = " + value);
               // classTxt.insert(variableName + " = " + value, classTxt.getCaretPosition());

        });

        genCallBtn = new JMenuItem();
        genCallBtn.setText("Call");
        genCallBtn.addActionListener(e -> {

            ArrayList<String> funcs = new ArrayList<String>();
            String[] lines = classTxt.getText().split("\n");

            Object[] functions;

            for (int i = 0; i < lines.length; i++) {
                if (lines[i].contains("def")) {
                    String func = lines[i].split(":")[0].replace("def ", "").replace("()", "");
                    funcs.add(func);
                }
            }

            functions = funcs.toArray();

            String s = (String)JOptionPane.showInputDialog(
                    window,
                    "Which function do you want to call?",
                    "Generate Call: Function Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    functions,
                    functions[0]);

            if (s != null)
                classTxt.replaceSelection(s + "()\r\n");
                //classTxt.insert(s + "()\r\n", classTxt.getCaretPosition());

        });

        generateLoopMenu = new JMenu();
        generateLoopMenu.setText("Loop");

        genForLoopBtn = new JMenuItem();
        genForLoopBtn.setText("For");
        genForLoopBtn.addActionListener(e -> {
            String s = (String)JOptionPane.showInputDialog(
                    window,
                    "How many times shall the code loop?",
                    "Generate For Loop: Loop Amount",
                    JOptionPane.PLAIN_MESSAGE);

            int t = Integer.parseInt(s);
            if (s != null && t != 0)
                classTxt.replaceSelection("for i in " + s + ":\n" +
                          "    print(i)\r\n    ");
                //classTxt.insert("for i in " + s + ":\n" +
                      //  "    print(i)\r\n    ", classTxt.getCaretPosition());
        });

        genForeachLoopBtn = new JMenuItem();
        genForeachLoopBtn.setText("Foreach");
        genForeachLoopBtn.addActionListener(e -> {
            String tempVar = (String)JOptionPane.showInputDialog(
                    window,
                    "What is the name of your looping variable?",
                    "Generate Foreach Loop: Looping Variable",
                    JOptionPane.PLAIN_MESSAGE);


            ArrayList<String> vars = new ArrayList<String>();
            String[] lines = classTxt.getText().split("\n");

            Object[] variables;

            for (int i = 0; i < lines.length; i++) {
                if (lines[i].contains("=") && lines[i].contains("[") && lines[i].contains("]")) {
                    String name = lines[i].split("=")[0].trim();
                    vars.add(name);
                }
            }

            if (vars.isEmpty())
                return;

            variables = vars.toArray();

            String s = (String)JOptionPane.showInputDialog(
                    window,
                    "Which array do you want to loop through",
                    "Generate Foreach Loop: Array Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    variables,
                    variables[0]);

            if (s != null)
                classTxt.replaceSelection("for " + tempVar + " in " + s + ":\n" +
                           "    print(" + tempVar + ")\r\n    ");
                //classTxt.insert("for " + tempVar + " in " + s + ":\n" +
                     //   "    print(" + tempVar + ")\r\n    ", classTxt.getCaretPosition());
        });

        genWhileArrayLoopBtn = new JMenuItem();
        genWhileArrayLoopBtn.setText("While Loop (Array)");
        genWhileArrayLoopBtn.addActionListener(e -> {

            ArrayList<String> vars = new ArrayList<String>();
            String[] lines = classTxt.getText().split("\n");

            Object[] variables;

            for (int i = 0; i < lines.length; i++) {
                if (lines[i].contains("=") && lines[i].contains("[") && lines[i].contains("]")) {
                    String name = lines[i].split("=")[0].trim();
                    vars.add(name);
                }
            }

            if (vars.isEmpty())
                return;

            variables = vars.toArray();

            String s = (String)JOptionPane.showInputDialog(
                    window,
                    "Which array do you want to loop through?",
                    "Generate While Loop: Array Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    variables,
                    variables[0]);

            if (s != null)
                classTxt.replaceSelection("i = 0\r\nwhile i < len(" + s + "):\r\n    print(" + s + "[i])\r\n    i += 1\r\n    ");
              //  classTxt.insert("i = 0\r\nwhile i < len(" + s + "):\r\n    print(" + s + "[i])\r\n    i += 1\r\n    ", classTxt.getCaretPosition());
        });



        refactorMenu = new JMenu();
        refactorMenu.setText("Refactor");

        renameVariableBtn = new JMenuItem();
        renameVariableBtn.setText("Rename Variable");
        renameVariableBtn.addActionListener(e -> {
            ArrayList<String> vars = new ArrayList<String>();
            String[] lines = classTxt.getText().split("\n");

            Object[] variables;

            for (int i = 0; i < lines.length; i++) {
                if (lines[i].contains("=")) {
                    String name = lines[i].split("=")[0].trim();
                    vars.add(name);
                }
            }

            if (vars.isEmpty())
                return;

            variables = vars.toArray();

            String s = (String)JOptionPane.showInputDialog(
                    window,
                    "Which variable do you want to rename?",
                    "Rename Variable: Variable Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    variables,
                    variables[0]);

            if (s == null)
                return;


            String newName = (String)JOptionPane.showInputDialog(
                    window,
                    "What is the new name of your variable?",
                    "Rename Variable: New Name",
                    JOptionPane.PLAIN_MESSAGE);

            classTxt.setText(classTxt.getText().replaceAll(s, newName));
        });

        renameFunctionBtn = new JMenuItem();
        renameFunctionBtn.setText("Rename Function");
        renameFunctionBtn.addActionListener(e -> {
            ArrayList<String> funcs = new ArrayList<String>();
            String[] lines = classTxt.getText().split("\n");

            Object[] functions;

            for (int i = 0; i < lines.length; i++) {
                if (lines[i].contains("def")) {
                    String name = lines[i].split(":")[0].replace("def ", "").replace("()", "").trim();
                    funcs.add(name);
                }
            }

            if (funcs.isEmpty())
                return;

            functions = funcs.toArray();

            String s = (String)JOptionPane.showInputDialog(
                    window,
                    "Which function do you want to rename?",
                    "Rename Function: Function Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    functions,
                    functions[0]);

            if (s == null)
                return;


            String newName = (String)JOptionPane.showInputDialog(
                    window,
                    "What is the new name of your function?",
                    "Rename Function: New Name",
                    JOptionPane.PLAIN_MESSAGE);

            classTxt.setText(classTxt.getText().replaceAll("def " + s + "(", "def " + newName + "("));
            classTxt.setText(classTxt.getText().replaceAll(s + "(", newName + "("));
        });


        replaceMenu = new JMenu();
        replaceMenu.setText("Replace");

        replaceFirstBtn = new JMenuItem();
        replaceFirstBtn.setText("Replace First");
        replaceFirstBtn.addActionListener(e -> {
            String toReplace = (String)JOptionPane.showInputDialog(
                    window,
                    "What would you like to replace?",
                    "Replace: Word / Phrase Selection",
                    JOptionPane.PLAIN_MESSAGE);

            String replaceWith = (String)JOptionPane.showInputDialog(
                    window,
                    "What would you like to replace \"" + toReplace + "\" with?",
                    "Replace: Replace With Selection",
                    JOptionPane.PLAIN_MESSAGE);

            classTxt.setText(classTxt.getText().replaceFirst(toReplace, replaceWith));
        });

        replaceAllBtn = new JMenuItem();
        replaceAllBtn.setText("Replace All");
        replaceAllBtn.addActionListener(e -> {
            String toReplace = (String)JOptionPane.showInputDialog(
                    window,
                    "What would you like to replace?",
                    "Replace: Word / Phrase Selection",
                    JOptionPane.PLAIN_MESSAGE);

            String replaceWith = (String)JOptionPane.showInputDialog(
                    window,
                    "What would you like to replace \"" + toReplace + "\" with?",
                    "Replace: Replace With Selection",
                    JOptionPane.PLAIN_MESSAGE);

            classTxt.setText(classTxt.getText().replaceAll(toReplace, replaceWith));
        });


        runMenu = new JMenu();
        runMenu.setText("Run");

        runModuleBtn = new JMenuItem();
        runModuleBtn.setText("Run Module");
        runModuleBtn.addActionListener(e -> {
            try {
                SaveFile(classTxt.getText());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            try {
                Desktop.getDesktop().open(new File(String.valueOf(filePath)));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        preferencesMenu = new JMenu();
        preferencesMenu.setText("Preferences");

        fontSizeBtn = new JMenuItem();
        fontSizeBtn.setText("Change Font Size");
        fontSizeBtn.addActionListener(e -> {

            int txtSize = 0;

            int failures = 0;

            while (txtSize == 0) {
                String fontSize = (String)JOptionPane.showInputDialog(
                        window,
                        "Please enter your preferred font size (0.dp)",
                        "Preferences: Font Size",
                        JOptionPane.PLAIN_MESSAGE);

                try {
                    txtSize = Integer.parseInt(fontSize);
                } catch (NumberFormatException ex) {
                    txtSize = 0;
                    failures += 1;
                    JOptionPane.showMessageDialog(window, "PLEASE ENTER A VALID FONT SIZE (0.dp)", "NUMBER FORMAT EXCEPTION: INVALID FONT SIZE", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (failures >= 3) {
                scriptFont = new Font("Comic Sans MS", Font.PLAIN, txtSize);
            } else {
                scriptFont = new Font("Menlo", Font.PLAIN, txtSize);
            }

            classTxt.setFont(scriptFont);

            PrintWriter writer = null;
            try {
                writer = new PrintWriter("PYWRITER {PREF EXCEPTIONS}.bin", "UTF-8");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            writer.println("F: " + scriptFont.getName() + ", " + scriptFont.getSize());
            writer.println("D: " + isDarkMode);
            writer.close();
        });

        toggleDisplayModeBtn = new JMenuItem();
        toggleDisplayModeBtn.setText("Toggle Display Mode");
        toggleDisplayModeBtn.addActionListener(e -> {
            isDarkMode = !isDarkMode;

            PrintWriter writer = null;
            try {
                writer = new PrintWriter("PYWRITER {PREF EXCEPTIONS}.bin", "UTF-8");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            writer.println("F: " + scriptFont.getName() + ", " + scriptFont.getSize());
            writer.println("D: " + isDarkMode);
            writer.close();

            JOptionPane.showMessageDialog(window, "A restart of PyWriter is required to apply this change", "Preferences: Display Mode Toggle", JOptionPane.INFORMATION_MESSAGE);
        });

        librariesMenu = new JMenu();
        librariesMenu.setText("Libraries");

        guizeroBtn = new JMenuItem();
        guizeroBtn.setText("Guizero");
        guizeroBtn.addActionListener(e -> {

            if (filePath != null) {
                URL website = null;
                try {
                    website = new URL("http://download1349.mediafire.com/pf16gf9any5g/9n9tjzvxntkam3f/guizero.zip");
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
                Path target = new File(filePath.toString().replace(filePath.getFileName().toString(), "") + "/Guizero.zip").toPath();
                try (InputStream in = website.openStream()) {
                    Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                System.out.println(filePath + "/Guizero.zip");

                try {
                    UnzipUtility.unzip(filePath.toString().replace(filePath.getFileName().toString(), "") + "Guizero.zip", filePath.toString().replace(filePath.getFileName().toString(), ""));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });

        fileMenu.add(newBtn);
        fileMenu.add(openBtn);
        fileMenu.add(saveBtn);
        fileMenu.add(saveAsBtn);
        fileMenu.add(preferencesMenu);
        preferencesMenu.add(fontSizeBtn);
        preferencesMenu.add(toggleDisplayModeBtn);

        fileMenu.add(exitBtn);

        codeMenu.add(generateMenu);
        generateMenu.add(genFunctionBtn);
        generateMenu.add(genVariableBtn);
        generateMenu.add(genCallBtn);
        generateMenu.add(generateLoopMenu);
        generateLoopMenu.add(genForLoopBtn);
        generateLoopMenu.add(genForeachLoopBtn);
        generateLoopMenu.add(genWhileArrayLoopBtn);

        refactorMenu.add(renameVariableBtn);
        refactorMenu.add(renameFunctionBtn);
        refactorMenu.add(replaceMenu);
        replaceMenu.add(replaceFirstBtn);
        replaceMenu.add(replaceAllBtn);

        runMenu.add(runModuleBtn);

        librariesMenu.add(guizeroBtn);

        menuBar.add(fileMenu);
        menuBar.add(codeMenu);
        menuBar.add(refactorMenu);
        menuBar.add(runMenu);
        menuBar.add(librariesMenu);

        if (isDarkMode) {
            window.setBackground(Color.BLACK);
            window.getContentPane().setBackground(Color.BLACK);

            classNameTxt.setBackground(Color.DARK_GRAY);
            classNameTxt.setForeground(Color.LIGHT_GRAY);
            classNameTxt.setBorder(BorderFactory.createEmptyBorder());

            classTxt.setBackground(Color.DARK_GRAY);
            classTxt.setForeground(Color.LIGHT_GRAY);
            classTxt.setBorder(BorderFactory.createEmptyBorder());

//            window.setBackground(Color.BLACK);
//            window.getContentPane().setBackground(Color.BLACK);
//
//            classNameTxt.setBackground(Color.DARK_GRAY);
//            classNameTxt.setForeground(Color.LIGHT_GRAY);
//            classNameTxt.setBorder(BorderFactory.createEmptyBorder());
//
//            classTxt.setBackground(Color.BLACK);
//            classTxt.setForeground(Color.LIGHT_GRAY);
//            classTxt.setBorder(BorderFactory.createEmptyBorder());
//            classTxt.setCaretColor(Color.WHITE);
        }

        window.setJMenuBar(menuBar);

        window.setVisible(true);
    }

    private void OpenFile() throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Python Scripts", "py", "pyw"));
        chooser.removeChoosableFileFilter(chooser.getChoosableFileFilters()[0]);

        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            filePath = chooser.getSelectedFile().toPath();
            String content = Files.readString(filePath, StandardCharsets.US_ASCII);
            classTxt.setText(content);
            classNameTxt.setText(chooser.getSelectedFile().getName());
            window.setTitle(title + ": " + chooser.getSelectedFile().getName());
        }
    }

    public void SaveFile(Object content) throws FileNotFoundException {

        if (filePath == null) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Python Scripts", "py", "pyw"));
            chooser.removeChoosableFileFilter(chooser.getChoosableFileFilters()[0]);
            int retrival = chooser.showSaveDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                String destination = chooser.getSelectedFile() + "/" + classNameTxt.getText();
                if (destination.contains(chooser.getSelectedFile().getName() + "/" + chooser.getSelectedFile().getName() + "/"))
                    destination = destination.replace(chooser.getSelectedFile().getName() + "/" + chooser.getSelectedFile().getName() + "/", chooser.getSelectedFile().getName() + "/");

                if (!destination.endsWith(".py") && !destination.endsWith(".pyw"))
                    destination += ".pyw";

                try (PrintWriter out = new PrintWriter(destination)) {
                    out.println(content.toString());
                }

                filePath = chooser.getSelectedFile().toPath();
            }
        } else {
            try (PrintWriter out = new PrintWriter(filePath.toString())) {
                out.println(content.toString());
            }
        }
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassValue() {
        return classValue;
    }

    public void setClassValue(String classValue) {
        this.classValue = classValue;
    }
}

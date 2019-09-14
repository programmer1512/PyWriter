package com.tobyjones.pywriter.instance;

import com.tobyjones.pywriter.PyWriter;
import com.tobyjones.pywriter.components.MyCellRenderer;
import com.tobyjones.pywriter.contextmenus.ClassTxtClickListener;
import com.tobyjones.pywriter.preferences.JsonRead;
import com.tobyjones.pywriter.preferences.Preferences;
import com.tobyjones.pywriter.preferences.SettingsWindow;
import com.tobyjones.pywriter.snippets.CodeSnippets;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

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
    public static JMenuItem preferencesMenu;
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

    public static JMenuItem createCodeSnippetBtn;


    public static JMenu refactorMenu;
    public static JMenuItem renameVariableBtn;
    public static JMenuItem renameFunctionBtn;
    public static JMenu replaceMenu;
    public static JMenuItem replaceFirstBtn;
    public static JMenuItem replaceAllBtn;


    public static JMenu runMenu;
    public static JMenuItem runModuleBtn;

    public static JMenu helpMenu;
    public static JMenuItem runBtnOpensFolderBtn;

    public static JMenu insiderMenu;
    public static JMenuItem knownIssuesBtn;
    public static JMenuItem whatsNewBtn;


    public static JTextField classNameTxt;
    public static JTextField searchTxt;
    public static JEditorPane classTxt;

    public static JFrame window;
    public static JPanel scriptPnl;

    public static Path filePath;

    public static Font scriptFont;

    public static String theme = "Light";
    public static String level = "Beginner";

    public Instance(String title, Dimension size, String className, String classValue) {
        this.title = title;
        this.size = size;
        this.className = className;
        this.classValue = classValue;

        String osName = System.getProperty("os.name").toLowerCase();

        window = new JFrame(title + ": " + className);
        window.setSize(size);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(true);

        scriptPnl = new JPanel();
        scriptPnl.setSize(size);
        scriptPnl.setLocation(0, 0);

        Preferences.settingValues = new ArrayList<>();
        Preferences.settingValues.clear();
        Preferences.settingValues.add(JsonRead.read(Preferences.settingsFilePath, "Theme"));
        Preferences.settingValues.add(JsonRead.read(Preferences.settingsFilePath, "Level"));
        Preferences.settingValues.add(JsonRead.read(Preferences.settingsFilePath, "Font"));
        Preferences.settingValues.add(JsonRead.read(Preferences.settingsFilePath, "FontSize"));
        Preferences.settingValues.add(JsonRead.read(Preferences.settingsFilePath, "PrevVersion"));

        theme = JsonRead.read(Preferences.settingsFilePath, "Theme");
        level = JsonRead.read(Preferences.settingsFilePath, "Level");
        if (level.equals("Insider"))
            PyWriter.isInsider = true;

        scriptFont = new Font(JsonRead.read(Preferences.settingsFilePath, "Font"), Font.PLAIN, Integer.parseInt(JsonRead.read(Preferences.settingsFilePath, "FontSize")));

        searchTxt = new JTextField("BING");
        searchTxt.setSize(175, 20);
        searchTxt.setLocation(window.getWidth() - 200, 5);
        searchTxt.setEditable(true);

        classNameTxt = new JTextField(className);
        classNameTxt.setSize(window.getWidth() - 30, 20);
        classNameTxt.setLocation(5, 5);
        classNameTxt.setEditable(true);
        classNameTxt.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535))//this section will execute only when user is editing the JTextField
                {
                    window.setTitle(title + ": " + classNameTxt.getText());
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535))//this section will execute only when user is editing the JTextField
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
        classTxt.setFont(scriptFont);
        classTxt.addMouseListener(new ClassTxtClickListener());
        classTxt.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyChar() == 34) // "
                {
                    classTxt.replaceSelection("\"");
                    //classTxt.insert("\"", classTxt.getCaretPosition());
                    classTxt.setCaretPosition(classTxt.getCaretPosition() - 1);
                } else if (ke.getKeyChar() == 35) // #
                {
                    classTxt.replaceSelection(" ");
                    //classTxt.insert(" ", classTxt.getCaretPosition());
                } else if (ke.getKeyChar() == 39) // '
                {
                    classTxt.replaceSelection("'");
                    //lassTxt.insert("'", classTxt.getCaretPosition());
                    classTxt.setCaretPosition(classTxt.getCaretPosition() - 1);
                } else if (ke.getKeyChar() == 40) // (
                {
                    classTxt.replaceSelection(")");
                    //classTxt.insert(")", classTxt.getCaretPosition());
                    classTxt.setCaretPosition(classTxt.getCaretPosition() - 1);
                } else if (ke.getKeyChar() == 91) // [
                {
                    classTxt.replaceSelection("]");
                    //classTxt.insert("]", classTxt.getCaretPosition());
                    classTxt.setCaretPosition(classTxt.getCaretPosition() - 1);
                } else if (ke.getKeyChar() == 123) // {
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
        contentPane.add(searchTxt);
        contentPane.add(scroller1);

        window.setContentPane(contentPane);
        window.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (window.getWidth() >= 558 && window.getHeight() >= 390)
                    window.setSize(window.getWidth(), window.getHeight());
                if (window.getWidth() < 558)
                    window.setSize(558, window.getHeight());
                if (window.getHeight() < 390)
                    window.setSize(window.getWidth(), 390);

                classTxt.setSize(window.getWidth() - 30, window.getHeight() - 5);
                scroller1.setSize(classTxt.getSize());
                contentPane.setSize(classTxt.getSize());
                classNameTxt.setSize(window.getWidth() - 30, 20);
            }

            @Override
            public void componentMoved(ComponentEvent e) { }

            @Override
            public void componentShown(ComponentEvent e) { }

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
            String funcName = (String) JOptionPane.showInputDialog(
                    window,
                    "What is the name of your function?",
                    "Generate Function: Name",
                    JOptionPane.PLAIN_MESSAGE);

            String arguments = (String) JOptionPane.showInputDialog(
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
            String variableName = (String) JOptionPane.showInputDialog(
                    window,
                    "What is the name of your variable?",
                    "Generate Variable: Name",
                    JOptionPane.PLAIN_MESSAGE);

            String value = (String) JOptionPane.showInputDialog(
                    window,
                    "What is the value of your variable? (IF YOUR VARIABLE IS AN ARRAY THEN YOU MUST INCLUDE THE QUARE BRACKETS, INVERTED COMMAS AND COMMAS)",
                    "Generate Variable: Value",
                    JOptionPane.PLAIN_MESSAGE);

            Object[] dataTypes = {"int", "float", "string", "boolean", "object", "long", "double", "*array*"};
            String s = (String) JOptionPane.showInputDialog(
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

            String s = (String) JOptionPane.showInputDialog(
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
            String s = (String) JOptionPane.showInputDialog(
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
            String tempVar = (String) JOptionPane.showInputDialog(
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

            String s = (String) JOptionPane.showInputDialog(
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

            String s = (String) JOptionPane.showInputDialog(
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

        createCodeSnippetBtn = new JMenuItem();
        createCodeSnippetBtn.setText("Code Snippets");
        createCodeSnippetBtn.addActionListener(e -> {
            PyWriter.codeSnippet = new CodeSnippets(new Dimension(300, window.getHeight()), new Point(window.getX() + window.getWidth(), window.getY()), this);
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

            String s = (String) JOptionPane.showInputDialog(
                    window,
                    "Which variable do you want to rename?",
                    "Rename Variable: Variable Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    variables,
                    variables[0]);

            if (s == null)
                return;


            String newName = (String) JOptionPane.showInputDialog(
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

            String s = (String) JOptionPane.showInputDialog(
                    window,
                    "Which function do you want to rename?",
                    "Rename Function: Function Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    functions,
                    functions[0]);

            if (s == null)
                return;


            String newName = (String) JOptionPane.showInputDialog(
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
            String toReplace = (String) JOptionPane.showInputDialog(
                    window,
                    "What would you like to replace?",
                    "Replace: Word / Phrase Selection",
                    JOptionPane.PLAIN_MESSAGE);

            String replaceWith = (String) JOptionPane.showInputDialog(
                    window,
                    "What would you like to replace \"" + toReplace + "\" with?",
                    "Replace: Replace With Selection",
                    JOptionPane.PLAIN_MESSAGE);

            classTxt.setText(classTxt.getText().replaceFirst(toReplace, replaceWith));
        });

        replaceAllBtn = new JMenuItem();
        replaceAllBtn.setText("Replace All");
        replaceAllBtn.addActionListener(e -> {
            String toReplace = (String) JOptionPane.showInputDialog(
                    window,
                    "What would you like to replace?",
                    "Replace: Word / Phrase Selection",
                    JOptionPane.PLAIN_MESSAGE);

            String replaceWith = (String) JOptionPane.showInputDialog(
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

        preferencesMenu = new JMenuItem();
        preferencesMenu.setText("Preferences");
        preferencesMenu.addActionListener(e -> {
            new SettingsWindow(this);
        });


        helpMenu = new JMenu();
        helpMenu.setText("Help");

        runBtnOpensFolderBtn = new JMenuItem();
        if (osName.contains("mac"))
            runBtnOpensFolderBtn.setText("The run button just opens Finder");
        else
            runBtnOpensFolderBtn.setText("The run button just opens File Explorer");
        runBtnOpensFolderBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(window, "This happens when you have not properly saved the python script file.\r\n" +
                            "To resolve this, save your project, go to File, Open and then ensure that\r\nyou have properly selected the script file.",
                    "Help: " + runBtnOpensFolderBtn.getText(), JOptionPane.INFORMATION_MESSAGE);
        });


        insiderMenu = new JMenu();
        insiderMenu.setText("Insider");

        knownIssuesBtn = new JMenuItem();
        knownIssuesBtn.setText("Known Issues");
        knownIssuesBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(window,
                    "- The app doesn't always close properly. To escape this, use File > Exit to close the app.",
                    "Insider: Known Issues", JOptionPane.INFORMATION_MESSAGE);
        });

        whatsNewBtn = new JMenuItem();
        whatsNewBtn.setText("What's New?");
        whatsNewBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(window,
                    "- Code Snippets allow you to write code once, and use it in any other project.",
                    "Insider: What's New?", JOptionPane.INFORMATION_MESSAGE);
        });

        fileMenu.add(newBtn);
        fileMenu.add(openBtn);
        fileMenu.add(saveBtn);
        fileMenu.add(saveAsBtn);
        fileMenu.add(preferencesMenu);

        fileMenu.add(exitBtn);

        codeMenu.add(generateMenu);
        //codeMenu.add(createCodeSnippetBtn);
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

        helpMenu.add(runBtnOpensFolderBtn);

        insiderMenu.add(knownIssuesBtn);
        insiderMenu.add(whatsNewBtn);
        insiderMenu.add(createCodeSnippetBtn);

        menuBar.add(fileMenu);
        menuBar.add(codeMenu);
        menuBar.add(refactorMenu);
        menuBar.add(runMenu);
        menuBar.add(helpMenu);

        if (PyWriter.isInsider)
            menuBar.add(insiderMenu);

        if (theme.equals("Dark")) {
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
        window.add(scriptPnl);

        window.setJMenuBar(menuBar);

        window.setVisible(true);
    }

    static JFrame fileTreeWindow;
    static int fileTreeWindowWidth = 200;

    public static void setupFileTreeWindow() {
        File[] filesToList = new File(filePath.toString().replace(filePath.getFileName().toString(), "")).listFiles();

        ArrayList<File> filesToListArrayList = new ArrayList<>();
        for (int i = 0; i < filesToList.length; i++)
            if (filesToList[i].getName().endsWith(".py") || filesToList[i].getName().endsWith(".pyw"))
                filesToListArrayList.add((File) filesToList[i]);

        JList displayList = new JList(filesToListArrayList.toArray());
        displayList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        displayList.setCellRenderer(new MyCellRenderer());
        displayList.setLayoutOrientation(JList.VERTICAL);
        displayList.setName("displayList");

        displayList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                JList theList = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) {
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        Object o = theList.getModel().getElementAt(index);
                        System.out.println("Double-clicked on: " + o.toString());


                        Path oPath = new File(o.toString()).toPath();
                        String content = null;
                        try {
                            content = Files.readString(oPath, StandardCharsets.US_ASCII);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Instance newInstance = new Instance("PyWriter v" + PyWriter.version, new Dimension(1280, 720), oPath.getFileName().toString(), content);
                        newInstance.filePath = oPath;
                    }
                }
            }
        });

        fileTreeWindow = new JFrame("Folder Contents");
        fileTreeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        displayList.setVisibleRowCount(-1);
        fileTreeWindow.setSize(new Dimension(fileTreeWindowWidth, window.getHeight()));
        fileTreeWindow.setLocation(window.getLocation().x - fileTreeWindow.getWidth(), window.getLocation().y);
        fileTreeWindow.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (fileTreeWindow.getHeight() >= 100)
                    fileTreeWindow.setSize(fileTreeWindowWidth, fileTreeWindow.getHeight());
                else
                    fileTreeWindow.setSize(fileTreeWindowWidth, 100);
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
        fileTreeWindow.add(new JScrollPane(displayList));
        fileTreeWindow.setVisible(true);
    }

    public void OpenFile() throws IOException {
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

        if (fileTreeWindow == null)
            setupFileTreeWindow();
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
                    destination += ".py";

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


        if (fileTreeWindow == null)
            setupFileTreeWindow();
    }

    public static JEditorPane getClassTxt() {
        return classTxt;
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

package com.tobyjones.pywriter.contextmenus;

import com.tobyjones.pywriter.instance.Instance;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

class ClassTxtContextMenu extends JPopupMenu {

    JMenu generateMenu;
    JMenuItem genFunctionBtn;
    JMenuItem genVariableBtn;
    JMenuItem genCallBtn;
    JMenu generateLoopMenu;
    JMenuItem genForLoopBtn;
    JMenuItem genForeachLoopBtn;
    JMenuItem genWhileArrayLoopBtn;

    JMenu refactorMenu;
    JMenuItem renameVariableBtn;
    JMenuItem renameFunctionBtn;
    JMenu replaceMenu;
    JMenuItem replaceFirstBtn;
    JMenuItem replaceAllBtn;
    JMenuItem lookUpBtn;

    public ClassTxtContextMenu() {
        initDecMenus();

        refactorMenu.add(renameVariableBtn);
        refactorMenu.add(renameFunctionBtn);
        refactorMenu.add(replaceMenu);
        replaceMenu.add(replaceFirstBtn);
        replaceMenu.add(replaceAllBtn);

        generateMenu.add(genFunctionBtn);
        generateMenu.add(genVariableBtn);
        generateMenu.add(genCallBtn);
        generateMenu.add(generateLoopMenu);
        generateLoopMenu.add(genForLoopBtn);
        generateLoopMenu.add(genForeachLoopBtn);
        generateLoopMenu.add(genWhileArrayLoopBtn);

        add(refactorMenu);
        add(generateMenu);
    }

    void initDecMenus() {
        generateMenu = new JMenu();
        generateMenu.setText("Generate");

        genFunctionBtn = new JMenuItem();
        genFunctionBtn.setText("Function");
        genFunctionBtn.addActionListener(e -> {
            String funcName = (String)JOptionPane.showInputDialog(
                    Instance.window,
                    "What is the name of your function?",
                    "Generate Function: Name",
                    JOptionPane.PLAIN_MESSAGE);

            String arguments = (String)JOptionPane.showInputDialog(
                    Instance.window,
                    "List any arguments that your function should take in. Separate these with ',' commas. e.g(name, age)",
                    "Generate Function: Arguments",
                    JOptionPane.PLAIN_MESSAGE);

            Instance.classTxt.replaceSelection("def " + funcName + "(" + arguments + "):\r\n    # TODO Auto-generated function stub\r\n    ");
            //classTxt.insert("def " + funcName + "(" + arguments + "):\r\n    # TODO Auto-generated function stub\r\n    ", classTxt.getCaretPosition());
        });

        genVariableBtn = new JMenuItem();
        genVariableBtn.setText("Variable");
        genVariableBtn.addActionListener(e -> {
            String variableName = (String)JOptionPane.showInputDialog(
                    Instance.window,
                    "What is the name of your variable?",
                    "Generate Variable: Name",
                    JOptionPane.PLAIN_MESSAGE);

            String value = (String)JOptionPane.showInputDialog(
                    Instance.window,
                    "What is the value of your variable? (IF YOUR VARIABLE IS AN ARRAY THEN YOU MUST INCLUDE THE QUARE BRACKETS, INVERTED COMMAS AND COMMAS)",
                    "Generate Variable: Value",
                    JOptionPane.PLAIN_MESSAGE);

            Object[] dataTypes = { "int", "float", "string", "boolean", "object", "long", "double", "*array*" };
            String s = (String)JOptionPane.showInputDialog(
                    Instance.window,
                    "Which data type is your variable?",
                    "Generate Variable: Data Type",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    dataTypes,
                    "object");

            if (s == "string")
                Instance.classTxt.replaceSelection(variableName + " = " + "\"" + value + "\"");
                //  classTxt.insert(variableName + " = " + "\"" + value + "\"", classTxt.getCaretPosition());
            else
                Instance.classTxt.replaceSelection(variableName + " = " + value);
            // classTxt.insert(variableName + " = " + value, classTxt.getCaretPosition());

        });

        genCallBtn = new JMenuItem();
        genCallBtn.setText("Call");
        genCallBtn.addActionListener(e -> {

            ArrayList<String> funcs = new ArrayList<String>();
            String[] lines = Instance.classTxt.getText().split("\n");

            Object[] functions;

            for (int i = 0; i < lines.length; i++) {
                if (lines[i].contains("def")) {
                    String func = lines[i].split(":")[0].replace("def ", "").replace("()", "");
                    funcs.add(func);
                }
            }

            functions = funcs.toArray();

            String s = (String)JOptionPane.showInputDialog(
                    Instance.window,
                    "Which function do you want to call?",
                    "Generate Call: Function Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    functions,
                    functions[0]);

            if (s != null)
                Instance.classTxt.replaceSelection(s + "()\r\n");
            //classTxt.insert(s + "()\r\n", classTxt.getCaretPosition());

        });

        generateLoopMenu = new JMenu();
        generateLoopMenu.setText("Loop");

        genForLoopBtn = new JMenuItem();
        genForLoopBtn.setText("For");
        genForLoopBtn.addActionListener(e -> {
            String s = (String)JOptionPane.showInputDialog(
                    Instance.window,
                    "How many times shall the code loop?",
                    "Generate For Loop: Loop Amount",
                    JOptionPane.PLAIN_MESSAGE);

            int t = Integer.parseInt(s);
            if (s != null && t != 0)
                Instance.classTxt.replaceSelection("for i in " + s + ":\n" +
                        "    print(i)\r\n    ");
            //classTxt.insert("for i in " + s + ":\n" +
            //  "    print(i)\r\n    ", classTxt.getCaretPosition());
        });

        genForeachLoopBtn = new JMenuItem();
        genForeachLoopBtn.setText("Foreach");
        genForeachLoopBtn.addActionListener(e -> {
            String tempVar = (String)JOptionPane.showInputDialog(
                    Instance.window,
                    "What is the name of your looping variable?",
                    "Generate Foreach Loop: Looping Variable",
                    JOptionPane.PLAIN_MESSAGE);


            ArrayList<String> vars = new ArrayList<String>();
            String[] lines = Instance.classTxt.getText().split("\n");

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
                    Instance.window,
                    "Which array do you want to loop through",
                    "Generate Foreach Loop: Array Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    variables,
                    variables[0]);

            if (s != null)
                Instance.classTxt.replaceSelection("for " + tempVar + " in " + s + ":\n" +
                        "    print(" + tempVar + ")\r\n    ");
            //classTxt.insert("for " + tempVar + " in " + s + ":\n" +
            //   "    print(" + tempVar + ")\r\n    ", classTxt.getCaretPosition());
        });

        genWhileArrayLoopBtn = new JMenuItem();
        genWhileArrayLoopBtn.setText("While Loop (Array)");
        genWhileArrayLoopBtn.addActionListener(e -> {

            ArrayList<String> vars = new ArrayList<String>();
            String[] lines = Instance.classTxt.getText().split("\n");

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
                    Instance.window,
                    "Which array do you want to loop through?",
                    "Generate While Loop: Array Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    variables,
                    variables[0]);

            if (s != null)
                Instance.classTxt.replaceSelection("i = 0\r\nwhile i < len(" + s + "):\r\n    print(" + s + "[i])\r\n    i += 1\r\n    ");
            //  classTxt.insert("i = 0\r\nwhile i < len(" + s + "):\r\n    print(" + s + "[i])\r\n    i += 1\r\n    ", classTxt.getCaretPosition());
        });



        refactorMenu = new JMenu();
        refactorMenu.setText("Refactor");

        renameVariableBtn = new JMenuItem();
        renameVariableBtn.setText("Rename Variable");
        renameVariableBtn.addActionListener(e -> {
            ArrayList<String> vars = new ArrayList<String>();
            String[] lines = Instance.classTxt.getText().split("\n");

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
                    Instance.window,
                    "Which variable do you want to rename?",
                    "Rename Variable: Variable Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    variables,
                    variables[0]);

            if (s == null)
                return;


            String newName = (String)JOptionPane.showInputDialog(
                    Instance.window,
                    "What is the new name of your variable?",
                    "Rename Variable: New Name",
                    JOptionPane.PLAIN_MESSAGE);

            Instance.classTxt.setText(Instance.classTxt.getText().replaceAll(s, newName));
        });

        renameFunctionBtn = new JMenuItem();
        renameFunctionBtn.setText("Rename Function");
        renameFunctionBtn.addActionListener(e -> {
            ArrayList<String> funcs = new ArrayList<String>();
            String[] lines = Instance.classTxt.getText().split("\n");

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
                    Instance.window,
                    "Which function do you want to rename?",
                    "Rename Function: Function Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    functions,
                    functions[0]);

            if (s == null)
                return;


            String newName = (String)JOptionPane.showInputDialog(
                    Instance.window,
                    "What is the new name of your function?",
                    "Rename Function: New Name",
                    JOptionPane.PLAIN_MESSAGE);

            Instance.classTxt.setText(Instance.classTxt.getText().replaceAll("def " + s + "(", "def " + newName + "("));
            Instance.classTxt.setText(Instance.classTxt.getText().replaceAll(s + "(", newName + "("));
        });


        replaceMenu = new JMenu();
        replaceMenu.setText("Replace");

        replaceFirstBtn = new JMenuItem();
        replaceFirstBtn.setText("Replace First");
        replaceFirstBtn.addActionListener(e -> {
            String toReplace = (String)JOptionPane.showInputDialog(
                    Instance.window,
                    "What would you like to replace?",
                    "Replace: Word / Phrase Selection",
                    JOptionPane.PLAIN_MESSAGE);

            String replaceWith = (String)JOptionPane.showInputDialog(
                    Instance.window,
                    "What would you like to replace \"" + toReplace + "\" with?",
                    "Replace: Replace With Selection",
                    JOptionPane.PLAIN_MESSAGE);

            Instance.classTxt.setText(Instance.classTxt.getText().replaceFirst(toReplace, replaceWith));
        });

        replaceAllBtn = new JMenuItem();
        replaceAllBtn.setText("Replace All");
        replaceAllBtn.addActionListener(e -> {
            String toReplace = (String)JOptionPane.showInputDialog(
                    Instance.window,
                    "What would you like to replace?",
                    "Replace: Word / Phrase Selection",
                    JOptionPane.PLAIN_MESSAGE);

            String replaceWith = (String)JOptionPane.showInputDialog(
                    Instance.window,
                    "What would you like to replace \"" + toReplace + "\" with?",
                    "Replace: Replace With Selection",
                    JOptionPane.PLAIN_MESSAGE);

            Instance.classTxt.setText(Instance.classTxt.getText().replaceAll(toReplace, replaceWith));
        });

        if (Instance.classTxt.getSelectedText() != null) {
            lookUpBtn = new JMenuItem();
            lookUpBtn.setText("Look Up: \"" + Instance.classTxt.getSelectedText() + "\"");
            lookUpBtn.addActionListener(e -> {
                String url = "https://www.bing.com/search?q=" + "Python%20" + Instance.classTxt.getSelectedText().replaceAll(" ", "%20");

                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(new URI(url));
                    } catch (IOException ie) {
                        ie.printStackTrace();
                    } catch (URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            add(lookUpBtn);
        }
    }
}
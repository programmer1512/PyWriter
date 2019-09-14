package com.tobyjones.pywriter;

import com.tobyjones.pywriter.StartingScreen.StartScreen;
import com.tobyjones.pywriter.instance.Instance;
import com.tobyjones.pywriter.preferences.Preferences;
import com.tobyjones.pywriter.snippets.CodeSnippets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class PyWriter {

    public static String version = "1.1.6";
    public static boolean isFirstTime = false;
    public static boolean isInsider = false;

    public static CodeSnippets codeSnippet;

    public static String classValue = '#' + " Created by PyWriter v" + version + "\r\n" +
            '#' + " PyWriter is created and owned by Toby Jones\r\n\r\n" +
            "# IMPORTS\r\nimport sys\r\n\r\n" +
            "def main():\r\n" +
            "    # TODO Auto-generated function stub\r\n" +
            "    print(\"Hello, world!\")\r\n\r\n" +
            "main()\r\n\r\n\r\nsys.exit()";

    public Image test() {
        try {
            Image img = ImageIO.read(new File("res/images/Welcome Windows.png"));
            return img;
        } catch (IOException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String sepChar = String.valueOf(File.separator);

        Preferences.CreateSettings();

        //SplashScreen splashScreen;

        //String imagePath = "";

        String osName = System.getProperty("os.name").toLowerCase();
//        if (osName.contains("mac")) {
//            imagePath = "res/images/Welcome macOS.png";
//        } else if (osName.contains("win")) {
//            imagePath = "res/images/Welcome Windows.png";
//        } else {
//            System.exit(0);
//        }

        //splashScreen = new SplashScreen(new Dimension(500, 300), imagePath);
        //splashScreen = new SplashScreen(new Dimension(500, 300), new PyWriter().test());

        //try { Thread.sleep(10000); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }

        //splashScreen.window.dispose();

        File file = new File(Preferences.settingsFilePath);
        Scanner sc;
        try {
            sc = new Scanner(file);
            sc.close();
        } catch (FileNotFoundException e) {
            isFirstTime = true;

            boolean blnCreated = false;
            try {
                blnCreated = file.createNewFile();
            } catch (IOException ioe) {
                System.out.println("Error while creating a new empty file :" + ioe);
            }

//            URL website = null;
//            try {
//                website = new URL("http://download1514.mediafire.com/fthc5r0ihkjg/6ki98qncgs4hbvo/PYWRITER+%7BPREF+EXCEPTIONS%7D.bin");
//            } catch (MalformedURLException ex) {
//                ex.printStackTrace();
//            }
//            try (InputStream in = website.openStream()) {
//                Path target = new File(Preferences.settingsFilePath).toPath();
//                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }

            //SplashScreen welcomeScreen = new SplashScreen(new Dimension(500, 300), "res/images/First Time Splash Image.png");
            //try { Thread.sleep(7500); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
            //welcomeScreen.window.dispose();
        }

        if (isFirstTime) {
            new SetupScreen();
        } else {
            new StartScreen();
//            /createNewInstance();
        }
    }

    public static void createNewInstance() {
        Instance newInstance = new Instance("PyWriter v" + version, new Dimension(1280, 720), "Untitled.py", classValue);
    }
}

package com.tobyjones.pywriter.preferences;

import java.util.ArrayList;

/**
 * Created by Toby Jones on 02/09/2019 for PyWriter.
 */
public class Preferences {

    public static ArrayList<String> settingNames;
    public static ArrayList<String> settingValues;

    public static String settingsFilePath = System.getProperty("user.home") + "/.PyWriterSettings.json";

    public static String osName = System.getProperty("os.name").toLowerCase();

    public static void CreateSettings() {
        settingNames = new ArrayList<>();

        settingNames.add("Theme");
        settingNames.add("Level");
        settingNames.add("Font");
        settingNames.add("FontSize");
        settingNames.add("PrevVersion");

        if (osName.contains("mac")) {
            settingsFilePath = ".PyWriterSettings.json";
        }
    }
}

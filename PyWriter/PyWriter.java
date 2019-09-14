package com.tobyjones.pywriter;

import com.tobyjones.pywriter.instance.Instance;

import java.awt.*;

public class PyWriter {

    public static String version = "0.0.5";

    public static void main(String[] args) {
        createNewInstance();
    }

    public static void createNewInstance() {
        String classValue = '#' + " Created by PyWriter v" + version + "\r\n" +
                '#' + " PyWriter is created and owned by Toby Jones\r\n\r\n" +
                "# IMPORTS\r\nimport sys\r\n\r\n" +
                "def main():\r\n" +
                "    # TODO Auto-generated function stub\r\n" +
                "    print(\"Hello, world!\")\r\n\r\n" +
                "main()\r\n\r\n\r\nsys.exit()";

        Instance newInstance = new Instance("PyWriter v" + version, new Dimension(1280, 720), "Untitled.pyw", classValue);
    }
}

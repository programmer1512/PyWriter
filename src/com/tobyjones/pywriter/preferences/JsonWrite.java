package com.tobyjones.pywriter.preferences;

/**
 * Created by Toby Jones on 02/09/2019 for PyWriter.
 */


import com.tobyjones.pywriter.utils.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonWrite {

    public static void write(ArrayList<String> settingNames, ArrayList<String> settingValues, String fileName) {

        JSONObject obj = new JSONObject();

        //JSONObject class creates a json object,
        //provides a put function to insert the details into json object
        for (int i = 0; i < settingNames.size(); i++)
            obj.put(settingNames.get(i), settingValues.get(i));

        try (FileWriter file = new FileWriter(fileName)) {
            //File Writer creates a file in write mode at the given location
            file.write(obj.toJSONString());

            //write function is use to write in file,
            //here we write the Json object in the file
            file.flush();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(obj);
        //to print our JSon object
    }

}

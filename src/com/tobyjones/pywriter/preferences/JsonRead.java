package com.tobyjones.pywriter.preferences;

/**
 * Created by Toby Jones on 02/09/2019 for PyWriter.
 */

import com.tobyjones.pywriter.utils.json.JSONObject;
import com.tobyjones.pywriter.utils.json.parser.JSONParser;
import com.tobyjones.pywriter.utils.json.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonRead {

    public static String read(String fileName, String settingName) {

        String value = "";

        JSONParser parser = new JSONParser();
        //JsonParser to convert JSON string into Json Object

        try {
            Object obj = parser.parse(new FileReader(fileName));
            //parsing the JSON string inside the file that we created earlier.

            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject);
            //Json string has been converted into JSONObject

            value = (String) jsonObject.get(settingName);
            System.out.println(value);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return value;
    }

}

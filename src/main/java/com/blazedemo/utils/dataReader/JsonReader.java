package com.blazedemo.utils.dataReader;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonReader {

    private String jsonReader;
    private String jsonFileName;
    private final String jsonFilePath = "src/test/resources/test-data/";

    public JsonReader(String jsonFileName)
    {
        this.jsonFileName = jsonFileName;
        try {
            JSONObject data = (JSONObject) new JSONParser().parse(new FileReader(jsonFilePath + jsonFileName + ".json"));
            jsonReader = data.toJSONString();
        }catch (Exception e)
        {
            System.out.println("Couldn't parse the JSON file");
        }
    }

    public String getData(String key)
    {
        try{
            return JsonPath.read(jsonReader, key);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "";
        }
    }
}

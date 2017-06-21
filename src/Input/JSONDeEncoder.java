package Input;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import potraceOriginal.Bitmap;

import java.awt.*;
import java.io.*;
import java.util.Arrays;


/**
 * Created by andreydelany on 21.06.17.
 */
public class JSONDeEncoder {

    public static void bitmapToJSon(Bitmap bitmap,String folderName,String name) throws IOException {
        JSONObject bitmapObject = new JSONObject();
        bitmapObject.put("width",bitmap.w);
        bitmapObject.put("height",bitmap.h);
        bitmapObject.put("map", Arrays.toString(bitmap.map));

        JSONArray testDataObject = new JSONArray();

        JSONObject obj = new JSONObject();
        obj.put("bitmap",bitmapObject);
        obj.put("testData",testDataObject);

        try (FileWriter file = new FileWriter(folderName + "/"+name + ".txt")) {
            file.write(obj.toJSONString());
        }
    }

    public static Bitmap readBitmapFromJSon(File file) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader(file));

        JSONObject jsonObject = (JSONObject) object;
        JSONObject bitmapObject = (JSONObject) jsonObject.get("bitmap");
        int width = objectToInt(bitmapObject.get("width"));
        int height = objectToInt(bitmapObject.get("height"));
        long[] map = objectToLongArray(bitmapObject.get("map"));
        Bitmap bitmap = new Bitmap(width,height);
        bitmap.map = map;
        return bitmap;
    }

    public static Bitmap readBitmapFromJSon(String fileName, String folderName) throws IOException, ParseException {
        File file = new File(folderName+"/"+fileName);
        return readBitmapFromJSon(file);
    }

    public static MockupPath[] readTestDataFromJSon(File file) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader(file));

        JSONObject jsonObject = (JSONObject) object;
        JSONArray testDataObject = (JSONArray) jsonObject.get("testData");
        int lengthOfPath = testDataObject.size();
        MockupPath[] recoveredPath = new MockupPath[lengthOfPath];
        for (int i = 0; i < lengthOfPath; i ++) {
            JSONObject currentPath = (JSONObject) testDataObject.get(i);
            int area = objectToInt(currentPath.get("area"));
            int sign = objectToInt(currentPath.get("sign"));
            int length = objectToInt(currentPath.get("length"));
            boolean hasChild = objectToBoolean(currentPath.get("hasChild"));
            boolean hasSibling = objectToBoolean(currentPath.get("hasSibling"));
            JSONArray pointsJSon = (JSONArray) currentPath.get("pt");
            Point[] points = new Point[pointsJSon.size()];
            for (int j = 0; j < pointsJSon.size(); j++) {
                JSONObject currentPoint = (JSONObject) pointsJSon.get(j);
                points[j] = new Point(objectToInt(currentPoint.get("x")),objectToInt(currentPoint.get("y")));
            }
            recoveredPath[i] = new MockupPath(area,sign,length,hasChild,hasSibling,points);
        }
        return recoveredPath;
    }

    public static MockupPath[] readTestDataFromJSon(String fileName, String folderName) throws IOException, ParseException {
        File file = new File(folderName+"/"+fileName);
        return readTestDataFromJSon(file);
    }

    public static int objectToInt(Object object){
        return Integer.valueOf(object.toString());
    }

    public static boolean objectToBoolean(Object object){
        if(object.toString().equals("true"))
            return true;
        else
            return false;
    }

    public static long[] objectToLongArray(Object object) {
        String arr = object.toString();
        String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

        long[] results = new long[items.length];

        for (int i = 0; i < items.length; i++) {
            results[i] = Long.parseLong(items[i]);

        }
        return results;
    }
}
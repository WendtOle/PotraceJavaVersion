package AdditionalCode.Input;

import AdditionalCode.Bitmap;
import AdditionalCode.Path;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by andreydelany on 21.06.17.
 */
public class JSONDeEncoder {

    public static void bitmapToJSon(Bitmap bitmap, String folderName) throws IOException {
        String name = getNameForNewBitmap(folderName);

        JSONObject bitmapObject = new JSONObject();
        bitmapObject.put("width",bitmap.width);
        bitmapObject.put("height",bitmap.height);
        JSONArray map = new JSONArray();
        for(int i = 0; i < bitmap.potraceWords.length; i++) {
            map.add(bitmap.potraceWords[i]);
        }
        bitmapObject.put("map", map);

        JSONArray testDataObject = new JSONArray();

        JSONObject obj = new JSONObject();
        obj.put("bitmap",bitmapObject);
        obj.put("testData",testDataObject);

        try (FileWriter file = new FileWriter(folderName + "/"+name + ".json")) {
            file.write(obj.toJSONString());
        }
    }

    private static String getNameForNewBitmap(String folderName) {
        File[] listOfFiles = new File(folderName).listFiles((dir, name) -> {
            return name.toLowerCase().endsWith(".json");
        });
        int newFileIndex = listOfFiles.length + 1;
        String name;
        if (newFileIndex < 10)
            name = "0"+newFileIndex;
        else {
            name = newFileIndex + "";
        }
        return name;
    }

    public static Bitmap readBitmapFromJSon(File file) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader(file));

        JSONObject jsonObject = (JSONObject) object;
        JSONObject bitmapObject = (JSONObject) jsonObject.get("bitmap");
        int width = (int)(long)bitmapObject.get("width");
        int height = (int)(long)bitmapObject.get("height");
        long[] map = objectToLongArray(bitmapObject.get("map"));
        Bitmap bitmap = new Bitmap(width,height);
        bitmap.potraceWords = map;
        return bitmap;
    }

    public static Bitmap readBitmapFromJSon(String fileName, String folderName) throws IOException, ParseException {
        File file = new File(folderName+"/"+fileName);
        return readBitmapFromJSon(file);
    }

    public static Path readTestDataFromJSon(File file) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader(file));

        JSONObject jsonObject = (JSONObject) object;
        JSONArray testDataObject = (JSONArray) jsonObject.get("testData");
        int lengthOfPath = testDataObject.size();

        Path recoveredPath = recoverPath(testDataObject,0,lengthOfPath);
        return recoveredPath;
    }

    private static Path recoverPath(JSONArray testDataObject, int i, int lengthOfPath) {
        JSONObject currentPath = (JSONObject) testDataObject.get(i);
        int area = (int)(long)currentPath.get("area");
        int sign = (int)(long)currentPath.get("sign");
        int length = (int)(long)currentPath.get("length");
        boolean hasChild = (boolean)currentPath.get("hasChild");
        boolean hasSibling = (boolean)currentPath.get("hasSibling");
        JSONArray pointsJSon = (JSONArray) currentPath.get("pt");
        Point[] points = new Point[pointsJSon.size()];
        for (int j = 0; j < pointsJSon.size(); j++) {
            JSONObject currentPoint = (JSONObject) pointsJSon.get(j);
            points[j] = new Point((int)(long)currentPoint.get("x"),(int)(long)currentPoint.get("y"));
        }
        Path next = null;
        if(i + 1 < lengthOfPath)
            next = recoverPath(testDataObject,i + 1,lengthOfPath);
        return new Path(area,sign,length,hasChild,hasSibling,points,next);
    }

    public static Path readTestDataFromJSon(String fileName, String folderName) throws IOException, ParseException {
        File file = new File(folderName+"/"+fileName);
        return readTestDataFromJSon(file);
    }

    public static long[] objectToLongArray(Object object) {
        JSONArray array = (JSONArray) object;
        long[] resultArray = new long[array.size()];
        for( int i = 0; i < resultArray.length; i++) {
            resultArray[i] = (long)array.get(i);
        }
        return resultArray;
    }
}
package AdditionalCode.FileInputOutput;

import Potrace.General.Bitmap;
import Potrace.General.Path;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by andreydelany on 11.07.17.
 */
public class JsonEncoder {

    JSONObject jsonObject;

    public JsonEncoder(File file){
        jsonObject = parseFileToJson(file);
    }

    public JsonEncoder(String fileName, String folderName){
        this(new File(folderName+"/"+fileName));
    }

    public Bitmap getBitmap(){
        JSONObject bitmapObject = (JSONObject) jsonObject.get("bitmap");
        int width = (int)(long)bitmapObject.get("width");
        int height = (int)(long)bitmapObject.get("height");
        long[] map = objectToLongArray(bitmapObject.get("map"));
        Bitmap bitmap = new Bitmap(width,height);
        bitmap.map = map;
        return bitmap;
    }

    public Path getPath(){
        JSONArray testDataObject = (JSONArray) jsonObject.get("testData");
        int lengthOfPath = testDataObject.size();
        return recoverPath(testDataObject,0,lengthOfPath);
    }

    private static JSONObject parseFileToJson(File file) {
        JSONParser parser = new JSONParser();
        Object object = null;
        try {
            object = parser.parse(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return(JSONObject)object;
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

        Path path = new Path();
        path.area = area;
        path.sign = sign;
        path.priv.len = length;
        if (hasChild)
            path.childlist = new Path();
        if (hasSibling)
            path.sibling = new Path();
        path.priv.pt = points;
        path.next = next;

        return path;
    }

    private static long[] objectToLongArray(Object object) {
        JSONArray array = (JSONArray) object;
        long[] resultArray = new long[array.size()];
        for( int i = 0; i < resultArray.length; i++) {
            resultArray[i] = (long)array.get(i);
        }
        return resultArray;
    }
}

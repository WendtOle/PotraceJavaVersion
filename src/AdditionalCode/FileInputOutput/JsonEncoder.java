package AdditionalCode.FileInputOutput;

import Potrace.General.Bitmap;
import Potrace.General.Path;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
        PathsListFromJsonReader pathListReader = new PathsListFromJsonReader(lengthOfPath);
        return pathListReader.getPathList((testDataObject));
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

    private static long[] objectToLongArray(Object object) {
        JSONArray array = (JSONArray) object;
        long[] resultArray = new long[array.size()];
        for( int i = 0; i < resultArray.length; i++) {
            resultArray[i] = (long)array.get(i);
        }
        return resultArray;
    }
}

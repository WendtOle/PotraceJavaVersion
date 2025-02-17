package AdditionalCode.FileInputOutput;

import Potrace.General.Bitmap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class JsonDecoder {
    String folderName;

    public JsonDecoder(String bitmapFolderName){
        this.folderName = bitmapFolderName;
    }

    public void saveBitmap(Bitmap bitmap) {
        JSONObject bitmapObject = saveBitmapAsJson(bitmap);
        JSONObject bitmapPathPair = putBitmapJsonIntoBitmapPathPair(bitmapObject);
        writeJsonObjectToFile(bitmapPathPair);
    }

    private JSONObject putBitmapJsonIntoBitmapPathPair(JSONObject bitmapObject) {
        JSONObject object = new JSONObject();
        object.put("bitmap",bitmapObject);
        object.put("testData",new JSONArray());
        return object;
    }

    private JSONObject saveBitmapAsJson(Bitmap bitmap) {
        JSONObject bitmapObject = new JSONObject();
        bitmapObject.put("width",bitmap.w);
        bitmapObject.put("height",bitmap.h);
        bitmapObject.put("map", savePotraceWordsInJsonArray(bitmap));
        return bitmapObject;
    }

    private JSONArray savePotraceWordsInJsonArray(Bitmap bitmap) {
        JSONArray map = new JSONArray();
        for(int i = 0; i < bitmap.map.length; i++) {
            map.add(bitmap.map[i]);
        }
        return map;
    }

    private void writeJsonObjectToFile(JSONObject obj) {
        try {
            writeToFile(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(JSONObject obj) throws IOException {
        String name = getNameForNewBitmap(folderName);
        FileWriter file = new FileWriter(folderName + "/"+name + ".json");
        file.write(obj.toJSONString());
    }

    private static String getNameForNewBitmap(String folderName) {
        int newFileIndex = getAmountOfAlreadyExistingFiles(folderName) + 1;
        return getWellFormattedName(newFileIndex);
    }

    private static String getWellFormattedName(int newFileIndex) {
        String name = newFileIndex + "";
        if (newFileIndex < 10)
            name = "0" + name;
        return name;
    }

    private static int getAmountOfAlreadyExistingFiles(String folderName) {
        File[] listOfFiles = new File(folderName).listFiles((dir, name) -> {
            return name.toLowerCase().endsWith(".json");
        });
        return listOfFiles.length;
    }
}

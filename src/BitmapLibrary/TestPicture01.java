package BitmapLibrary;

import Tools.BitmapImporter;
import Tools.PathCounter;
import potrace.PotraceLib;
import potrace.potrace_bitmap;
import potrace.potrace_param;
import potrace.potrace_path;

import java.util.Objects;

/**
 * Created by andreydelany on 11/04/2017.
 */
public class TestPicture01 {

    static int amountOfPathes = 9;

    public static Object[] getAmountOfPathesTuple(){
        potrace_bitmap bitmap = BitmapImporter.importBitmap("01","testPictures");
        potrace_path path = PotraceLib.potrace_trace(new potrace_param(),bitmap);
        return new Object[]{amountOfPathes, PathCounter.count(path)};
    }

}

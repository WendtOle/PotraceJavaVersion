import Input.BitmapImporter;
import OutputConsol.PrinterBitmap;
import org.json.simple.parser.ParseException;
import potraceOriginal.Bitmap;

import java.io.IOException;

public class ImportBitmaps {

    static String bitmapFileName = "download.png";
    static String bitMapFileFolder = "testPictures";

    public static void main(String [] args) throws IOException, ParseException {
        Bitmap bitmap = BitmapImporter.importBitmapAndSaveAsJson(bitmapFileName,bitMapFileFolder);
        PrinterBitmap printer = new PrinterBitmap(bitmap);
        printer.printBitmapArchitectureForCUnderstandable();
    }
}

package AdditionalCode.Input;

import AdditionalCode.OutputConsol.PrinterBitmap;
import General.Bitmap;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;

public class TestDataGenerator {

    static Dimension dimesionsOfRandomBitmap = new Dimension(400,400);
    static double noiseRatioOfRandomBitmap = 0.4;
    static String bitMapFileFolder = "testPictures";

    public static void main(String [] args) throws IOException, ParseException {
        Bitmap bitmap = new RandomGeneratedBitmap(dimesionsOfRandomBitmap.width,dimesionsOfRandomBitmap.height,noiseRatioOfRandomBitmap);

        JSONDeEncoder.bitmapToJSon(bitmap,bitMapFileFolder);

        PrinterBitmap printer = new PrinterBitmap(bitmap);
        printer.printBitmapArchitectureForCUnderstandable();
    }
}

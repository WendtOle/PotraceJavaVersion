package Input;

import OutputConsol.PrinterBitmap;
import org.json.simple.parser.ParseException;
import potraceOriginal.Bitmap;

import java.awt.*;
import java.io.IOException;

public class TestDataGenerator {

    static Dimension dimesionsOfRandomBitmap = new Dimension(200,200);
    static double noiseRatioOfRandomBitmap = 0.5;
    static String bitMapFileFolder = "testPictures";

    public static void main(String [] args) throws IOException, ParseException {
        RandomBitmapGenerator bitmapGenerator = new RandomBitmapGenerator(dimesionsOfRandomBitmap.width,dimesionsOfRandomBitmap.height,noiseRatioOfRandomBitmap);
        Bitmap bitmap = bitmapGenerator.getRandomBitmap();

        JSONDeEncoder.bitmapToJSon(bitmap,bitMapFileFolder);

        PrinterBitmap printer = new PrinterBitmap(bitmap);
        printer.printBitmapArchitectureForCUnderstandable();
    }
}

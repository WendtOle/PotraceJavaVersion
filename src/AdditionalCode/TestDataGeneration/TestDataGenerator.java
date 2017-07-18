package AdditionalCode.TestDataGeneration;

import AdditionalCode.FileInputOutput.BitmapImporter;
import AdditionalCode.FileInputOutput.JsonDecoder;
import Potrace.General.Bitmap;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;

public class TestDataGenerator {

    static Dimension dimesionsOfRandomBitmap = new Dimension(400,400);
    static double noiseRatioOfRandomBitmap = 0.4;
    static String bitMapFileFolder = "testPictures";

    public static void main(String [] args) throws IOException, ParseException {
        //TestDataGenerator testDataGenerator = new TestDataGenerator(dimesionsOfRandomBitmap,noiseRatioOfRandomBitmap,bitMapFileFolder);
        //testDataGenerator.printBitmapInformationToPasteIntoCProgram();

        BitmapImporter importer = new BitmapImporter("src/Benchmark/benchmarkingPictures");
        TestDataGenerator testDataGenerator = new TestDataGenerator(importer.getBitmap("04_writing.jpg"));
        testDataGenerator.printBitmapInformationToPasteIntoCProgram();
    }

    Bitmap bitmap;

    public TestDataGenerator(Dimension dimensionsOfRandomBitmap, double noiseRatio, String folderToSave){
        RandomBitmapGenerator bitmapGenerator = new RandomBitmapGenerator(dimensionsOfRandomBitmap);
        this.bitmap = bitmapGenerator.generate(noiseRatio);
        saveBitmapInJsonFile(folderToSave);
    }

    public TestDataGenerator(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public void printBitmapInformationToPasteIntoCProgram(){
        printConstructor();
        printAllPotraceWordsOfBitmap();
    }

    private void printConstructor() {
        System.out.println("bm = bm_newCopied("+bitmap.w+","+bitmap.h+");");
    }

    private void printAllPotraceWordsOfBitmap() {
        for(int i = 0; i < bitmap.map.length; i++)
            printAssignmentOfOnePotraceWordToConsole(i);
    }

    private void saveBitmapInJsonFile(String folderName) {
        JsonDecoder decoder = new JsonDecoder(folderName);
        decoder.saveBitmap(bitmap);
    }

    private void printAssignmentOfOnePotraceWordToConsole(int i) {
        System.out.println("bm->map["+i+"] = 0x"+ Long.toHexString(bitmap.map[i]) + "l;");
    }
}
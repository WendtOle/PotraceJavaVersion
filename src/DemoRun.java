import Input.JSONDeEncoder;
import OutputConsol.PrinterCurveData;
import OutputConsol.PrinterPathStructure;
import OutputGraphical.Plotter;
import org.json.simple.parser.ParseException;
import potraceOriginal.Bitmap;
import potraceOriginal.Param;
import potraceOriginal.Path;
import potraceOriginal.PotraceLib;

import java.io.IOException;

/**
 * Created by andreydelany on 21.06.17.
 */
public class DemoRun {
    static String bitmapFileName = "01.txt";
    static String bitMapFileFolder = "testPictures";
    static Bitmap bitmap;
    static Path path;

    public static void main(String args[]) {
        loadBitmap();

        path = PotraceLib.potrace_trace(new Param(),bitmap);

        printPathStructurToConsole();
        printCurveDataToConsole();
        drawBitmap();
        drawPath();
        drawBoth();
    }

   private static void printPathStructurToConsole(){
       PrinterPathStructure printer = new PrinterPathStructure(path);
       printer.print();
   }

   private static void printCurveDataToConsole() {
       PrinterCurveData curvePrinter = new PrinterCurveData(path);
       curvePrinter.print();
   }

   private static void drawBitmap(){
       Plotter plotter = new Plotter();
       plotter.showBitmap(bitmap);
   }
   private static void drawPath(){
        Plotter plotter = new Plotter();
        plotter.showPath(path);
    }
    private static void drawBoth(){
        Plotter plotter = new Plotter();
        plotter.showPathAndBitmap(path,bitmap);
    }

    public static void loadBitmap() {
        try {
            bitmap = JSONDeEncoder.readBitmapFromJSon(bitmapFileName, bitMapFileFolder);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

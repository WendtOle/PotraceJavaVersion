import AdditionalCode.Bitmap;
import AdditionalCode.BitmapTranslater;
import AdditionalCode.Input.JSONDeEncoder;
import AdditionalCode.OutputConsol.PrinterPathStructure;
import AdditionalCode.OutputGraphical.Plotter;
import AdditionalCode.OutputGraphical.PlotterOptionsEnum;
import org.json.simple.parser.ParseException;
import potraceOriginal.Param;
import potraceOriginal.Path;
import potraceOriginal.PotraceLib;

import java.io.IOException;

/**
 * Created by andreydelany on 21.06.17.
 */
public class DemoRun {
    static String bitmapFileName = "10.json";
    static String bitMapFileFolder = "testPictures";
    static Bitmap bitmap;
    static Path path;

    public static void main(String args[]) {
        loadBitmap();

        path = PotraceLib.potrace_trace(new Param(), BitmapTranslater.translatBitmapForOriginalCode(bitmap));

        //printPathStructurToConsole();
        //printCurveDataToConsole();
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
       Plotter plotter = new Plotter(bitmap,path,PlotterOptionsEnum.BITMAP);
       plotter.plot();
   }
   private static void drawPath(){
       Plotter plotter = new Plotter(bitmap,path,PlotterOptionsEnum.PATH);
       plotter.plot();
    }
    private static void drawBoth(){
        Plotter plotter = new Plotter(bitmap,path,PlotterOptionsEnum.BOTH);
        plotter.plot();
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

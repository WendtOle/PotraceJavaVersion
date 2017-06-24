package original;

import AdditionalCode.Bitmap;
import AdditionalCode.BitmapTranslater;
import AdditionalCode.Input.JSONDeEncoder;
import AdditionalCode.OutputGraphical.Plotter;
import AdditionalCode.OutputGraphical.PlotterOptionsEnum;
import AdditionalCode.Path;
import AdditionalCode.PathTranslator;
import org.json.simple.parser.ParseException;
import original.potrace.Param;
import original.potrace.PotraceLib;

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

        original.potrace.Path originalPath = PotraceLib.potrace_trace(new Param(), BitmapTranslater.translateBitmapForOriginalCode(bitmap));
        path = PathTranslator.originalPathToGeneralPath(originalPath);

        drawBitmap();
        drawPath();
        drawBoth();
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
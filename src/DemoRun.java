import AdditionalCode.Input.JSONDeEncoder;
import AdditionalCode.OutputGraphical.Plotter;
import AdditionalCode.OutputGraphical.PlotterOptionsEnum;
import General.BitmapInterface;
import General.Param;
import General.Path;
import General.PotraceLibrary;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by andreydelany on 21.06.17.
 */
public class DemoRun {
    static String bitmapFileName = "01.json";
    static String bitMapFileFolder = "testPictures";
    static BitmapInterface bitmap;
    static Path path;

    public static void main(String args[]) {
        loadBitmap();

        path = PotraceLibrary.potrace_trace(new Param(), bitmap);

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
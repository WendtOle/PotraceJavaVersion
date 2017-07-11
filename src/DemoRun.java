import AdditionalCode.Input.JsonEncoder;
import AdditionalCode.OutputGraphical.Plotter;
import AdditionalCode.OutputGraphical.PlotterOptionsEnum;
import Potrace.General.Bitmap;
import Potrace.General.Param;
import Potrace.General.Path;
import Potrace.General.PotraceLibrary;

/**
 * Created by andreydelany on 21.06.17.
 */
public class DemoRun {
    static String bitmapFileName = "01.json";
    static String bitMapFileFolder = "testPictures";
    static Bitmap bitmap;
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
       JsonEncoder encoder = new JsonEncoder(bitmapFileName,bitMapFileFolder);
       bitmap = encoder.getBitmap();
   }
}
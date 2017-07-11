import AdditionalCode.FileInputOutput.BitmapImporter;
import AdditionalCode.FileInputOutput.JsonEncoder;
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
    static String jsonBitmapFileName = "01.json";
    static String bitmapFileName = "testPictureChildsAndSiblings.png";
    static String bitMapFileFolder = "testPictures";
    static Bitmap bitmap;
    static Path path;

    public static void main(String args[]) {
        loadBitmapFromJson();
        //loadBitmapFromPictureFile();

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

   private static void loadBitmapFromJson() {
       JsonEncoder encoder = new JsonEncoder(jsonBitmapFileName,bitMapFileFolder);
       bitmap = encoder.getBitmap();
   }

    private static void loadBitmapFromPictureFile() {
        BitmapImporter bitmapImporter = new BitmapImporter(bitmapFileName,bitMapFileFolder);
        bitmap = bitmapImporter.getBitmap();
    }
}
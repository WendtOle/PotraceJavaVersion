package refactored;

import AdditionalCode.Bitmap;

/**
 * Created by andreydelany on 21.06.17.
 */
public class DemoRun {
    static String bitmapFileName = "01.json";
    static String bitMapFileFolder = "testPictures";
    static Bitmap bitmap;
    static AdditionalCode.Path path;
/*
    public static void main(String args[]) {
        loadBitmap();

        Path originalPath = PotraceLib.potrace_trace(new Param(), BitmapTranslater.translateBitmapForRefactoredCode(bitmap));
        path = PathTranslator.refactoredPathToGeneralPath(originalPath);

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
   }*/
}
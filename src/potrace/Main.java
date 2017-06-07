package potrace;
import Tools.*;

public class Main {
    public static void main(String [] args){
        bitmap bitmap = BitmapImporter.importBitmap("01.bmp","testPictures");

        path path = potraceLib.potrace_trace(new param(),bitmap);

        PolygonArchitecturePrinter printer = new PolygonArchitecturePrinter(path);
        printer.print();

        CurvePrinter curvePrinter = new CurvePrinter(path);
        curvePrinter.print();

        Plotter plotter = new Plotter();
        //plotter.showPathAndBitmap(path,bitmap);
        //plotter.showPath(path);
    }


}

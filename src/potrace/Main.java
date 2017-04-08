package potrace;
import Tools.*;

/**
 * Created by andreydelany on 06/03/2017.
 */
public class Main {
    public static void main(String [] args){
        potrace_bitmap testBitmap = Importer.importBitmap("test.bmp");
        potrace_path result = PotraceLib.potrace_trace(new potrace_param(),testBitmap);

        Plotter plotter = new Plotter();
        plotter.showPathAndBitmap(result,testBitmap);

        PolygonArchitecturePrinter printer = new PolygonArchitecturePrinter(result);
        printer.print();
        BitmapPrinter printer1 = new BitmapPrinter(testBitmap);
        printer1.print();
    }
}

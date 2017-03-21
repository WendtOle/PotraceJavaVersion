package potrace;
import Tools.*;
import sun.awt.geom.Curve;

/**
 * Created by andreydelany on 06/03/2017.
 */
public class Main {
    public static void main(String [] args) {
        potrace_param param = new potrace_param();
        potrace_bitmap bm = potrace_bitmap.default_bitmap_Ten();

        BitmapPrinter bitmapPrinter = new BitmapPrinter(bm);
        bitmapPrinter.print();

        long startTime = System.currentTimeMillis();
        potrace_path result = PotraceLib.potrace_trace(param,bm);
        long  endTime = System.currentTimeMillis();

        System.out.println("Excecution needed: " + (endTime - startTime) + "ms");

        PolygonArchitecturePrinter polygonPrinter = new PolygonArchitecturePrinter(result);
        polygonPrinter.print();

        CurvePrinter curvePrinter = new CurvePrinter(result);
        curvePrinter.print();
    }
}

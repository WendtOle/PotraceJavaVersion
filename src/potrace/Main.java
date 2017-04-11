package potrace;
import Tools.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by andreydelany on 06/03/2017.
 */
public class Main {
    public static void main(String [] args){
        potrace_bitmap bitmap = BitmapImporter.importBitmap("errorBitmap07","error");
        potrace_path result = PotraceLib.potrace_trace(new potrace_param(),bitmap);
        Plotter plotter = new Plotter();
        plotter.showPathAndBitmap(result,bitmap);
        //plotter.showPath(result);


        PolygonArchitecturePrinter printer = new PolygonArchitecturePrinter(result);
        printer.print();

    }


}

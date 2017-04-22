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
        potrace_bitmap bitmap = BitmapImporter.importBitmap("03.jpg","testPictures");
        potrace_path path = PotraceLib.potrace_trace(new potrace_param(),bitmap);
        PolygonArchitecturePrinter printer = new PolygonArchitecturePrinter(path);
        printer.print();
    }


}

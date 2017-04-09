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
        potrace_bitmap bitmap = BitmapImporter.importBitmap("errorBitmap17","error");
        potrace_path result = PotraceLib.potrace_trace(new potrace_param(),bitmap);
        Plotter plotter = new Plotter("Plotter",1000,800,9);
        plotter.showPathAndBitmap(result,bitmap);
    }


}

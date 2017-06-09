package Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import potrace.*;

public class BitmapImporter {

    public static bitmap importBitmap(String filename, String folderName){
        try {
            File file = new File(System.getProperty("user.dir") + File.separator + folderName + File.separator + filename);
            BufferedImage image = ImageIO.read(file);
            return copyValues(image);
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    private static bitmap copyValues(BufferedImage image) {
        bitmap bitmap = new bitmap(image.getWidth(), image.getHeight());
        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {
                if ((image.getRGB(x, y) & 0xff) == 0)
                    bitmap.BM_PUT(bitmap, x, image.getHeight() - y - 1, true);
            }
        return bitmap;
    }
}
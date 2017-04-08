package Tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import potrace.*;

/**
 * Created by andreydelany on 06/04/2017.
 */
public class BitmapImporter {

    String folderName;

    public BitmapImporter (String folderName) {
        this.folderName = folderName;
    }

    public ArrayList<potrace_bitmap> importAllBitmaps() {
        ArrayList<potrace_bitmap> bitmaps = new ArrayList<>();
        try {
            File[] bitmapFiles = new File(System.getProperty("user.dir") + File.separator + folderName).listFiles();
            for (int i = 0; i < bitmapFiles.length; i++) {
                BufferedImage image = ImageIO.read(bitmapFiles[i]);
                bitmaps.add(copyValues(image));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return bitmaps;
    }

    public static potrace_bitmap importBitmap(String name){
        try {
            BufferedImage image = ImageIO.read(new File(name));
            return copyValues(image);
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    private static BetterBitmap copyValues(BufferedImage image) {
        BetterBitmap bitmap = new BetterBitmap(image.getWidth(),image.getHeight());
        for(int y = 0; y < image.getHeight(); y ++)
            for(int x = 0; x < image.getWidth(); x ++) {
                if ((image.getRGB(x, y) & 0xff) == 0)
                    bitmap.addBlob(new Point(x,image.getHeight() - y),true);
            }
        return bitmap;
    }

}

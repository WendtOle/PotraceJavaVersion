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

    public ArrayList<bitmap> importAllBitmaps() {
        ArrayList<bitmap> bitmaps = new ArrayList<>();
        try {
            File[] bitmapFiles = getCurrentNumberOfFilesInFolder();

            for (int i = 0; i < bitmapFiles.length; i++) {
                BufferedImage image = ImageIO.read(bitmapFiles[i]);
                bitmaps.add(copyValues(image));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return bitmaps;
    }

    public File[] getCurrentNumberOfFilesInFolder(){
        return new File(getBitMapFolderPath()).listFiles((dir, name) -> {
                    return name.toLowerCase().endsWith(".bmp");
                }
        );
        //http://stackoverflow.com/questions/5751335/using-file-listfiles-with-filenameextensionfilter 19.46 - 08.04.2017
    }

    public String getBitMapFolderPath() {
        return System.getProperty("user.dir") + File.separator + folderName;
    }

    public static bitmap importBitmap(String name){
        try {
            BufferedImage image = ImageIO.read(new File(name));
            return copyValues(image);
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public static bitmap importBitmap(String filename, String folderName){
        try {
            BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir") + File.separator + folderName + File.separator + filename));
            return copyValues(image);
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    private static bitmap copyValues(BufferedImage image) {
        bitmap bitmap = new bitmap(image.getWidth(),image.getHeight());
        for(int y = 0; y < image.getHeight(); y ++)
            for(int x = 0; x < image.getWidth(); x ++) {
                if ((image.getRGB(x, y) & 0xff) == 0)
                    bitmap.BM_PUT(bitmap,x, image.getHeight() - y -1,true);
            }
        return bitmap;
    }




}

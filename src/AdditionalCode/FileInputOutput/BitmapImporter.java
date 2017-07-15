package AdditionalCode.FileInputOutput;

import Potrace.General.Bitmap;
import Potrace.General.BitmapManipulator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BitmapImporter {
    Bitmap bitmap;
    String folderName;

    public BitmapImporter(String folderName){
        this.folderName = folderName;
    }

    public Bitmap getBitmap(String fileName){
        File bitmapFile = new File(System.getProperty("user.dir") + File.separator + folderName + File.separator + fileName);
        BufferedImage image = loadImage(bitmapFile);
        return getAsBitmap(image);
    }

    public Bitmap[] getAllBitmaps(){
        File[] bitmapFiles = getAllBitmapFilesForTesting(folderName);
        Bitmap[] bitmaps = new Bitmap[bitmapFiles.length];
        for (int bitmapIndex = 0; bitmapIndex < bitmapFiles.length; bitmapIndex ++) {
            bitmaps[bitmapIndex] = getAsBitmap(loadImage(bitmapFiles[bitmapIndex]));
        }
        return bitmaps;
    }

    private static File[] getAllBitmapFilesForTesting(String folderNameOfTestPictures) {
        return new File(folderNameOfTestPictures).listFiles((dir, name) -> {
                    return name.toLowerCase().endsWith(".png") ||
                            name.toLowerCase().endsWith(".jpg") ||
                            name.toLowerCase().endsWith(".gif") ||
                            name.toLowerCase().endsWith(".bmp");
                }
        );
    }

    private BufferedImage loadImage(File bitmapFile) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(bitmapFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private Bitmap getAsBitmap(BufferedImage image) {
        bitmap = new Bitmap(image.getWidth(),image.getHeight());
        copyPixelToBitmap(image);
        return bitmap;
    }

    private void copyPixelToBitmap(BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++)
                setPixel(image, new Point(x,y));
    }

    private void setPixel(BufferedImage image, Point pixel) {
        if ((image.getRGB(pixel.x, pixel.y) & 0xff) < 128)
            BitmapManipulator.BM_PUT(bitmap, pixel.x, image.getHeight() - pixel.y - 1, true);
    }
}
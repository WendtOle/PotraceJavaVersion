package AdditionalCode.FileInputOutput;

import Potrace.General.Bitmap;
import Potrace.General.BitmapManipulator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BitmapImporter {
    File bitmapFile;
    Bitmap bitmap;

    public BitmapImporter(String filename, String folderName) {
        bitmapFile = new File(System.getProperty("user.dir") + File.separator + folderName + File.separator + filename);
    }

    public Bitmap getBitmap(){
        BufferedImage image = loadImage();
        return getAsBitmap(image);
    }

    private BufferedImage loadImage() {
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
        if ((image.getRGB(pixel.x, pixel.y) & 0xff) == 0)
            BitmapManipulator.BM_PUT(bitmap, pixel.x, image.getHeight() - pixel.y - 1, true);
    }
}
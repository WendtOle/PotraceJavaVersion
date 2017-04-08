package Tools;

import potrace.potrace_bitmap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

/**
 * Created by andreydelany on 08/04/2017.
 */
public class BitmapExporter {

    public static void exportErrorPictures(potrace_bitmap bitmap) {

        int alreadyExistingFiles = new File(System.getProperty("user.dir") + File.separator + "error").list().length;
        try {
            BufferedImage bufferedImage = getMyImage(bitmap);
            File outputfile = new File(System.getProperty("user.dir") + File.separator + "error" + File.separator + "errorBitmap" + (alreadyExistingFiles + 1) + ".bmp");
            ImageIO.write(bufferedImage, "bmp", outputfile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void exporter(potrace_bitmap bitmap,String fileName) {
        try {
            BufferedImage bufferedImage = getMyImage(bitmap);
            File outputfile = new File(fileName + ".bmp");
            ImageIO.write(bufferedImage, "bmp", outputfile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static BufferedImage getMyImage(potrace_bitmap bitmap) {
        BufferedImage image = new BufferedImage(bitmap.w,bitmap.h,BufferedImage.TYPE_BYTE_BINARY);
        for (int y = 0; y < bitmap.h; y ++)
            for (int x = 0; x < bitmap.w; x ++){
                if (bitmap.BM_GET(x,y))
                    image.setRGB(x,y,0xff000000);
                else
                    image.setRGB(x,y,0xffffffff);
            }
        return image;
    }
}

package Tools;

import potrace.potrace_bitmap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by andreydelany on 08/04/2017.
 */
public class BitmapExporter {

    String folderName;
    String fileName;

    public BitmapExporter(String folderName, String fileName) {
        this.folderName = folderName;
        this.fileName = fileName;
    }

    public void export(potrace_bitmap bitmap) {
        int alreadyExistingFiles = getCurrentNumberOfFilesInFolder();
        try {
            BufferedImage bufferedImage = saveBitmapToFile(bitmap);
            File outputfile = new File(getBitMapPath(getIndexForNextFile()));
            ImageIO.write(bufferedImage, "bmp", outputfile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void export(potrace_bitmap bitmap,String fileName) {
        try {
            BufferedImage bufferedImage = saveBitmapToFile(bitmap);
            File outputfile = new File(fileName + ".bmp");
            ImageIO.write(bufferedImage, "bmp", outputfile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static BufferedImage saveBitmapToFile(potrace_bitmap bitmap) {
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

    public String getBitMapFolderPath() {
        return System.getProperty("user.dir") + File.separator + folderName;
    }

    public String getBitMapPath(int index) {
        return getBitMapFolderPath() + File.separator + fileName + index + ".bmp";
    }

    public int  getCurrentNumberOfFilesInFolder(){
        return new File(getBitMapFolderPath()).list().length;
    }

    public int getIndexForNextFile() {
        return getCurrentNumberOfFilesInFolder() + 1;
    }
}

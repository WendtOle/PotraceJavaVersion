package Tools;

import potrace.bitmap;

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

    public void export(bitmap bitmap) {
        try {
            BufferedImage bufferedImage = saveBitmapToFile(bitmap);
            File outputfile = new File(getBitMapPath(getIndexForNextFile()));
            ImageIO.write(bufferedImage, "bmp", outputfile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void export(bitmap bitmap, String fileName) {
        try {
            BufferedImage bufferedImage = saveBitmapToFile(bitmap);
            File outputfile = new File(fileName + ".bmp");
            ImageIO.write(bufferedImage, "bmp", outputfile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static BufferedImage saveBitmapToFile(bitmap bitmap) {
        BufferedImage image = new BufferedImage(bitmap.w,bitmap.h,BufferedImage.TYPE_BYTE_BINARY);
        for (int y = 0; y < bitmap.h; y ++)
            for (int x = 0; x < bitmap.w; x ++){
                if (bitmap.BM_GET(bitmap,x,y))
                    image.setRGB(x,y,0xff000000);
                else
                    image.setRGB(x,y,0xffffffff);
            }
        return image;
    }

    public String getBitMapFolderPath() {
        return System.getProperty("user.dir") + File.separator + folderName;
    }

    public String getBitMapPath(String index) {
        return getBitMapFolderPath() + File.separator + fileName + index + ".bmp";
    }

    public int  getCurrentNumberOfFilesInFolder(){
        return new File(getBitMapFolderPath()).listFiles((dir, name) -> {
                    return name.toLowerCase().endsWith(".bmp");
                }
        ).length;
        //http://stackoverflow.com/questions/5751335/using-file-listfiles-with-filenameextensionfilter 19.46 - 08.04.2017
    }

    public String getIndexForNextFile() {
        int numberOfFilesInFolder = getCurrentNumberOfFilesInFolder() + 1;
        if (numberOfFilesInFolder < 10)
            return "0" + numberOfFilesInFolder;
        else
            return numberOfFilesInFolder + "";
    }
}

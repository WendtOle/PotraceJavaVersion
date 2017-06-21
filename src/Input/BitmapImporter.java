package Input;

import potraceOriginal.Bitmap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BitmapImporter {

    public static Bitmap importBitmap(String filename, String folderName){
        try {
            File file = new File(System.getProperty("user.dir") + File.separator + folderName + File.separator + filename);
            BufferedImage image = ImageIO.read(file);
            return copyValues(image);
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    private static Bitmap copyValues(BufferedImage image) {
        Bitmap bitmap = new Bitmap(image.getWidth(), image.getHeight());
        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {
                if ((image.getRGB(x, y) & 0xff) == 0)
                    bitmap.BM_PUT(bitmap, x, image.getHeight() - y - 1, true);
            }
        return bitmap;
    }

    public static Bitmap importBitmapAndSaveAsJson(String fileName, String folderName) throws IOException{
        Bitmap bitmap = importBitmap(fileName,folderName);

        File[] listOfFiles = new File(folderName).listFiles((dir, name) -> {
                    return name.toLowerCase().endsWith(".txt");
                });
        int amountOfFiles = listOfFiles.length;
        String name;
        if (amountOfFiles < 10)
            name = "0"+amountOfFiles;
        else {
            name = amountOfFiles + "";
        }

        JSONDeEncoder.bitmapToJSon(bitmap,folderName, name);
        return bitmap;
    }

    public static void importBitmapForJSon(String fileName, String folderName){

    }
}
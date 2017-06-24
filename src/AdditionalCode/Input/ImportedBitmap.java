package AdditionalCode.Input;


import AdditionalCode.Bitmap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImportedBitmap extends Bitmap{

    public ImportedBitmap(String filename, String folderName){
        try {
            File file = new File(System.getProperty("user.dir") + File.separator + folderName + File.separator + filename);
            BufferedImage image = ImageIO.read(file);
            copyValues(image);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void copyValues(BufferedImage image) {
        setDimensions(image.getWidth(),image.getHeight());
        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {
                if ((image.getRGB(x, y) & 0xff) == 0)
                    Bitmap.BM_PUT(this, x, image.getHeight() - y - 1, true);
            }
    }
}
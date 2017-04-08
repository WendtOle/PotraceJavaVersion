package potrace;
import Tools.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by andreydelany on 06/03/2017.
 */
public class Main {
    public static void main(String [] args){
        System.out.println(new File(System.getProperty("user.dir") + File.separator + "error").list().length);
    }


}

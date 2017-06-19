package OutputGraphical;

import potraceOriginal.Bitmap;
import potraceOriginal.Path;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andreydelany on 08/04/2017.
 */
public class Drawer extends JPanel {

    Path path;
    Bitmap bitmap;
    int scale, height;

    public Drawer(Path path, Bitmap bitmap, int scale, int height) {
        this.path = path;
        this.bitmap = bitmap;
        this.scale = scale;
        this.height = height;
    }

    public Drawer(Path path, int scale, int height) {
        this(path,null,scale,height);
    }

    public Drawer(Bitmap bitmap, int scale, int height) {
        this(null,bitmap,scale,height);
    }

    public void paintComponent(Graphics g) {
        if(path != null) {
            DrawerPath drawedPath = new DrawerPath(path,scale, height);
            drawedPath.paintComponent(g);
        }
        if(bitmap != null) {
            DrawerBitmap drawedBitmap = new DrawerBitmap(bitmap,scale,height);
            drawedBitmap.paintComponent(g);
        }
    }
}

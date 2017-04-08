package Tools;

import potrace.potrace_bitmap;
import potrace.potrace_path;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andreydelany on 08/04/2017.
 */
public class Drawer extends JPanel {

    potrace_path path;
    potrace_bitmap bitmap;
    int scale, height;

    public Drawer(potrace_path path, potrace_bitmap bitmap, int scale, int height) {
        this.path = path;
        this.bitmap = bitmap;
        this.scale = scale;
        this.height = height;
    }

    public Drawer(potrace_path path, int scale, int height) {
        this(path,null,scale,height);
    }

    public Drawer(potrace_bitmap bitmap, int scale, int height) {
        this(null,bitmap,scale,height);
    }

    public void paintComponent(Graphics g) {
        if(path != null) {
            PathDrawer drawedPath = new PathDrawer(path,scale, height);
            drawedPath.paintComponent(g);
        }
        if(bitmap != null) {
            BitmapDrawer drawedBitmap = new BitmapDrawer(bitmap,scale,height);
            drawedBitmap.paintComponent(g);
        }
    }
}

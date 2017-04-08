package Tools;

import potrace.potrace_bitmap;
import potrace.potrace_path;

import javax.swing.*;

/**
 * Created by andreydelany on 08/04/2017.
 */
public class Plotter {
    JFrame jFrame;
    static final int SCALE = 40;
    static final int standartWidth = 1000;
    static final int standartHeight = 800;

    public Plotter() {
        this("Plotter", standartWidth, standartHeight );
    }

    public Plotter(String name, int width, int height){
        jFrame = new JFrame(name);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(width,height);
    }

    /*public void showPath(potrace_path path) {
        PathDrawer drawedPath = new PathDrawer(path,SCALE, jFrame.getHeight());
        jFrame.add(drawedPath);
        jFrame.setVisible(true);
    }

    public void showBitmap(potrace_path path) {
        BitmapDrawer drawedBitmap = new BitmapDrawer(path,SCALE,jFrame.getHeight());
        jFrame.add(drawedBitmap);
        jFrame.setVisible(true);
    }
*/
    public void showPathAndBitmap(potrace_path path, potrace_bitmap bitmap) {
        Drawer drawer = new Drawer(path,bitmap,SCALE, jFrame.getHeight());
        jFrame.add(drawer);
        jFrame.setVisible(true);
    }
}

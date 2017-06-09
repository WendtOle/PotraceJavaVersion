package Output;

import potrace.bitmap;
import potrace.path;

import javax.swing.*;

/**
 * Created by andreydelany on 08/04/2017.
 */
public class Plotter {
    JFrame jFrame;
    int scale;
    static final int standartScale = 40;
    static final int standartWidth = 1000;
    static final int standartHeight = 800;

    public Plotter() {
        this("Plotter", standartWidth, standartHeight,standartScale);
    }

    public Plotter(String name, int width, int height,int scale){
        this.scale = scale;
        jFrame = new JFrame(name);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(width,height);
    };

    public void showPath(path path) {
        Drawer drawer = new Drawer(path,scale, jFrame.getHeight());
        jFrame.add(drawer);
        jFrame.setVisible(true);
    }

    public void showBitmap(bitmap bitmap) {
        Drawer drawer = new Drawer(bitmap,scale, jFrame.getHeight());
        jFrame.add(drawer);
        jFrame.setVisible(true);
    }

    public void showPathAndBitmap(path path, bitmap bitmap) {
        Drawer drawer = new Drawer(path,bitmap,scale, jFrame.getHeight());
        jFrame.add(drawer);
        jFrame.setVisible(true);
    }
}

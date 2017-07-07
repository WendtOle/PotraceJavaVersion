package AdditionalCode.OutputGraphical;

import General.BitmapInterface;
import General.Path;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andreydelany on 08/04/2017.
 */
public class Plotter {
    JFrame jFrame;
    Path path;
    PlotterOptionsEnum option;
    double scale;

    static Dimension dimensionsOfWindow = new Dimension(1000,800);
    static int boarderHorizontally = 1;
    static int boarderVertically = 23;


    public Plotter(BitmapInterface bitmap, Path path, PlotterOptionsEnum option){
        this.path = path;
        this.option = option;
        setOptimalScale(bitmap);
        updateDimensionOfWindow(bitmap);

        jFrame = new JFrame(option.toString());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(dimensionsOfWindow);
    };

    private void setOptimalScale(BitmapInterface bitmap) {
        double scaleWidth = dimensionsOfWindow.width / bitmap.getWidth();
        double scaleHeight = dimensionsOfWindow.height / bitmap.getHeight();

        if (scaleHeight < scaleWidth) {
            this.scale = scaleHeight;
        } else {
            this.scale = scaleWidth;
        }
    }

    private void updateDimensionOfWindow(BitmapInterface bitmap) {
        dimensionsOfWindow.width = (int)(bitmap.getWidth() * scale) + boarderHorizontally;
        dimensionsOfWindow.height = (int)(bitmap.getHeight() * scale) + boarderVertically;
    }

    public void plot() {
        Drawer drawer = new Drawer(option,path,scale);
        jFrame.add(drawer);
        jFrame.setVisible(true);
    }
}

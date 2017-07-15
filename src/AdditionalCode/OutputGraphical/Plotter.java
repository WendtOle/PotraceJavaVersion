package AdditionalCode.OutputGraphical;

import Potrace.General.Bitmap;
import Potrace.General.Path;

import javax.swing.*;
import java.awt.*;

public class Plotter {
    JFrame jFrame;
    Path path;
    PlotterOptionsEnum option;
    double scale;

    static Dimension dimensionsOfWindow = new Dimension(1000,800);
    static int boarderHorizontally = 1;
    static int boarderVertically = 23;


    public Plotter(Bitmap bitmap, Path path, PlotterOptionsEnum option){
        this.path = path;
        this.option = option;
        setOptimalScale(bitmap);
        updateDimensionOfWindow(bitmap);

        jFrame = new JFrame(option.toString());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(dimensionsOfWindow);
    };

    private void setOptimalScale(Bitmap bitmap) {
        double scaleWidth = (double)dimensionsOfWindow.width / bitmap.w;
        double scaleHeight = (double)dimensionsOfWindow.height / bitmap.h;

        if (scaleHeight < scaleWidth) {
            this.scale = scaleHeight;
        } else {
            this.scale = scaleWidth;
        }
    }

    private void updateDimensionOfWindow(Bitmap bitmap) {
        dimensionsOfWindow.width = (int)(bitmap.w * scale) + boarderHorizontally;
        dimensionsOfWindow.height = (int)(bitmap.h * scale) + boarderVertically;
    }

    public void plot() {
        Drawer drawer = new Drawer(option,path,scale);
        jFrame.add(drawer);
        jFrame.setVisible(true);
    }
}

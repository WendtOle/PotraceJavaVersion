package AdditionalCode.OutputGraphical;


import Potrace.General.*;

import javax.swing.*;
import java.awt.*;

public class Drawer extends JPanel {

    Path path;
    int height;
    double scale;
    PlotterOptionsEnum option;

    public Drawer(PlotterOptionsEnum option, Path path, double scale,int height) {
        this.path = path;
        this.option = option;
        this.scale = scale;
        this.height = height;
    }

    public void paintComponent(Graphics g) {
        if(option == PlotterOptionsEnum.BITMAP) {
            DrawerBitmap drawedBitmap = new DrawerBitmap(path,scale,option,height);
            drawedBitmap.paintComponent(g);
        }
        if(option == PlotterOptionsEnum.PATH) {
            DrawerPath drawedPath = new DrawerPath(path,scale,option,height);
            drawedPath.paintComponent(g);
        }
        if (option == PlotterOptionsEnum.BOTH) {
            DrawerBitmap drawedBitmap = new DrawerBitmap(path,scale,option,height);
            drawedBitmap.paintComponent(g);

            DrawerPath drawedPath = new DrawerPath(path,scale,option,height);
            drawedPath.paintComponent(g);
        }
    }
}

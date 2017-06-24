package AdditionalCode.OutputGraphical;

import potraceOriginal.Path;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andreydelany on 08/04/2017.
 */
public class Drawer extends JPanel {

    Path path;

    double scale;
    PlotterOptionsEnum option;

    public Drawer(PlotterOptionsEnum option, Path path, double scale) {
        this.path = path;
        this.option = option;
        this.scale = scale;
    }

    public void paintComponent(Graphics g) {
        if(option == PlotterOptionsEnum.BITMAP) {
            DrawerBitmap drawedBitmap = new DrawerBitmap(path,scale,option);
            drawedBitmap.paintComponent(g);
        }
        if(option == PlotterOptionsEnum.PATH) {
            DrawerPath drawedPath = new DrawerPath(path,scale,option);
            drawedPath.paintComponent(g);
        }
        if (option == PlotterOptionsEnum.BOTH) {
            DrawerBitmap drawedBitmap = new DrawerBitmap(path,scale,option);
            drawedBitmap.paintComponent(g);

            DrawerPath drawedPath = new DrawerPath(path,scale,option);
            drawedPath.paintComponent(g);
        }
    }
}

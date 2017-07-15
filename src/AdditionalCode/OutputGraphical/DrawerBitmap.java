package AdditionalCode.OutputGraphical;

import Potrace.General.*;

import java.awt.*;

public class DrawerBitmap {
        Path path;
        Graphics2D graphics;
        double scale;
        PlotterOptionsEnum option;

        public DrawerBitmap(Path path, double scale, PlotterOptionsEnum option)  {
            this.path = path;
            this.scale = scale;
            this.option = option;
        }

        public void paintComponent(Graphics g){
            graphics = (Graphics2D) g;
            drawPathes(path);
        }

        private void drawPathes(Path startPath){
            Path currentPath = startPath;
            while (currentPath != null) {
                drawPathAsPolygon(currentPath);
                currentPath = currentPath.next;
            }
        }

        private void drawPathAsPolygon(Path path) {
            int amountOfPoints = path.priv.len;
            int xpoints[] = new int[amountOfPoints];
            int ypoints[] = new int[amountOfPoints];
            for (int i = 0; i < amountOfPoints; i++) {
                Point currentPoint = path.priv.pt[i];
                xpoints[i] = scaleCoordinate(currentPoint.x);
                ypoints[i] = scaleCoordinate(currentPoint.y);
            }
            Color black;
            if(option == PlotterOptionsEnum.BOTH) {
                black = new Color(50,50,50);
            } else {
                black = Color.BLACK;
            }

            if (path.sign == 43) {
                graphics.setColor(black);
                graphics.fillPolygon(xpoints,ypoints,amountOfPoints);
            } else {
                Color transparentWhite = new Color(238,238,238);
                graphics.setColor(transparentWhite);
                graphics.fillPolygon(xpoints,ypoints,amountOfPoints);
            }
        }

        private int scaleCoordinate(int coordinate) {return (int)(coordinate * scale);}
}

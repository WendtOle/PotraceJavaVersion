package AdditionalCode.OutputGraphical;

import General.*;

import java.awt.*;

/**
 * Created by andreydelany on 08/04/2017.
 */
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
                //drawPath(currentPath);
                drawPathAsPolygon(currentPath);
                currentPath = currentPath.next;
            }
        }

        private void drawPath(Path path) {
            Point startPoint = path.priv.pt[path.priv.len - 1];
            for (int i = 0; i < path.priv.len; i++) {
                Point currentPoint = path.priv.pt[i];
                drawLine(startPoint,currentPoint);
                startPoint = currentPoint;
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

        private void drawLine(Point startIn, Point endIn){
            Point start = scalePoint(startIn);
            Point end = scalePoint(endIn);
            graphics.drawLine(start.x,start.y,end.x,end.y);
        };

        private Point scalePoint(Point point){
            return new Point((int)(point.x * scale), (int)(point.y * scale));
        }

        private int scaleCoordinate(int coordinate) {return (int)(coordinate * scale);}
}

package OutputGraphical;

import potraceOriginal.*;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by andreydelany on 08/04/2017.
 */
public class DrawerBitmap {
        Path path;
        Graphics2D graphics;
        int scale, height;

        public DrawerBitmap(Bitmap bitmap, int skalierung, int height)  { //Fixme: to use height, is not good
            this.path = Decompose.bm_to_pathlist(bitmap, new Param());
            this.scale = skalierung;
            this.height = height;
        }

        public void paintComponent(Graphics g){
            graphics = (Graphics2D) g;
            flipImageOnYAxis();
            drawPathes(path);
        }

        public void flipImageOnYAxis() {
            AffineTransform flipVertically = new AffineTransform();
            flipVertically.scale(1,-1);
            flipVertically.translate(0,-height);
            graphics.setTransform(flipVertically);
        }

        private void drawPathes(Path startPath){
            Path currentPath = startPath;
            while (currentPath != null) {
                drawPath(currentPath);
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

        private DPoint getStartPointOfCurrentCurve(DPoint[][] curvesOfPath){
            int indexOfLastCurve = curvesOfPath.length - 1;
            DPoint startPoint = curvesOfPath[indexOfLastCurve][2];
            return startPoint;
        }

        private void drawLine(Point startIn, Point endIn){
            Point start = scalePoint(startIn);
            Point end = scalePoint(endIn);
            graphics.drawLine(start.x,start.y,end.x,end.y);
        };

        private Point scalePoint(Point point){
            return new Point(point.x * scale, point.y * scale);
        }
}

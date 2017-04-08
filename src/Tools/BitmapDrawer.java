package Tools;

import potrace.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;

/**
 * Created by andreydelany on 08/04/2017.
 */
public class BitmapDrawer{
        potrace_path path;
        Graphics2D graphics;
        int scale, height;

        public BitmapDrawer(potrace_bitmap bitmap, int skalierung, int height)  { //Fixme: to use height, is not good
            this.path = findOriginalBitmap(bitmap);
            this.scale = skalierung;
            this.height = height;
        }

        private potrace_path findOriginalBitmap(potrace_bitmap bitmap){
            potrace_param param = new potrace_param();
            int x;
            int y;
            potrace_path p;
            potrace_path plist = null;  // linked potrace.list of path objects
            //potrace.potrace_path plist_hook = null;  // used to speed up appending to linked potrace.list
            potrace_bitmap bm1 = bitmap.bm_dup();
            int sign;

            //be sure the byte padding on the right is set to 0, as the fast
            //pixel search below relies on it
            bm1.bm_clearexcess();

            // iterate through components
            x = 0;
            y = bm1.h - 1;
            Point xy = new Point(x,y);
            while ((xy = decompose.findnext(bm1,xy)) != null) {
                // calculate the sign by looking at the original bitmap, bm1 wird immer wieder invertiert nachdem ein pfad entfernt wurde.
                // mit dem nachgucken nach dem sign in der original bitmap bekommt einen eindruck darüber ob es ein wirklicher pfad ist oder nur der ausschnitt von einen pfad, also das innnere
                sign = bitmap.BM_GET(xy.x, xy.y) ? '+' : '-';

                // calculate the path
                p = decompose.findpath(bm1, xy.x, xy.y+1, sign, param.turnpolicy);

                // update buffered image
                bm1 = decompose.xor_path(bm1, p);

                // if it's a turd, eliminate it, else append it to the potrace.list
                if (p.area > param.turdsize) {

                    //TODO Originally it was made with a plist_hook, with which it was easier and faster to append a element at the end of the linkedlist
                    plist = list.unefficient_list_insert_beforehook(p,plist);
                }
            }
            return plist;
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

        private void drawPathes(potrace_path startPath){
            potrace_path currentPath = startPath;
            while (currentPath != null) {
                drawPath(currentPath);
                currentPath = currentPath.next;
            }
        }

        private void drawPath(potrace_path path) {
            Point startPoint = path.priv.pt[path.priv.len - 1];
            for (int i = 0; i < path.priv.len; i++) {
                Point currentPoint = path.priv.pt[i];
                drawLine(startPoint,currentPoint);
                startPoint = currentPoint;
            }
        }

        private potrace_dpoint getStartPointOfCurrentCurve(potrace_dpoint[][] curvesOfPath){
            int indexOfLastCurve = curvesOfPath.length - 1;
            potrace_dpoint startPoint = curvesOfPath[indexOfLastCurve][2];
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

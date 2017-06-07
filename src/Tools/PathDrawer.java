package Tools;

import potrace.dpoint;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;

/**
 * Created by andreydelany on 06/04/2017.
 */
public class PathDrawer{

    potrace.path path;
    Graphics2D graphics;
    int scale, height;

    public PathDrawer(potrace.path path, int skalierung, int height) { //Fixme: to use height, is not good

        this.path = path;
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

    private void drawPathes(potrace.path startPath){
        potrace.path currentPath = startPath;
        while (currentPath != null) {
            drawPath(currentPath);
            currentPath = currentPath.next;
        }
    }

    private void drawPath(potrace.path path) {
        dpoint startPointForNextCorner = getStartPointOfCurrentCurve(path.curve.c);
        for (int i = 0; i < path.curve.n; i++) {
            dpoint[] pointsOfCurrentCorner = path.curve.c[i];
            if (isStraightCorner(path.curve.tag[i]))
                startPointForNextCorner = drawStraightCorner(startPointForNextCorner, pointsOfCurrentCorner);
            else
                startPointForNextCorner = drawRoundCorner(startPointForNextCorner, pointsOfCurrentCorner);
        }
    }

    private dpoint getStartPointOfCurrentCurve(dpoint[][] curvesOfPath){
        int indexOfLastCurve = curvesOfPath.length - 1;
        dpoint startPoint = curvesOfPath[indexOfLastCurve][2];
        return startPoint;
    }


    private boolean isStraightCorner(int identifier){
        return identifier == 2 ? true : false; //2 straight, 1 round
    }

    private dpoint drawStraightCorner(dpoint C, dpoint[] pointsOfCorner){ //Angle ABC -> clockwise
        dpoint B = pointsOfCorner[1];
        dpoint A = pointsOfCorner[2];
        drawLine(C,B);
        drawLine(B,A);
        return A;
    }

    private dpoint drawRoundCorner(dpoint P0, dpoint[] pointsOfCorner) { //PO startPoint, P3 endPoint, P1 & P2 ControllPoint -> clockwise
        dpoint P1 = pointsOfCorner[0];
        dpoint P2 = pointsOfCorner[1];
        dpoint P3 = pointsOfCorner[2];
        drawBezierCurve(P0,P1,P2,P3);
        return P3;
    }

    private void drawLine(dpoint startIn, dpoint endIn){
        dpoint start = scalePoint(startIn);
        dpoint end = scalePoint(endIn);
        Shape line = new Line2D.Double(start.x,start.y,end.x,end.y);
        graphics.draw(line);
    };

    private void drawBezierCurve(dpoint p0In, dpoint p1In, dpoint p2In, dpoint p3In) {
        dpoint p0 = scalePoint(p0In);
        dpoint p1 = scalePoint(p1In);
        dpoint p2 = scalePoint(p2In);
        dpoint p3 = scalePoint(p3In);
        CubicCurve2D.Double bezierCurve = new CubicCurve2D.Double(p0.x, p0.y, p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
        graphics.draw(bezierCurve);
    }

    private dpoint scalePoint(dpoint point){
        return new dpoint(point.x * scale, point.y * scale);
    }





}

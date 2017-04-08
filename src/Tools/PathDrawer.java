package Tools;

import potrace.potrace_dpoint;
import potrace.potrace_path;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;

/**
 * Created by andreydelany on 06/04/2017.
 */
public class PathDrawer{

    potrace_path path;
    Graphics2D graphics;
    int scale, height;

    public PathDrawer(potrace_path path, int skalierung, int height) { //Fixme: to use height, is not good

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

    private void drawPathes(potrace_path startPath){
        potrace_path currentPath = startPath;
        while (currentPath != null) {
            drawPath(currentPath);
            currentPath = currentPath.next;
        }
    }

    private void drawPath(potrace_path path) {
        potrace_dpoint startPointForNextCorner = getStartPointOfCurrentCurve(path.curve.c);
        for (int i = 0; i < path.curve.n; i++) {
            potrace_dpoint[] pointsOfCurrentCorner = path.curve.c[i];
            if (isStraightCorner(path.curve.tag[i]))
                startPointForNextCorner = drawStraightCorner(startPointForNextCorner, pointsOfCurrentCorner);
            else
                startPointForNextCorner = drawRoundCorner(startPointForNextCorner, pointsOfCurrentCorner);
        }
    }

    private potrace_dpoint getStartPointOfCurrentCurve(potrace_dpoint[][] curvesOfPath){
        int indexOfLastCurve = curvesOfPath.length - 1;
        potrace_dpoint startPoint = curvesOfPath[indexOfLastCurve][2];
        return startPoint;
    }


    private boolean isStraightCorner(int identifier){
        return identifier == 2 ? true : false; //2 straight, 1 round
    }

    private potrace_dpoint drawStraightCorner(potrace_dpoint C, potrace_dpoint[] pointsOfCorner){ //Angle ABC -> clockwise
        potrace_dpoint B = pointsOfCorner[1];
        potrace_dpoint A = pointsOfCorner[2];
        drawLine(C,B);
        drawLine(B,A);
        return A;
    }

    private potrace_dpoint drawRoundCorner(potrace_dpoint P0, potrace_dpoint[] pointsOfCorner) { //PO startPoint, P3 endPoint, P1 & P2 ControllPoint -> clockwise
        potrace_dpoint P1 = pointsOfCorner[0];
        potrace_dpoint P2 = pointsOfCorner[1];
        potrace_dpoint P3 = pointsOfCorner[2];
        drawBezierCurve(P0,P1,P2,P3);
        return P3;
    }

    private void drawLine(potrace_dpoint startIn, potrace_dpoint endIn){
        potrace_dpoint start = scalePoint(startIn);
        potrace_dpoint end = scalePoint(endIn);
        Shape line = new Line2D.Double(start.x,start.y,end.x,end.y);
        graphics.draw(line);
    };

    private void drawBezierCurve(potrace_dpoint p0In, potrace_dpoint p1In, potrace_dpoint p2In, potrace_dpoint p3In) {
        potrace_dpoint p0 = scalePoint(p0In);
        potrace_dpoint p1 = scalePoint(p1In);
        potrace_dpoint p2 = scalePoint(p2In);
        potrace_dpoint p3 = scalePoint(p3In);
        CubicCurve2D.Double bezierCurve = new CubicCurve2D.Double(p0.x, p0.y, p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
        graphics.draw(bezierCurve);
    }

    private potrace_dpoint scalePoint(potrace_dpoint point){
        return new potrace_dpoint(point.x * scale, point.y * scale);
    }





}

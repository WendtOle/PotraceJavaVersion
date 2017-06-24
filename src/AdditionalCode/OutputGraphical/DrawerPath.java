package AdditionalCode.OutputGraphical;

import AdditionalCode.Path;

import java.awt.*;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Created by andreydelany on 06/04/2017.
 */
public class DrawerPath {

    Path path;
    Graphics2D graphics;
    double scale;
    PlotterOptionsEnum option;

    public DrawerPath(Path path, double scale, PlotterOptionsEnum option) {
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
            drawShape(currentPath);
            currentPath = currentPath.next;
        }
    }

    private void drawPath(Path path) {
        Point2D.Double startPointForNextCorner = getStartPointOfCurrentCurve(path.curve.c);
        for (int i = 0; i < path.curve.n; i++) {
            Point2D.Double[] pointsOfCurrentCorner = path.curve.c[i];
            if (isStraightCorner(path.curve.tag[i]))
                startPointForNextCorner = drawStraightCorner(startPointForNextCorner, pointsOfCurrentCorner);
            else
                startPointForNextCorner = drawRoundCorner(startPointForNextCorner, pointsOfCurrentCorner);
        }
    }

    private Point2D.Double getStartPointOfCurrentCurve(Point2D.Double[][] curvesOfPath){
        int indexOfLastCurve = curvesOfPath.length - 1;
        Point2D.Double startPoint = curvesOfPath[indexOfLastCurve][2];
        return startPoint;
    }


    private boolean isStraightCorner(int identifier){
        return identifier == 2 ? true : false; //2 straight, 1 round
    }

    private Point2D.Double drawStraightCorner(Point2D.Double C, Point2D.Double[] pointsOfCorner){ //Angle ABC -> clockwise
        Point2D.Double B = pointsOfCorner[1];
        Point2D.Double A = pointsOfCorner[2];
        drawLine(C,B);
        drawLine(B,A);
        return A;
    }

    private void drawStraightCorner(Point2D.Double[] pointsOfCorner,GeneralPath path){ //Angle ABC -> clockwise
        Point2D.Double B = pointsOfCorner[1];
        Point2D.Double A = pointsOfCorner[2];
        path.lineTo(scaleCoordinate(B.x),scaleCoordinate(B.y));
        path.lineTo(scaleCoordinate(A.x),scaleCoordinate(A.y));
    }

    private Point2D.Double drawRoundCorner(Point2D.Double P0, Point2D.Double[] pointsOfCorner) { //PO startPoint, P3 enPoint2D.Double, P1 & P2 ControllPoint -> clockwise
        Point2D.Double P1 = pointsOfCorner[0];
        Point2D.Double P2 = pointsOfCorner[1];
        Point2D.Double P3 = pointsOfCorner[2];
        drawBezierCurve(P0,P1,P2,P3);
        return P3;
    }

    private void drawRoundCorner(Point2D.Double[] pointsOfCorner,GeneralPath shape) { //PO startPoint, P3 enPoint2D.Double, P1 & P2 ControllPoint -> clockwise
        Point2D.Double P1 = pointsOfCorner[0];
        Point2D.Double P2 = pointsOfCorner[1];
        Point2D.Double P3 = pointsOfCorner[2];
        shape.curveTo(scaleCoordinate(P1.x),scaleCoordinate(P1.y),scaleCoordinate(P2.x),scaleCoordinate(P2.y),scaleCoordinate(P3.x),scaleCoordinate(P3.y));
    }

    private void drawLine(Point2D.Double startIn, Point2D.Double endIn){
        Point2D.Double start = scalePoint(startIn);
        Point2D.Double end = scalePoint(endIn);
        Shape line = new Line2D.Double(start.x,start.y,end.x,end.y);
        graphics.draw(line);
    };

    private void drawBezierCurve(Point2D.Double p0In, Point2D.Double p1In, Point2D.Double p2In, Point2D.Double p3In) {
        Point2D.Double p0 = scalePoint(p0In);
        Point2D.Double p1 = scalePoint(p1In);
        Point2D.Double p2 = scalePoint(p2In);
        Point2D.Double p3 = scalePoint(p3In);
        CubicCurve2D.Double bezierCurve = new CubicCurve2D.Double(p0.x, p0.y, p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
        graphics.draw(bezierCurve);
    }

    private Point2D.Double scalePoint(Point2D.Double point){
        return new Point2D.Double(point.x * scale, point.y * scale);
    }

    private double scaleCoordinate(double coordinate){
        return coordinate * scale;
    }

    private void drawShape(Path path) {
        Point2D.Double startPointForNextCorner = getStartPointOfCurrentCurve(path.curve.c);
        GeneralPath shape = new GeneralPath();
        shape.moveTo(scaleCoordinate(startPointForNextCorner.x),scaleCoordinate(startPointForNextCorner.y));
        for (int i = 0; i < path.curve.n; i++) {
            Point2D.Double[] pointsOfCurrentCorner = path.curve.c[i];
            if (isStraightCorner(path.curve.tag[i]))
                drawStraightCorner(pointsOfCurrentCorner,shape);
            else
                drawRoundCorner(pointsOfCurrentCorner,shape);
        }
        shape.closePath();

        if(option == PlotterOptionsEnum.BOTH) {
            graphics.setColor(Color.red);
            graphics.setStroke(new BasicStroke(2));
            graphics.draw(shape);
        } else {
            if (path.sign == 43) {
                graphics.setColor(Color.red);
                graphics.fill(shape);
            } else {
                Color transparentWhite = new Color(238,238,238);
                graphics.setColor(transparentWhite);
                graphics.fill(shape);
            }
            graphics.setColor(Color.black);
            graphics.draw(shape);
        }


    }
}

package AdditionalCode.OutputGraphical;

import potraceOriginal.DPoint;
import potraceOriginal.Path;

import java.awt.*;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

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
        DPoint startPointForNextCorner = getStartPointOfCurrentCurve(path.curve.c);
        for (int i = 0; i < path.curve.n; i++) {
            DPoint[] pointsOfCurrentCorner = path.curve.c[i];
            if (isStraightCorner(path.curve.tag[i]))
                startPointForNextCorner = drawStraightCorner(startPointForNextCorner, pointsOfCurrentCorner);
            else
                startPointForNextCorner = drawRoundCorner(startPointForNextCorner, pointsOfCurrentCorner);
        }
    }

    private DPoint getStartPointOfCurrentCurve(DPoint[][] curvesOfPath){
        int indexOfLastCurve = curvesOfPath.length - 1;
        DPoint startPoint = curvesOfPath[indexOfLastCurve][2];
        return startPoint;
    }


    private boolean isStraightCorner(int identifier){
        return identifier == 2 ? true : false; //2 straight, 1 round
    }

    private DPoint drawStraightCorner(DPoint C, DPoint[] pointsOfCorner){ //Angle ABC -> clockwise
        DPoint B = pointsOfCorner[1];
        DPoint A = pointsOfCorner[2];
        drawLine(C,B);
        drawLine(B,A);
        return A;
    }

    private void drawStraightCorner(DPoint[] pointsOfCorner,GeneralPath path){ //Angle ABC -> clockwise
        DPoint B = pointsOfCorner[1];
        DPoint A = pointsOfCorner[2];
        path.lineTo(scaleCoordinate(B.x),scaleCoordinate(B.y));
        path.lineTo(scaleCoordinate(A.x),scaleCoordinate(A.y));
    }

    private DPoint drawRoundCorner(DPoint P0, DPoint[] pointsOfCorner) { //PO startPoint, P3 endPoint, P1 & P2 ControllPoint -> clockwise
        DPoint P1 = pointsOfCorner[0];
        DPoint P2 = pointsOfCorner[1];
        DPoint P3 = pointsOfCorner[2];
        drawBezierCurve(P0,P1,P2,P3);
        return P3;
    }

    private void drawRoundCorner(DPoint[] pointsOfCorner,GeneralPath shape) { //PO startPoint, P3 endPoint, P1 & P2 ControllPoint -> clockwise
        DPoint P1 = pointsOfCorner[0];
        DPoint P2 = pointsOfCorner[1];
        DPoint P3 = pointsOfCorner[2];
        shape.curveTo(scaleCoordinate(P1.x),scaleCoordinate(P1.y),scaleCoordinate(P2.x),scaleCoordinate(P2.y),scaleCoordinate(P3.x),scaleCoordinate(P3.y));
    }

    private void drawLine(DPoint startIn, DPoint endIn){
        DPoint start = scalePoint(startIn);
        DPoint end = scalePoint(endIn);
        Shape line = new Line2D.Double(start.x,start.y,end.x,end.y);
        graphics.draw(line);
    };

    private void drawBezierCurve(DPoint p0In, DPoint p1In, DPoint p2In, DPoint p3In) {
        DPoint p0 = scalePoint(p0In);
        DPoint p1 = scalePoint(p1In);
        DPoint p2 = scalePoint(p2In);
        DPoint p3 = scalePoint(p3In);
        CubicCurve2D.Double bezierCurve = new CubicCurve2D.Double(p0.x, p0.y, p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
        graphics.draw(bezierCurve);
    }

    private DPoint scalePoint(DPoint point){
        return new DPoint(point.x * scale, point.y * scale);
    }

    private double scaleCoordinate(double coordinate){
        return coordinate * scale;
    }

    private void drawShape(Path path) {
        DPoint startPointForNextCorner = getStartPointOfCurrentCurve(path.curve.c);
        GeneralPath shape = new GeneralPath();
        shape.moveTo(scaleCoordinate(startPointForNextCorner.x),scaleCoordinate(startPointForNextCorner.y));
        for (int i = 0; i < path.curve.n; i++) {
            DPoint[] pointsOfCurrentCorner = path.curve.c[i];
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

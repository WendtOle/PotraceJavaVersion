package AdditionalCode.OutputGraphical;

import Potrace.General.Path;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class DrawerPath {

    Path path;
    Graphics2D graphics;
    double scale;
    PlotterOptionsEnum option;
    int height;

    public DrawerPath(Path path, double scale, PlotterOptionsEnum option,int height) {
        this.path = path;
        this.scale = scale;
        this.option = option;
        this.height = height;
    }

    public void paintComponent(Graphics g){
        graphics = (Graphics2D) g;
        drawPathes(path);
    }

    private void drawPathes(Path startPath){
        Path currentPath = startPath;
        while (currentPath != null) {
            drawShape(currentPath);
            currentPath = currentPath.next;
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


    private void drawStraightCorner(Point2D.Double[] pointsOfCorner,GeneralPath path){ //Angle ABC -> clockwise
        Point2D.Double B = pointsOfCorner[1];
        Point2D.Double A = pointsOfCorner[2];
        path.lineTo(scaleCoordinate(B.x),height -scaleCoordinate(B.y));
        path.lineTo(scaleCoordinate(A.x),height -scaleCoordinate(A.y));
    }

    private void drawRoundCorner(Point2D.Double[] pointsOfCorner,GeneralPath shape) { //PO startPoint, P3 enPoint2D.Double, P1 & P2 ControllPoint -> clockwise
        Point2D.Double P1 = pointsOfCorner[0];
        Point2D.Double P2 = pointsOfCorner[1];
        Point2D.Double P3 = pointsOfCorner[2];
        shape.curveTo(scaleCoordinate(P1.x),height -scaleCoordinate(P1.y),scaleCoordinate(P2.x),height -scaleCoordinate(P2.y),scaleCoordinate(P3.x),height -scaleCoordinate(P3.y));
    }


    private double scaleCoordinate(double coordinate){
        return coordinate * scale;
    }

    private void drawShape(Path path) {
        Point2D.Double startPointForNextCorner = getStartPointOfCurrentCurve(path.curve.c);
        GeneralPath shape = new GeneralPath();
        shape.moveTo(scaleCoordinate(startPointForNextCorner.x),height - scaleCoordinate(startPointForNextCorner.y));
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

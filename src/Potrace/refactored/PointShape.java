package Potrace.refactored;

import java.awt.*;

/**
 * Created by andreydelany on 07.07.17.
 */
public class PointShape {
    Point[] pointsOfPath = new Point[1];
    int indexOfCurrentPoint = 0;
    Point currentPoint;
    int areaOfPath = 0;

    public void addPointToPointsOfPath(Point point) {
        currentPoint = point;
        extendPointArrayCapacityWhenNecessary();
        pointsOfPath[indexOfCurrentPoint]=(Point) currentPoint.clone();
        indexOfCurrentPoint++;
    }

    public Point[] getPointsOfPath() {
        return pointsOfPath;
    }

    public int getLengthOfPath() {
        return indexOfCurrentPoint;
    }

    public void updateAreaOfPath(int verticalDirection){
        areaOfPath += currentPoint.x * verticalDirection;
    }

    private void extendPointArrayCapacityWhenNecessary() {
        if (isPointArrayNotBigEnoughForAnotherPoint())
            expendPointArrayCapacity();
    }

    private boolean isPointArrayNotBigEnoughForAnotherPoint() {
        return indexOfCurrentPoint >= pointsOfPath.length;
    }

    private void expendPointArrayCapacity() { //Todo to much
        int newSize = (int)(1.3 * (pointsOfPath.length+100));
        Point[] newSizedPointArray = new Point[newSize];
        System.arraycopy(pointsOfPath,0,newSizedPointArray,0, pointsOfPath.length);
        pointsOfPath = newSizedPointArray;
    }

    public int getAreaOfPath() {
        return areaOfPath;
    }
}
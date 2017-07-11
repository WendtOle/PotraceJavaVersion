package Potrace.refactored;

import Potrace.General.*;
import java.awt.*;

public class FindPath {

    Point startPoint;   //TODO zu viele Felder
    int sign;
    TurnPolicyEnum turnPolicy;
    BitmapHandlerInterface bitmapHandler;
    PointShape pointShape;
    Point currentPoint;
    int areaOfPath = 0;
    DirectionHandler directionHandler;

    public FindPath(Bitmap bitmap, Point firstFilledPixel, int sign, TurnPolicyEnum turnPolicy) {
        initializeFields(firstFilledPixel,bitmap, sign, turnPolicy);
        findPath();
    }

    public Path getPath(){
        return new Path(areaOfPath, sign, pointShape.getLengthOfPath(), pointShape.getPointsOfPath());
    }

    private void initializeFields(Point firstFilledPixel, Bitmap bitmap, int sign, TurnPolicyEnum turnPolicy) {
        this.sign = sign;
        this.turnPolicy = turnPolicy;
        directionHandler = new DirectionHandler(bitmap,turnPolicy,sign);
        this.bitmapHandler = new BitmapHandler(bitmap);
        this.pointShape = new PointShape();
        setInitialPointPosition(firstFilledPixel);
    }

    private void setInitialPointPosition(Point firstFilledPixel) {
        this.startPoint = new Point(firstFilledPixel.x,firstFilledPixel.y + 1);
        this.currentPoint = new Point(firstFilledPixel.x,firstFilledPixel.y + 1);
    }

    private void findPath() {
        do moveInDirectionAndRememberCurrentPoint();
        while (pathIsOpen());
    }

    private void moveInDirectionAndRememberCurrentPoint() {
        determineNextDirection();
        saveCurrentLocation();
        moveToNextPoint();
        updateAreaOfPath();
    }

    private void determineNextDirection() {
        directionHandler.turnInNextDirection(currentPoint);
    }

    private void saveCurrentLocation() {
        pointShape.addPointToPointsOfPath(currentPoint);
    }

    private void moveToNextPoint() {
        currentPoint.x += directionHandler.getHorizontalDirection();
        currentPoint.y += directionHandler.getVerticalDirection();
    }

    private void updateAreaOfPath() {
        areaOfPath += currentPoint.x * directionHandler.getVerticalDirection();
    }

    private boolean pathIsOpen() {
        boolean isPathClosed = currentPoint.equals(startPoint);
        return !isPathClosed;
    }
}
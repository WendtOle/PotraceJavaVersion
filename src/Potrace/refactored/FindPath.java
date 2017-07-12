package Potrace.refactored;

import Potrace.General.*;
import java.awt.*;

public class FindPath {

    Point startPoint;   //TODO zu viele Felder
    PathKindEnum kindOfPath;
    TurnPolicyEnum turnPolicy;
    BitmapHandlerInterface bitmapHandler;
    PointShape pointShape;
    Point currentPoint;
    int areaOfPath = 0;
    DirectionHandler directionHandler;

    public FindPath(Bitmap bitmap, Point firstFilledPixel, PathKindEnum kindOfPath, TurnPolicyEnum turnPolicy) {
        initializeFields(firstFilledPixel,bitmap, kindOfPath, turnPolicy);
        findPath();
    }

    public Path getPath(){
        return new Path(areaOfPath, kindOfPath.intRepresentation, pointShape.getLengthOfPath(), pointShape.getPointsOfPath());
    }

    private void initializeFields(Point firstFilledPixel, Bitmap bitmap, PathKindEnum kindOfPath, TurnPolicyEnum turnPolicy) {
        this.kindOfPath = kindOfPath;
        this.turnPolicy = turnPolicy;
        directionHandler = new DirectionHandler(bitmap,turnPolicy,kindOfPath);
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
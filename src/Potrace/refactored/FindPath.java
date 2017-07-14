package Potrace.refactored;

import Potrace.General.*;
import java.awt.*;

public class FindPath {
    Point startPoint;
    PathKindEnum kindOfPath;
    PathShape pathShape;
    Point currentPoint;
    DirectionHandler directionHandler;

    public FindPath(Bitmap bitmap, Point firstFilledPixel,PathFindingCharacteristics pathFindingCharacteristics) {
        this.kindOfPath = pathFindingCharacteristics.kindOfPath;
        initializeHelperClasses(bitmap, pathFindingCharacteristics);
        setInitialPointPosition(firstFilledPixel);
        findPath();
    }

    public Path getPath(){
        int areaOfPath = pathShape.getAreaOfPath();
        int lengthOfPath = pathShape.getLengthOfPath();
        Point[] pointsOfPath = pathShape.getPointsOfPath();
        return new Path(areaOfPath, kindOfPath.intRepresentation, lengthOfPath, pointsOfPath);
    }

    private void initializeHelperClasses(Bitmap bitmap, PathFindingCharacteristics pathFindingCharacteristics) {
        directionHandler = new DirectionHandler(bitmap,pathFindingCharacteristics);
        pathShape = new PathShape();
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
        pathShape.addPointToPathShape(currentPoint);
    }

    private void moveToNextPoint() {
        currentPoint = directionHandler.moveInDirection();
    }

    private void updateAreaOfPath() {
        pathShape.updateAreaOfPath(directionHandler.getVerticalDirection());
    }

    private boolean pathIsOpen() {
        boolean isPathClosed = currentPoint.equals(startPoint);
        return !isPathClosed;
    }
}
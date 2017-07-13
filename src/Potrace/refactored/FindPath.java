package Potrace.refactored;

import Potrace.General.*;
import java.awt.*;

public class FindPath {
    Point startPoint;
    PathKindEnum kindOfPath;
    PointShape pointShape;
    Point currentPoint;
    DirectionHandler directionHandler;

    public FindPath(Bitmap bitmap, Point firstFilledPixel,DirectionChooserIdentificator directionIdentificators) {
        this.kindOfPath = directionIdentificators.kindOfPath;
        initializeHelperClasses(bitmap, directionIdentificators);
        setInitialPointPosition(firstFilledPixel);
        findPath();
    }

    public Path getPath(){
        int areaOfPath = pointShape.getAreaOfPath();
        int lengthOfPath = pointShape.getLengthOfPath();
        Point[] pointsOfPath = pointShape.getPointsOfPath();
        return new Path(areaOfPath, kindOfPath.intRepresentation, lengthOfPath, pointsOfPath);
    }

    private void initializeHelperClasses(Bitmap bitmap, DirectionChooserIdentificator directionIdentificators) {
        directionHandler = new DirectionHandler(bitmap,directionIdentificators);
        this.pointShape = new PointShape();
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
        currentPoint = directionHandler.moveInDirection();
    }

    private void updateAreaOfPath() {
        pointShape.updateAreaOfPath(directionHandler.getVerticalDirection());
    }

    private boolean pathIsOpen() {
        boolean isPathClosed = currentPoint.equals(startPoint);
        return !isPathClosed;
    }
}
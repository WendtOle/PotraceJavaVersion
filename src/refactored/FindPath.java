package refactored;

import General.*;
import java.awt.*;

public class FindPath {

    Point startPoint;
    Point direction = new Point(0, -1);;
    int sign;
    TurnPolicyEnum turnPolicy;
    BitmapHandlerInterface bitmapHandler;
    PointShape pointShape;
    Point currentPoint;
    int areaOfPath = 0;
    DirectionHandler directionHandler;

    public FindPath(Bitmap bitmap, Point firstFilledPixel, int sign, TurnPolicyEnum turnPolicy) {
        this.sign = sign;
        this.turnPolicy = turnPolicy;
        directionHandler = new DirectionHandler(bitmap,turnPolicy,sign);
        this.bitmapHandler = new BitmapHandler(bitmap);
        this.pointShape = new PointShape();
        setInitialPointPosition(firstFilledPixel);

        findPath();
    }

    private void setInitialPointPosition(Point firstFilledPixel) {
        this.startPoint = new Point(firstFilledPixel.x,firstFilledPixel.y + 1);
        this.currentPoint = new Point(firstFilledPixel.x,firstFilledPixel.y + 1);
    }

    public Path getPath(){
        return new Path(areaOfPath, sign, pointShape.getLengthOfPath(), pointShape.getPointsOfPath());
    }

    private void findPath() {
        do {
            moveInDirection();
        } while (pathIsOpen());
    }

    private void moveInDirection() {
        determineNewDirection();
        saveCurrentLocation();
        moveToNextPoint();
        updateAreaOfPath();
    }

    private void determineNewDirection() {
        direction = directionHandler.turnInNextDirection(currentPoint);
    }

    private void saveCurrentLocation() {
        pointShape.addPointToPointsOfPath(currentPoint);
    }

    private void moveToNextPoint() {
        currentPoint.x += direction.x;
        currentPoint.y += direction.y;
    }

    private void updateAreaOfPath() {
        areaOfPath += currentPoint.x * direction.y;
    }

    private boolean pathIsOpen() {
        boolean isPathClosed = currentPoint.equals(startPoint);
        return !isPathClosed;
    }
}
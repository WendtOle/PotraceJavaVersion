package refactored.potrace;

import java.awt.*;

public class FindPath {

    Point startPoint;
    Point direction = new Point(0, -1);;
    int sign;
    TurnPolicyEnum turnPolicy;
    Bitmap bitmap;
    Point[] points = new Point[1];
    int indexOfCurrentPoint = 0;
    Point currentPoint;
    int areaOfPath = 0;

    public FindPath(Bitmap bitmap, Point startPointOfPath, int sign, int turnPolicy) {
        this.startPoint = startPointOfPath;
        this.currentPoint = new Point(startPointOfPath.x,startPointOfPath.y);
        this.sign = sign;
        this.turnPolicy = TurnPolicyEnum.values()[turnPolicy];
        this.bitmap = bitmap;

        findPath();
    }

    private void findPath() {
        while (true) {
            addCurrentPointToPath();
            moveToNextPoint();
            updateAreaOfPath();
            if (isPathComplete())
                break;
            performTurnInNextDirection();
        }
    }

    private void addCurrentPointToPath() {
        extendPointArrayCapacityWhenNecessary();
        points[indexOfCurrentPoint] = new Point(currentPoint.x,currentPoint.y);
        indexOfCurrentPoint++;
    }

    private void extendPointArrayCapacityWhenNecessary() {
        if (indexOfCurrentPoint >= points.length) {
            expendPointArrayCapacity();
        }
    }

    private void expendPointArrayCapacity() {
        int newSize = (int)(1.3 * (points.length+100));
        Point[] newSizedPointArray = new Point[newSize];
        System.arraycopy(points,0,newSizedPointArray,0,points.length);
        points = newSizedPointArray;
    }

    private void moveToNextPoint() {
        currentPoint.x += direction.x;
        currentPoint.y += direction.y;
    }

    private void updateAreaOfPath() {
        areaOfPath += currentPoint.x * direction.y;
    }

    private boolean isPathComplete() {
        return currentPoint.equals(startPoint);
    }

    private void performTurnInNextDirection() {
        boolean isRightPixelFilled = isRightPixelFilled();
        boolean isLeftPixelFilled = isLeftPixelFilled();

        if (isAmbiguousSituation(isRightPixelFilled, isLeftPixelFilled)) {
            performTurnInAmbiguousSituation();
        } else if (isRightPixelFilled) {
            performRightTurn();
        } else if (!isLeftPixelFilled) {
            performLeftTurn();
        }
    }

    private boolean isRightPixelFilled() {
        int xComponent = currentPoint.x + (direction.x+direction.y-1)/2;
        int yCompontent = currentPoint.y + (direction.y-direction.x-1)/2;
        Point rightPixelPosition = new Point(xComponent, yCompontent);
        return bitmap.getPixelValue(rightPixelPosition.x,rightPixelPosition.y);
    }

    private boolean isLeftPixelFilled() {
        int xComponent = currentPoint.x + (direction.x - direction.y - 1) / 2;
        int yComponent = currentPoint.y + (direction.y + direction.x - 1) / 2;
        Point leftPixelPosition = new Point(xComponent, yComponent);
        return bitmap.getPixelValue(leftPixelPosition.x,leftPixelPosition.y);
    }

    private static boolean isAmbiguousSituation(boolean isRightPixelFilled, boolean isLeftPixelFilled) {
        boolean isLeftPixelEmpty = !isLeftPixelFilled;
        return isRightPixelFilled && isLeftPixelEmpty;
    }

    private void performTurnInAmbiguousSituation() {
        if (shouldTurnRightInAmbiguousSituation())
            performRightTurn();
        else
            performLeftTurn();
    }

    private boolean shouldTurnRightInAmbiguousSituation() {
        return turnPolicy.isTurnPolicySatisfied(sign,bitmap,currentPoint);
    }

    private void performRightTurn() {
        direction = new Point(direction.y,-direction.x);
    }

    private void performLeftTurn() {
        direction = new Point(- direction.y,direction.x);
    }

    public Path getPath(){
        return new Path(areaOfPath, sign, indexOfCurrentPoint, points);
    }
}
package refactored.potrace;

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

    public FindPath(Bitmap bitmap, Point firstFilledPixel, int sign, TurnPolicyEnum turnPolicy) {
        this.sign = sign;
        this.turnPolicy = turnPolicy;
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
        moveInDirection();
        while (pathIsOpen()) {
            turnInNextDirection();
            moveInDirection();
        }
    }

    private void moveInDirection() {
        pointShape.addPointToPointsOfPath(currentPoint);
        moveToNextPoint();
        updateAreaOfPath();
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

    private void turnInNextDirection() {
        boolean isRightPixelFilled = isRightPixelFilled();
        boolean isLeftPixelFilled = isLeftPixelFilled();

        performTurn(isRightPixelFilled, isLeftPixelFilled);
    }

    private boolean isRightPixelFilled() {
        int xComponent = currentPoint.x + (direction.x+direction.y-1)/2;
        int yCompontent = currentPoint.y + (direction.y-direction.x-1)/2;
        return bitmapHandler.isPixelFilled(new Point(xComponent,yCompontent));
    }

    private boolean isLeftPixelFilled() {
        int xComponent = currentPoint.x + (direction.x - direction.y - 1) / 2;
        int yComponent = currentPoint.y + (direction.y + direction.x - 1) / 2;
        return bitmapHandler.isPixelFilled(new Point(xComponent,yComponent));
    }

    private void performTurn(boolean isRightPixelFilled, boolean isLeftPixelFilled) {
        if (isAmbiguousSituation(isRightPixelFilled, isLeftPixelFilled))
            performTurnInAmbiguousSituation();
        else
            performTurnInNormalSituation(isRightPixelFilled, isLeftPixelFilled);
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
        return turnPolicy.isTurnPolicySatisfied(sign,bitmapHandler,currentPoint);
    }

    private void performRightTurn() {
        direction = new Point(direction.y,-direction.x);
    }

    private void performLeftTurn() {
        direction = new Point(- direction.y,direction.x);
    }

    private void performTurnInNormalSituation(boolean isRightPixelFilled, boolean isLeftPixelFilled) {
        if (isRightPixelFilled) {
            performRightTurn();
        } else if (!isLeftPixelFilled) {
            performLeftTurn();
        }
    }
}
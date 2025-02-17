package Potrace.refactored;

import Potrace.General.Bitmap;

import java.awt.*;

public class DirectionHandler {
    private BitmapHandlerInterface bitmapHandler;
    private Point direction = new Point(0, -1);
    private Point currentPoint;
    private TurnPolicyEnum turnPolicy;
    private boolean isRightPixelFilled;
    private boolean isLeftPixelEmpty;
    private PathKindEnum kindOfPath;

    public DirectionHandler(Bitmap bitmap, PathFindingCharacteristics pathFindingCharacteristics) {
        bitmapHandler = new BitmapHandler(bitmap);
        this.turnPolicy = pathFindingCharacteristics.turnPolicy;
        this.kindOfPath = pathFindingCharacteristics.kindOfPath;
    }

    public void turnInNextDirection(Point currentPoint) {
        gatherInformationAboutCircumstances(currentPoint);
        performTurn();
    }

    public Point moveInDirection(){
        currentPoint.x += direction.x;
        currentPoint.y += direction.y;
        return currentPoint;
    }

    public int getVerticalDirection(){
        return direction.y;
    }

    private void gatherInformationAboutCircumstances(Point currentPoint) {
        this.currentPoint = currentPoint;
        this.isRightPixelFilled = isRightPixelFilled();
        this.isLeftPixelEmpty = !isLeftPixelFilled();
    }

    private boolean isRightPixelFilled() {
        int xComponent = currentPoint.x + (direction.x+direction.y-1)/2;
        int yComponent = currentPoint.y + (direction.y-direction.x-1)/2;
        return bitmapHandler.isPixelFilled(new Point(xComponent,yComponent));
    }

    private boolean isLeftPixelFilled() {
        int xComponent = currentPoint.x + (direction.x - direction.y - 1) / 2;
        int yComponent = currentPoint.y + (direction.y + direction.x - 1) / 2;
        return bitmapHandler.isPixelFilled(new Point(xComponent,yComponent));
    }

    private void performTurn() {
        if (isAmbiguousSituation())
            performTurnInAmbiguousSituation();
        else
            performTurnInNormalSituation();
    }

    private boolean isAmbiguousSituation() {
        return isRightPixelFilled && isLeftPixelEmpty;
    }

    private void performTurnInAmbiguousSituation() {
        if (shouldTurnLeftInAmbiguousSituation())
            performLeftTurn();
        else
            performRightTurn();
    }

    private boolean shouldTurnLeftInAmbiguousSituation() {
        return turnPolicy.isTurnPolicySatisfied(kindOfPath,bitmapHandler,currentPoint);
    }

    private void performLeftTurn() {
        direction = new Point(direction.y,-direction.x);
    }

    private void performRightTurn() {
        direction = new Point(- direction.y,direction.x);
    }

    private void performTurnInNormalSituation() {
        if (isRightPixelFilled)
            performLeftTurn();
        else if (isLeftPixelEmpty)
            performRightTurn();
    }
}
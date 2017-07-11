package Potrace.refactored;

import Potrace.General.Bitmap;

import java.awt.*;

/**
 * Created by andreydelany on 07.07.17.
 */
public class DirectionHandler {
    BitmapHandlerInterface bitmapHandler;
    Point direction = new Point(0, -1);;
    Point currentPoint;
    TurnPolicyEnum turnPolicy;
    boolean isRightPixelFilled, isLeftPixelEmpty;
    int sign;

    public DirectionHandler(Bitmap bitmap, TurnPolicyEnum turnPolicy, int sign) {
        bitmapHandler = new BitmapHandler(bitmap);
        this.turnPolicy = turnPolicy;
        this.sign = sign;
    }

    public void turnInNextDirection(Point currentPoint) {
        gatherInformationAboutCircumstances(currentPoint);
        performTurn();
    }

    public int getHorizontalDirection(){
        return direction.x;
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

    private void performTurnInNormalSituation() {
        if (isRightPixelFilled)
            performRightTurn();
        else if (isLeftPixelEmpty)
            performLeftTurn();
    }
}
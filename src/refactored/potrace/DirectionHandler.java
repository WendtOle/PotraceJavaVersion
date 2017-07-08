package refactored.potrace;

import java.awt.*;

/**
 * Created by andreydelany on 07.07.17.
 */
public class DirectionHandler {
    BitmapHandlerInterface bitmapHandler;
    Point direction ;
    Point currentPoint;
    TurnPolicyEnum turnPolicy;
    boolean isRightPixelFilled, isLeftPixelFilled;
    int sign;

    public DirectionHandler(Bitmap bitmap, TurnPolicyEnum turnPolicy, int sign) {
        bitmapHandler = new BitmapHandler(bitmap);
        this.turnPolicy = turnPolicy;
        this.sign = sign;
        direction = new Point(0, -1);
    }

    public Point turnInNextDirection(Point currentPoint) {
        gatherSituationInformations(currentPoint);
        performTurn();
        return direction;
    }

    private void gatherSituationInformations(Point currentPoint) {
        this.currentPoint = currentPoint;
        this.isRightPixelFilled = isRightPixelFilled();
        this.isLeftPixelFilled = isLeftPixelFilled();
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

    private void performTurn() {
        if (isAmbiguousSituation())
            performTurnInAmbiguousSituation();
        else
            performTurnInNormalSituation();
    }

    private  boolean isAmbiguousSituation() {
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

    private void performTurnInNormalSituation() {
        if (isRightPixelFilled) {
            performRightTurn();
        } else if (!isLeftPixelFilled) {
            performLeftTurn();
        }
    }
}

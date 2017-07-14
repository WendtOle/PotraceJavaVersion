package Potrace.refactored;

import java.awt.*;

/**
 * Created by andreydelany on 29.06.17.
 */
public enum TurnPolicyEnum {
    BLACK, WHITE, LEFT, RIGHT, MINORITY, MAJORITY, RANDOM;

    FilledPixelRate ratioOfFilledPixel = new FilledPixelRate();
    Random random = new Random();

    public boolean isTurnPolicySatisfied(PathKindEnum kindOfPath, BitmapHandlerInterface bitmapHandler, Point currentPoint) {
        switch (this) {
            case RIGHT: return true;
            case BLACK: return kindOfPath.equals(PathKindEnum.POSITIV);
            case WHITE: return kindOfPath.equals(PathKindEnum.NEGATIV);
            case RANDOM: return random.getRandomBoolean(currentPoint);
            case MAJORITY: return ratioOfFilledPixel.isMajorityOfPixelFilledAtIntersection(currentPoint,bitmapHandler);
            case MINORITY: return ratioOfFilledPixel.isMinorityOfPixelFilledAtIntersection(currentPoint,bitmapHandler);
            default: return false;
        }
    }
}
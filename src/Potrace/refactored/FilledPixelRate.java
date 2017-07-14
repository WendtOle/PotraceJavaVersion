package Potrace.refactored;

import java.awt.*;

class FilledPixelRate {
    private final int startRadius = 2;
    private final int endRadius = 5;

    private int radius;
    private BitmapHandlerInterface bitmapHandler;
    private Point intersection;
    private int majorityIdentifier;

    public boolean isMinorityOfPixelFilledAtIntersection(Point intersection, BitmapHandlerInterface bitmapHandler){
        setFields(intersection, bitmapHandler);
        return !isMajorityOfPixelFilledAtIntersection(intersection,bitmapHandler);
    }

    public boolean isMajorityOfPixelFilledAtIntersection(Point intersection, BitmapHandlerInterface bitmapHandler) {
        setFields(intersection, bitmapHandler);
        return isMajorityOfPixelFilledAtIntersection();
    }

    private boolean isMajorityOfPixelFilledAtIntersection() {
        while (radius < endRadius){
            determineMajorityIdentifierAtRadius();
            if (wasMajorityIdentifierDefinite())
                return isMajorityFilled();
            radius ++;
        }
        return false;
    }

    private boolean wasMajorityIdentifierDefinite() {
        return majorityIdentifier != 0;
    }

    private void setFields(Point intersection, BitmapHandlerInterface bitmapHandler) {
        this.bitmapHandler = bitmapHandler;
        this.intersection = intersection;
        radius = startRadius;
    }

    private boolean isMajorityFilled() {
        return majorityIdentifier > 0;
    }

    private void determineMajorityIdentifierAtRadius() {
        majorityIdentifier = 0;
        for (int modifier = -radius + 1; modifier<=radius-1; modifier++)
            determineMajorityIdentifierWithRadiusAndModifier(modifier);
    }

    private  void determineMajorityIdentifierWithRadiusAndModifier(int modifier) {
        addPixelValueToMajorityIdentifier(modifier, radius - 1);
        addPixelValueToMajorityIdentifier(radius - 1,modifier - 1);
        addPixelValueToMajorityIdentifier(modifier - 1,- radius);
        addPixelValueToMajorityIdentifier(- radius,modifier);
    }

    private void addPixelValueToMajorityIdentifier(int xComponentModifier, int yComponentModifier) {
        int xComponent = intersection.x + xComponentModifier;
        int yComponent = intersection.y + yComponentModifier;
        addPixelValueToMajorityIdentifier(new Point(xComponent,yComponent));
    }

    private void addPixelValueToMajorityIdentifier(Point firstPixel) {
        if (bitmapHandler.isPixelFilled(firstPixel))
            majorityIdentifier ++;
        else
            majorityIdentifier --;
    }
}

package Potrace.refactored;

import Potrace.General.*;
import java.awt.*;

/**
 * Created by andreydelany on 02.07.17.
 */
public class PathInverter {
    RangeInverter rangeInverter;
    BitmapHandlerInterface bitmapHandler;
    private Point currentPoint;
    private Point previousPoint;
    int currentPointIdentifier;
    Path path;

    public PathInverter(Bitmap bitmap){
        this.bitmapHandler = new BitmapHandler(bitmap);
        this.rangeInverter = new RangeInverter(bitmap);
    }

    public void invertPathOnBitmap(Path path) {
        this.path = path;
        setPreviousPoint();
        invertingPathByInvertingRectangleBetweenPointsOfPath();
        currentPointIdentifier = 0;
    }

    private void setPreviousPoint() {
        int xComponent = path.priv.pt[path.priv.len-1].y;
        int yComponent = bitmapHandler.getBeginningIndexOfWordWithPixel(new Point(path.priv.pt[0]));
        previousPoint = new Point(yComponent,xComponent);
    }

    private void invertingPathByInvertingRectangleBetweenPointsOfPath() {
        while (notLookedAtAllPoints()) {
            invertRectangleBetweenCurrentAndPreviousPoint();
            currentPointIdentifier ++;
        }
    }

    private boolean notLookedAtAllPoints() {
        return currentPointIdentifier < path.priv.len;
    }

    private void invertRectangleBetweenCurrentAndPreviousPoint() {
        currentPoint = getCurrentPoint();
        if (canInvertARectangle())
            invertRectangle();
    }

    private Point getCurrentPoint() {
        return path.priv.pt[currentPointIdentifier];
    }

    private void invertRectangle() {
        rangeInverter.invertRangeInLine(currentPoint, previousPoint, getLineToInvert());
        previousPoint.y = currentPoint.y;
    }

    private boolean canInvertARectangle() {
        return currentPoint.y != previousPoint.y;
    }

    private int getLineToInvert() {
        return min(currentPoint.y, previousPoint.y);
    }

    private int min(int a, int b) {
        return (a)<(b) ? (a) : (b);
    }
}
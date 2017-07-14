package Potrace.refactored;

import Potrace.General.*;
import java.awt.*;

public class PathInverter {
    private RangeInverter rangeInverter;
    private BitmapHandlerInterface bitmapHandler;
    private Point currentPoint;
    private Point previousPoint;
    private int currentPointIdentifier;
    private Path path;

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
        int xComponent = bitmapHandler.getBeginningIndexOfWordWithPixel(new Point(path.priv.pt[0]));
        int yComponent = path.priv.pt[path.priv.len-1].y;
        previousPoint = new Point(xComponent,yComponent);
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

    private boolean canInvertARectangle() {
        return currentPoint.y != previousPoint.y;
    }

    private void invertRectangle() {
        rangeInverter.invertRangeInLine(currentPoint, previousPoint, getLineToInvert());
        previousPoint.y = currentPoint.y;
    }

    private int getLineToInvert() {
        return min(currentPoint.y, previousPoint.y);
    }

    private int min(int a, int b) {
        return (a)<(b) ? (a) : (b);
    }
}
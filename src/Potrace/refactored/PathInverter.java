package Potrace.refactored;

import Potrace.General.*;
import java.awt.*;

/**
 * Created by andreydelany on 02.07.17.
 */
public class PathInverter {
    PotraceRangeInverter lineInverter;
    BitmapHandlerInterface bitmapHandler;
    private Point currentPoint;
    private Point previousPoint;
    int currentPointIdentifier;
    Path path;

    public PathInverter(Bitmap bitmap){
        this.bitmapHandler = new BitmapHandler(bitmap);
        this.lineInverter = new PotraceRangeInverter(bitmap);
    }

    public void invertPathOnBitmap(Path path) {
        this.path = path;
        initializeInvertingProcessBySettingFixPoints();
        invertingPathByInvertingRectangleBetweenPointsOfPath();
    }

    private void initializeInvertingProcessBySettingFixPoints() {
        int verticalFixPunkt = path.priv.pt[path.priv.len-1].y;
        int horizontalFixPunkt = bitmapHandler.getBeginningIndexOfWordWithPixel(new Point(path.priv.pt[0]));
        previousPoint = new Point(horizontalFixPunkt,verticalFixPunkt);
    }

    private void invertingPathByInvertingRectangleBetweenPointsOfPath() {
        currentPointIdentifier = 0;
        while (notLookedAtAllPoints())
            invertRectangleBetweenCurrentAndPreviousePoint();
    }

    private boolean notLookedAtAllPoints() {
        return currentPointIdentifier < path.priv.len;
    }

    private void invertRectangleBetweenCurrentAndPreviousePoint() {
        currentPoint = getCurrentPoint();
        tryToInvertRectangleBetweenCurrentAndPreviousPoint();
        previousPoint.y = currentPoint.y;
        currentPointIdentifier ++;
    }

    private Point getCurrentPoint() {
        return path.priv.pt[currentPointIdentifier];
    }

    private void tryToInvertRectangleBetweenCurrentAndPreviousPoint() {
        if (canInvertALine())
            lineInverter.invertRangeInLine(currentPoint,previousPoint,getLineToInvert());
    }

    private boolean canInvertALine() {
        return currentPoint.y != previousPoint.y;
    }

    private int getLineToInvert() {
        return min(currentPoint.y, previousPoint.y);
    }

    private int min(int a, int b) {
        return (a)<(b) ? (a) : (b);
    }
}
package refactored.potrace;

import java.awt.*;

public class FindPath {

    Path path;
    Point startPoint;
    Point direction;
    int sign;
    int turnPolicy;

    public FindPath(Bitmap bitmap, Point startPointOfPath, int sign, int turnPolicy) {
        Point[] pt = new Point[1];

        this.startPoint = startPointOfPath;
        int x = startPointOfPath.x;
        int y = startPointOfPath.y;
        this.direction = new Point(0, -1);
        this.sign = sign;
        this.turnPolicy = turnPolicy;

        int len = 0;
        int area = 0;

        while (true) {
            /* add point to Path */
            if (len >= pt.length) {
                pt = expendPointArrayCapacity(pt);
            }
            pt[len] = new Point(x, y);
            len++;

            /* move to next point */
            x += direction.x;
            y += direction.y;
            area += x * direction.y;

            if (isPathComplete(new Point(x, y))) {
                break;
            }

            direction = determineNextDirection(bitmap, new Point(x, y));
        } /* while this Path */

        path =  new Path(area, sign, len, pt);
    }

    private boolean isPathComplete(Point currentPoint) {
        return currentPoint.x==startPoint.x && currentPoint.y==startPoint.y;
    }

    private static Point[] expendPointArrayCapacity(Point[] pt) {
        int newSize = (int)(1.3 * (pt.length+100));
        Point[] newSizedPointArray = new Point[newSize];
        System.arraycopy(pt,0,newSizedPointArray,0,pt.length);
        return newSizedPointArray;
    }

    private Point determineNextDirection(Bitmap bm, Point currentPoint) {
        Point rightPixelPosition = new Point(currentPoint.x + (direction.x+direction.y-1)/2, currentPoint.y + (direction.y-direction.x-1)/2);
        Point leftPixelPosition = new Point(currentPoint.x + (direction.x-direction.y-1)/2, currentPoint.y + (direction.y+direction.x-1)/2);

        boolean isRightPixelFilled = bm.getPixelValue(rightPixelPosition.x,rightPixelPosition.y);
        boolean isLeftPixelFilled = bm.getPixelValue(leftPixelPosition.x,leftPixelPosition.y);

        if (isAmbiguousSituation(isRightPixelFilled, isLeftPixelFilled)) {
            if (shouldGoRightInAmbiguousSituation(bm, currentPoint.x, currentPoint.y)) {
                direction = performRightTurn();
            } else {
                direction = performLeftTurn();
            }
        } else if (isRightPixelFilled) {
            direction = performRightTurn();
        } else if (!isLeftPixelFilled) {
            direction = performLeftTurn();
        }
        return direction;
    }

    private Point performLeftTurn() {
        return new Point(- direction.y,direction.x);
    }

    private Point performRightTurn() {
        return new Point(direction.y,-direction.x);
    }

    private static boolean isAmbiguousSituation(boolean c, boolean d) {
        return c && !d;
    }

    private boolean shouldGoRightInAmbiguousSituation(Bitmap bm, int x, int y) {
        return turnPolicy == PotraceLib.POTRACE_TURNPOLICY_RIGHT
                || (turnPolicy == PotraceLib.POTRACE_TURNPOLICY_BLACK && sign == '+')
                || (turnPolicy == PotraceLib.POTRACE_TURNPOLICY_WHITE && sign == '-')
                || (turnPolicy == PotraceLib.POTRACE_TURNPOLICY_RANDOM && Decompose.detrand(x,y))
                || (turnPolicy == PotraceLib.POTRACE_TURNPOLICY_MAJORITY && bm.getMajorityValueAtIntersection(x, y))
                || (turnPolicy == PotraceLib.POTRACE_TURNPOLICY_MINORITY && !bm.getMajorityValueAtIntersection(x, y));
    }
}
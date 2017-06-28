package refactored.potrace;

import java.awt.*;

public class FindPath {

    Path path;

    public FindPath(Bitmap bitmap, Point startPointOfPath, int sign, int turnPolicy) {
        Point[] pt = new Point[1];

        int x = startPointOfPath.x;
        int y = startPointOfPath.y;
        Point direction = new Point(0, -1);

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

            if (isPathComplete(startPointOfPath, new Point(x, y))) {
                break;
            }

            direction = determineNextDirection(bitmap, sign, turnPolicy, new Point(x, y), direction);
        } /* while this Path */

        path =  new Path(area, sign, len, pt);
    }

    private static boolean isPathComplete(Point startPointOfPath, Point currentPoint) {
        return currentPoint.x==startPointOfPath.x && currentPoint.y==startPointOfPath.y;
    }

    private static Point[] expendPointArrayCapacity(Point[] pt) {
        int newSize = (int)(1.3 * (pt.length+100));
        Point[] newSizedPointArray = new Point[newSize];
        System.arraycopy(pt,0,newSizedPointArray,0,pt.length);
        return newSizedPointArray;
    }

    private static Point determineNextDirection(Bitmap bm, int sign, int turnpolicy, Point currentPoint, Point direction) {
        Point rightPixelPosition = new Point(currentPoint.x + (direction.x+direction.y-1)/2, currentPoint.y + (direction.y-direction.x-1)/2);
        Point leftPixelPosition = new Point(currentPoint.x + (direction.x-direction.y-1)/2, currentPoint.y + (direction.y+direction.x-1)/2);

        boolean isRightPixelFilled = bm.getPixelValue(rightPixelPosition.x,rightPixelPosition.y);
        boolean isLeftPixelFilled = bm.getPixelValue(leftPixelPosition.x,leftPixelPosition.y);

        if (isAmbiguousSituation(isRightPixelFilled, isLeftPixelFilled)) {
            if (shouldGoRightInAmbiguousSituation(bm, sign, turnpolicy, currentPoint.x, currentPoint.y)) {
                direction = performRightTurn(direction);
            } else {
                direction = performLeftTurn(direction);
            }
        } else if (isRightPixelFilled) {
            direction = performRightTurn(direction);
        } else if (!isLeftPixelFilled) {
            direction = performLeftTurn(direction);
        }
        return direction;
    }

    private static Point performLeftTurn(Point direction) {
        return new Point(- direction.y,direction.x);
    }

    private static Point performRightTurn(Point direction) {
        return new Point(direction.y,-direction.x);
    }

    private static boolean isAmbiguousSituation(boolean c, boolean d) {
        return c && !d;
    }

    private static boolean shouldGoRightInAmbiguousSituation(Bitmap bm, int sign, int turnpolicy, int x, int y) {
        return turnpolicy == PotraceLib.POTRACE_TURNPOLICY_RIGHT
                || (turnpolicy == PotraceLib.POTRACE_TURNPOLICY_BLACK && sign == '+')
                || (turnpolicy == PotraceLib.POTRACE_TURNPOLICY_WHITE && sign == '-')
                || (turnpolicy == PotraceLib.POTRACE_TURNPOLICY_RANDOM && Decompose.detrand(x,y))
                || (turnpolicy == PotraceLib.POTRACE_TURNPOLICY_MAJORITY && bm.getMajorityValueAtIntersection(x, y))
                || (turnpolicy == PotraceLib.POTRACE_TURNPOLICY_MINORITY && !bm.getMajorityValueAtIntersection(x, y));
    }
}
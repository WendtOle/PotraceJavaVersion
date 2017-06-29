package refactored.potrace;

import java.awt.*;

public class FindPath {

    Point startPoint;
    Point direction = new Point(0, -1);;
    int sign;
    int turnPolicy;
    Bitmap bitmap;
    Point[] points = new Point[1];
    int indexOfCurrentPoint = 0;
    Point currentPoint;
    int areaOfPath = 0;

    public FindPath(Bitmap bitmap, Point startPointOfPath, int sign, int turnPolicy) {
        this.startPoint = startPointOfPath;
        this.currentPoint = new Point(startPointOfPath.x,startPointOfPath.y);
        this.sign = sign;
        this.turnPolicy = turnPolicy;
        this.bitmap = bitmap;

        findPath();
    }

    private void findPath() {
        while (true) {
            addPointToPath();
            moveToNextPoint();
            if (isPathComplete())
                break;
            determineNextDirection();
        }
    }

    private void addPointToPath() {
        extendPointArrayCapacityWhenNecessary();
        points[indexOfCurrentPoint] = new Point(currentPoint.x,currentPoint.y);
        indexOfCurrentPoint++;
    }

    private void extendPointArrayCapacityWhenNecessary() {
        if (indexOfCurrentPoint >= points.length) {
            expendPointArrayCapacity();
        }
    }

    private void expendPointArrayCapacity() {
        int newSize = (int)(1.3 * (points.length+100));
        Point[] newSizedPointArray = new Point[newSize];
        System.arraycopy(points,0,newSizedPointArray,0,points.length);
        points = newSizedPointArray;

    }

    private void moveToNextPoint() {
        currentPoint.x += direction.x;
        currentPoint.y += direction.y;
        areaOfPath += currentPoint.x * direction.y;
    }

    private boolean isPathComplete() {
        return currentPoint.x==startPoint.x && currentPoint.y==startPoint.y;
    }

    private void determineNextDirection() {
        boolean isRightPixelFilled = isRightPixelFilled();
        boolean isLeftPixelFilled = isLeftPixelFilled();

        if (isAmbiguousSituation(isRightPixelFilled, isLeftPixelFilled)) {
            performTurnInAmbiguousSituation();
        } else if (isRightPixelFilled) {
            performRightTurn();
        } else if (!isLeftPixelFilled) {
            performLeftTurn();
        }
    }

    private boolean isRightPixelFilled() {
        Point rightPixelPosition = new Point(currentPoint.x + (direction.x+direction.y-1)/2, currentPoint.y + (direction.y-direction.x-1)/2);
        return bitmap.getPixelValue(rightPixelPosition.x,rightPixelPosition.y);
    }

    private boolean isLeftPixelFilled() {
        Point leftPixelPosition = new Point(currentPoint.x + (direction.x-direction.y-1)/2, currentPoint.y + (direction.y+direction.x-1)/2);
        return bitmap.getPixelValue(leftPixelPosition.x,leftPixelPosition.y);
    }

    private static boolean isAmbiguousSituation(boolean c, boolean d) {
        return c && !d;
    }

    private void performTurnInAmbiguousSituation() {
        if (shouldGoRightInAmbiguousSituation()) {
            performRightTurn();
        } else {
            performLeftTurn();
        }
    }

    private boolean shouldGoRightInAmbiguousSituation() {
        return turnPolicy == PotraceLib.POTRACE_TURNPOLICY_RIGHT
                || (turnPolicy == PotraceLib.POTRACE_TURNPOLICY_BLACK && sign == '+')
                || (turnPolicy == PotraceLib.POTRACE_TURNPOLICY_WHITE && sign == '-')
                || (turnPolicy == PotraceLib.POTRACE_TURNPOLICY_RANDOM && detrand(currentPoint.x,currentPoint.y))
                || (turnPolicy == PotraceLib.POTRACE_TURNPOLICY_MAJORITY && bitmap.getMajorityValueAtIntersection(currentPoint.x, currentPoint.y))
                || (turnPolicy == PotraceLib.POTRACE_TURNPOLICY_MINORITY && !bitmap.getMajorityValueAtIntersection(currentPoint.x, currentPoint.y));
    }

    static boolean detrand(int x, int y) {
        int z;
        char t[] = {
        /* non-linear sequence: constant term of inverse in GF(8),
        mod x^8+x^4+x^3+x+1 */
                0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1,
                0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0,
                0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1,
                1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1,
                0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0,
                0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0,
                0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0,
                0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1,
                1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0,
                0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1,
                1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0,
        };

        /* 0x04b3e375 and 0x05a8ef93 are chosen to contain every possible
        5-bit sequence */
        z = ((0x04b3e375 * x) ^ y) * 0x05a8ef93;
        z = t[z & 0xff] ^ t[(z>>8) & 0xff] ^ t[(z>>16) & 0xff] ^ t[(z>>24) & 0xff];
        return z == 1 ? true : false;
    }

    private void performRightTurn() {
        direction = new Point(direction.y,-direction.x);
    }

    private void performLeftTurn() {
        direction = new Point(- direction.y,direction.x);
    }

    public Path getPath(){
        return new Path(areaOfPath, sign, indexOfCurrentPoint, points);
    }
}
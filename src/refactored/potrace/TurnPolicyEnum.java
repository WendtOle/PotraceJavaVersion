package refactored.potrace;

import java.awt.*;

/**
 * Created by andreydelany on 29.06.17.
 */
public enum TurnPolicyEnum {
    BLACK, WHITE, LEFT, RIGHT, MINORITY, MAJORITY, RANDOM;

    public boolean isTurnPolicySatisfied(int sign, BitmapHandlerInterface bitmapHandler, Point currentPoint) {
        switch (this) {
            case RIGHT: return true;
            case BLACK: return sign == '+';
            case WHITE: return sign == '-';
            case RANDOM: return detrand(currentPoint.x,currentPoint.y);
            case MAJORITY: return getMajorityValueAtIntersection(currentPoint.x,currentPoint.y,bitmapHandler);
            case MINORITY: return isMinorityAtIntersection(currentPoint.x,currentPoint.y,bitmapHandler);
            default: return false;
        }
    }

    private boolean detrand(int x, int y) {
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

    static boolean getMajorityValueAtIntersection(int x, int y, BitmapHandlerInterface bitmapHandler) {
        int i, a, ct;

        for (i=2; i<5; i++) { /* check at "radius" i */
            ct = 0;
            for (a=-i+1; a<=i-1; a++) {
                ct += bitmapHandler.isPixelFilled(new Point(x+a, y+i-1)) ? 1 : -1;
                ct += bitmapHandler.isPixelFilled(new Point(x+i-1, y+a-1)) ? 1 : -1;
                ct += bitmapHandler.isPixelFilled(new Point(x+a-1, y-i)) ? 1 : -1;
                ct += bitmapHandler.isPixelFilled(new Point(x-i, y+a)) ? 1 : -1;
            }
            if (ct>0) {
                return true;
            } else if (ct<0) {
                return false;
            }
        }
        return false;
    }

    boolean isMinorityAtIntersection(int x, int y, BitmapHandlerInterface bitmapHandler){
        return !getMajorityValueAtIntersection(x,y,bitmapHandler);
    }
}
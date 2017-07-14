package Potrace.refactored;

import java.awt.*;

/**
 * Created by andreydelany on 13.07.17.
 */
public class FilledEmptyRatio {
    BitmapHandlerInterface bitmapHandler;

    public boolean isMajority(BitmapHandlerInterface bitmapHandler, Point intersection){
        this.bitmapHandler = bitmapHandler;
        for (int radius = 2; radius < 5; radius ++) { /* check at "radius" i */
            int ratio = 0;
            for (int a = -radius + 1; a <= radius - 1; a ++)
                ratio += getCt(radius, a,intersection);
            if (ratio>0) {
                return true;
            } else if (ratio<0) {
                return false;
            }
        }
        return false;
    }

    private int getCt(int currentRadius, int a, Point intersection) {
        int ct = 0;
        ct += bitmapHandler.isPixelFilled(new Point(intersection.x+a, intersection.y+currentRadius-1)) ? 1 : -1;
        ct += bitmapHandler.isPixelFilled(new Point(intersection.x+currentRadius-1, intersection.y+a-1)) ? 1 : -1;
        ct += bitmapHandler.isPixelFilled(new Point(intersection.x+a-1, intersection.y-currentRadius)) ? 1 : -1;
        ct += bitmapHandler.isPixelFilled(new Point(intersection.x-currentRadius, intersection.y+a)) ? 1 : -1;
        return ct;
    }
}

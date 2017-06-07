package BitmapLibrary;

import Tools.BetterBitmap;
import potrace.bitmap;

import java.awt.*;

/**
 * Created by andreydelany on 03/04/2017.
 */
public class DefaultBitmapBoxOnPotraceWordBoundary extends BetterBitmap {
    public DefaultBitmapBoxOnPotraceWordBoundary() {
        super(bitmap.PIXELINWORD * 2, 2);
        addPolygon(new Point(bitmap.PIXELINWORD -1,1), new Point(bitmap.PIXELINWORD,0),true);
    }
}

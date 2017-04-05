package BitmapLibrary;

import Tools.BetterBitmap;
import potrace.potrace_bitmap;

import java.awt.*;

/**
 * Created by andreydelany on 03/04/2017.
 */
public class DefaultBitmapBoxOnPotraceWordBoundary extends BetterBitmap {
    public DefaultBitmapBoxOnPotraceWordBoundary() {
        super(potrace_bitmap.PIXELINWORD * 2, 2);
        addPolygon(new Point(potrace_bitmap.PIXELINWORD -1,1), new Point(potrace_bitmap.PIXELINWORD,0),true);
    }
}

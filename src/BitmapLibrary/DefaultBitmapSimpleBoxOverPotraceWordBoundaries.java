package BitmapLibrary;

import Tools.BetterBitmap;
import potrace.potrace_bitmap;

import java.awt.*;

/**
 * Created by andreydelany on 01/04/2017.
 */
public class DefaultBitmapSimpleBoxOverPotraceWordBoundaries extends BetterBitmap{

    public DefaultBitmapSimpleBoxOverPotraceWordBoundaries() {
        super(potrace_bitmap.PIXELINWORD * 2, 3);
        fillCompleteBitMap(true);
        addPolygon(new Point(1, 1), new Point(potrace_bitmap.PIXELINWORD - 2, 1), false);
    }
}

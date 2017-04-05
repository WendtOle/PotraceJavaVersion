package BitmapLibrary;

import Tools.BetterBitmap;

import java.awt.*;

/**
 * Created by andreydelany on 03/04/2017.
 */
public class DefaultBitmapWithSimpleChildenAndSiblings extends BetterBitmap{
    public DefaultBitmapWithSimpleChildenAndSiblings() {
        super(5,4);
        fillCompleteBitMap(true);
        addPolygon(new Point(1,2), new Point(1,1),false);
        addPolygon(new Point(3,2), new Point(3,1),false);

        /*
        x x x x x
        x 0 x 0 x
        x 0 x 0 x
        x x x x x
         */
    }
}

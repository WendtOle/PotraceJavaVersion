package BitmapLibrary;

import Tools.BetterBitmap;

import java.awt.*;

/**
 * Created by andreydelany on 01/04/2017.
 */
public class DefaultBitmap extends BetterBitmap{
    public DefaultBitmap() {
        super(4,4);
        addBlob(new Point(3,3),true);
        addBlob(new Point(0,0),true);
        addBlob(new Point(3,0),true);
        addBlob(new Point(0,3),true);
        addPolygon(new Point(0,2), new Point(2,2),true);
        addPolygon(new Point(2,1), new Point(3,1),true);

        /*
        x 0 0 x
        x x x 0
        o o x x
        x o o x
         */
    }
}
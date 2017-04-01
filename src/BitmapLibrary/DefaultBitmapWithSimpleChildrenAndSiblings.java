package BitmapLibrary;

import Tools.BetterBitmap;

import java.awt.*;

/**
 * Created by andreydelany on 01/04/2017.
 */
public class DefaultBitmapWithSimpleChildrenAndSiblings extends BetterBitmap{
    public DefaultBitmapWithSimpleChildrenAndSiblings() {
        super(8,8);
        addPolygon(new Point(0,7), new Point(0,0),true);
        addPolygon(new Point(0,7), new Point(4,7),true);
        addPolygon(new Point(0,0), new Point(6,0),true);
        addPolygon(new Point(6,5), new Point(6,0),true);
        addPolygon(new Point(4,7), new Point(4,5),true);
        addBlob(new Point(5,5), true);
        addPolygon(new Point(6,7), new Point(7,7),true);
        addPolygon(new Point(2,5), new Point(2,4),true);
        addPolygon(new Point(2,2), new Point(4,2),true);
        addBlob(new Point(4,3), true);
    }
}

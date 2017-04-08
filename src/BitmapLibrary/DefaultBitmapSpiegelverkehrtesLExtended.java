package BitmapLibrary;

import Tools.BetterBitmap;

import java.awt.*;

/**
 * Created by andreydelany on 06/04/2017.
 */
public class DefaultBitmapSpiegelverkehrtesLExtended extends BetterBitmap{
    public DefaultBitmapSpiegelverkehrtesLExtended() {
        super(8,8);
        addPolygon(new Point(6,7),new Point(6,4),true);
        addPolygon(new Point(1,1),new Point(3,1),true);
        addPolygon(new Point(5,2),new Point(7,0),true);
        addPolygon(new Point(0,3),new Point(1,3),true);
    }
}

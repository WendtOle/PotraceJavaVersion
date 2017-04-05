package BitmapLibrary;
import Tools.*;

import java.awt.*;

/**
 * Created by andreydelany on 01/04/2017.
 */
public class DefaultBitmapSimpleChildList extends BetterBitmap{

    public DefaultBitmapSimpleChildList() {
        super(7,7);
        addPolygon(new Point(0,6), new Point(6,0),true);
        addPolygon(new Point(1,5), new Point(5,1),false);
        addPolygon(new Point(2,4), new Point(4,2),true);
        addBlob(new Point(3,3),false);

        /*
        x x x x x x x
        x o o o o o x
        x o x x x o x
        x o x o x o x
        x o x x x x x
        x o o o o o x
        x x x x x x x
         */

    }
}

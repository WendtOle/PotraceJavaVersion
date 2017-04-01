package BitmapLibrary;
import potrace.*;
import Tools.*;

import java.awt.*;

/**
 * Created by andreydelany on 30/03/2017.
 */
public class DefaultBimapSmallBox extends BetterBitmap {

    public DefaultBimapSmallBox() {
        super(4,4);
        addPolygon(new Point(1,2),new Point(2,1),true);
    }
}

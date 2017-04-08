package BitmapLibrary;

import Tools.BetterBitmap;
import Tools.BitmapPrinter;

import java.awt.*;

/**
 * Created by andreydelany on 06/04/2017.
 */
public class DefaultBitmapSpiegelverkehrtesL extends BetterBitmap {
    public DefaultBitmapSpiegelverkehrtesL(){
        super(8,8);
        addPolygon(new Point(6,7),new Point(6,4),true);
        addPolygon(new Point(0,1),new Point(3,1),true);
        addPolygon(new Point(5,2),new Point(7,0),true);

        BitmapPrinter printer = new BitmapPrinter(this);
        printer.print();
    }
}

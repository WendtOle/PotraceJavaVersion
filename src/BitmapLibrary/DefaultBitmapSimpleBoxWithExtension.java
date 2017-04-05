package BitmapLibrary;

import Tools.BetterBitmap;

import java.awt.*;

/**
 * Created by andreydelany on 03/04/2017.
 */
public class DefaultBitmapSimpleBoxWithExtension extends BetterBitmap {
    public DefaultBitmapSimpleBoxWithExtension() {
        super(4,4);
        addPolygon(new Point(1,2), new Point(2,1),true);
        addPolygon(new Point(0,0), new Point(1,0),true);
        addBlob(new Point(3,3),true);

        /*
        o o o x
        o x x o
        o x x o
        x x o o
        */
    }
}

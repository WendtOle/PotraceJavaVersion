package BitmapLibrary;

import Tools.BetterBitmap;

import java.awt.*;

/**
 * Created by andreydelany on 01/04/2017.
 */
public class DefaultBitMapForFindingDifferentPathes extends BetterBitmap{

    private Point[] expectedPointsForBigPath;
    private Point[] expectedPointsForShortPath;

    public DefaultBitMapForFindingDifferentPathes(){
        super(4,4);
        addPolygon(new Point(0,3), new Point(0,2),true);
        addPolygon(new Point(1,2), new Point(1,1),true);
        addBlob(new Point(0,0), true);
        addBlob(new Point(2,1), true);
        addBlob(new Point(3,0), true);

        /*
        x o o o
        x x o o
        o x x o
        x o o x
        */

        setPointsForLongPath();
        setPointsForShortPath();
    }

    private void setPointsForLongPath() {
        expectedPointsForBigPath =  new Point[]{ new Point(0,4), new Point(0,3), new Point(0,2), new Point(1,2), new Point(1,1),
                                    new Point(0,1), new Point(0,0), new Point(1,0), new Point(1,1), new Point(2,1),
                                    new Point(3,1), new Point(3,0), new Point(4,0), new Point(4,1), new Point(3,1),
                                    new Point(3,2), new Point(2,2), new Point(2,3), new Point(1,3), new Point(1,4)};
    }

    private void setPointsForShortPath() {
        expectedPointsForShortPath = new Point[]{ new Point(0,4), new Point(0,3), new Point(0,2), new Point(1,2), new Point(1,1),
                                        new Point(2,1), new Point(3,1), new Point(3,2), new Point(2,2), new Point(2,3),
                                        new Point(1,3), new Point(1,4)};
    }

    public Point[] getLongPath() {
        return expectedPointsForBigPath;
    }

    public Point[] getShortPath() {
        return expectedPointsForShortPath;
    }
}

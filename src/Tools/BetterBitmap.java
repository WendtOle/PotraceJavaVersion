package Tools;
import potrace.potrace_bitmap;

import java.awt.*;

/**
 * Created by andreydelany on 29/03/2017.
 */
public class BetterBitmap extends potrace_bitmap{

    public BetterBitmap(int width, int height) {
        super(width,height);
    }

    private void fillArea(Point upperLeftCorner, Point downRightCorner, boolean filled){
        for (int y = downRightCorner.y; y <= upperLeftCorner.y; y ++)
            for (int x = upperLeftCorner.x ; x <= downRightCorner.x; x ++)
                BM_PUT(x,y,filled);
    }

    public void fillCompleteBitMap(boolean filled) {
        fillArea(new Point(0,h), new Point(w,0),filled);
    }

    public void addPolygon(Point upperLeftCorner, Point downRightCorner, boolean filled) {
        fillArea(upperLeftCorner, downRightCorner,filled);
    }

    public void addBlob(Point blob, boolean filled) {
        fillArea(blob, blob,filled);
    }

}

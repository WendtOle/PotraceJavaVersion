package Tools;
import potrace.potrace_bitmap;

import java.awt.*;

/**
 * Created by andreydelany on 29/03/2017.
 */
public class BitMapManipulator {

    potrace_bitmap bitmap;

    public BitMapManipulator(int w, int h) {
        this.bitmap = new potrace_bitmap(w,h);
    }

    public BitMapManipulator(potrace_bitmap bitmap) {this.bitmap = bitmap;};

    public void addPolygon(Point upperLeftCorner, Point downRightCorner, boolean filled) {
        for (int y = downRightCorner.y; y <= upperLeftCorner.y; y ++)
            for (int x = upperLeftCorner.x ; x <= downRightCorner.x; x ++)
                bitmap.BM_PUT(x,y,filled);
    }

    public void addBlob(Point blob, boolean filled) {
        bitmap.BM_PUT(blob.x,blob.y,filled);
    }

    public potrace_bitmap getBitmap() {
        return bitmap;
    }

}

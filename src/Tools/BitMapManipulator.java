package Tools;
import potrace.potrace_bitmap;

import java.awt.*;

/**
 * Created by andreydelany on 29/03/2017.
 */
public class BitMapManipulator {

    public static potrace_bitmap addPolygon(potrace_bitmap bitmap, Point upperLeftCorner, Point downRightCorner, boolean filled) {
        for (int y = downRightCorner.y; y <= upperLeftCorner.y; y ++)
            for (int x = upperLeftCorner.x ; x <= downRightCorner.x; x ++)
                bitmap.BM_PUT(x,y,filled);
        return bitmap;
    }

    public static potrace_bitmap addBlob(potrace_bitmap bitmap, Point blob, boolean filled) {
        bitmap.BM_PUT(blob.x,blob.y,filled);
        return bitmap;
    }
}

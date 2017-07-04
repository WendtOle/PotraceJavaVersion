package refactored.potrace;

import java.awt.*;

/**
 * Created by andreydelany on 02.07.17.
 */
public class ClearBitmapWithBBox {

    BitmapHandlerInterface bitmapHandler;

    public ClearBitmapWithBBox(Bitmap bitmap) {
        this.bitmapHandler = new BitmapHandler(bitmap);
    }

    public void clearBitmapWithBBox(BBox bbox) {
        int indexOfWordWhereBBoxStarts = (bbox.x0 / Bitmap.PIXELINWORD);
        int indexOfwordWhereBBoxEnds = ((bbox.x1 + Bitmap.PIXELINWORD-1) / Bitmap.PIXELINWORD);

        for (int y = bbox.y0; y < bbox.y1; y ++) {
            for (int i = indexOfWordWhereBBoxStarts; i < indexOfwordWhereBBoxEnds; i++) {
                bitmapHandler.setWordToNull(new Point(i * Bitmap.PIXELINWORD,y));
            }
        }
    }
}
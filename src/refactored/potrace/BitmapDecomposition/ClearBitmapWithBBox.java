package refactored.potrace.BitmapDecomposition;

import refactored.potrace.Bitmap;

import java.awt.*;

/**
 * Created by andreydelany on 02.07.17.
 */
public class ClearBitmapWithBBox {

    BitmapHandlerInterface bitmapHandler;
    int indexOfWordWhereBBoxStarts, indexOfwordWhereBBoxEnds;

    public ClearBitmapWithBBox(Bitmap bitmap) {
        this.bitmapHandler = new BitmapHandler(bitmap);
    }

    public void clearBitmapWithBBox(BBox bbox) {
        setHorizontalRange(bbox);
        clearAllLinesOfBbox(bbox);
    }

    private void setHorizontalRange(BBox bbox) {
        indexOfWordWhereBBoxStarts = (bbox.x0 / Bitmap.PIXELINWORD);
        indexOfwordWhereBBoxEnds = ((bbox.x1 + Bitmap.PIXELINWORD-1) / Bitmap.PIXELINWORD);
    }

    private void clearAllLinesOfBbox(BBox bbox) {
        for (int y = bbox.y0; y < bbox.y1; y ++) {
            clearAllWordsThatOverlapWithBbox(y);
        }
    }

    private void clearAllWordsThatOverlapWithBbox(int y) {
        for (int i = indexOfWordWhereBBoxStarts; i < indexOfwordWhereBBoxEnds; i++) {
            bitmapHandler.setWordToNull(new Point(i * Bitmap.PIXELINWORD,y));
        }
    }
}
package refactored;

import General.Bitmap;

import java.awt.*;

/**
 * Created by andreydelany on 02.07.17.
 */
public class ClearBitmapWithBoundingBox {

    BitmapHandlerInterface bitmapHandler;
    int indexOfWordWhereBBoxStarts, indexOfwordWhereBBoxEnds;

    public ClearBitmapWithBoundingBox(Bitmap bitmap) {
        this.bitmapHandler = new BitmapHandler(bitmap);
    }

    public void clearBitmapWithBBox(BoundingBox boundingBox) {
        setHorizontalRange(boundingBox);
        clearBitmapInHorizontalRange(boundingBox);
    }

    private void setHorizontalRange(BoundingBox boundingBox) {
        indexOfWordWhereBBoxStarts = (boundingBox.x0 / Bitmap.PIXELINWORD);
        indexOfwordWhereBBoxEnds = ((boundingBox.x1 + Bitmap.PIXELINWORD-1) / Bitmap.PIXELINWORD);
    }

    private void clearBitmapInHorizontalRange(BoundingBox bbox) {
        for (int y = bbox.y0; y < bbox.y1; y ++) {
            clearAllWordsThatOverlapWithBbox(y);
        }
    }

    private void clearAllWordsThatOverlapWithBbox(int line) {
        for (int indexOfCurrentWord = indexOfWordWhereBBoxStarts; indexOfCurrentWord < indexOfwordWhereBBoxEnds; indexOfCurrentWord++) {
            clearWord(line, indexOfCurrentWord);
        }
    }

    private void clearWord(int line, int indexOfWord) {
        Point positionOfWord = new Point(indexOfWord * Bitmap.PIXELINWORD, line);
        bitmapHandler.setWordToNull(positionOfWord);
    }
}
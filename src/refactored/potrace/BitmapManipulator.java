package refactored.potrace;

import java.awt.*;

/**
 * Created by andreydelany on 30.06.17.
 */
public class BitmapManipulator {

    Bitmap bitmap;

    public BitmapManipulator(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public void setPixelToValue(Point pixel, boolean b) {
        if (bitmap.isPixelInRange(pixel))
            setPixelToValueWithoutBoundChecking(pixel, b);
    }

    public void setWholeBitmapToSpecificValue(int c) {
        for (int y = 0; y < bitmap.height; y ++) {
            setLineToSpecificValue(c, y);
        }
    }

    public void clearBitmapWithBBox(BBox bbox) {
        int imin = (bbox.x0 / Bitmap.PIXELINWORD);
        int imax = ((bbox.x1 + Bitmap.PIXELINWORD-1) / Bitmap.PIXELINWORD);

        for (int y = bbox.y0; y < bbox.y1; y ++) {
            for (int i = imin; i<imax; i++) {
                bitmap.words[y * bitmap.wordsPerScanLine + i] = 0;
            }
        }
    }

    void clearExcessPixelsOfBitmap() {
        if (bitmap.width % Bitmap.PIXELINWORD != 0) {
            long mask = shiftValueForLastWordInLine(Bitmap.BM_ALLBITS);
            for (int y = 0; y < bitmap.height; y ++) {
                bitmap.words[y * bitmap.wordsPerScanLine + bitmap.wordsPerScanLine - 1] = bitmap.getWordWherePixelIsContained(new Point(bitmap.width, y)) & mask;
            }
        }
    }

    int getAccessIndexOfWord(Point pixel) {
        return pixel.y * bitmap.wordsPerScanLine + (pixel.x / Bitmap.PIXELINWORD);
    }

    private void setPixelToValueWithoutBoundChecking(Point pixel, boolean shallPixelFilled) {
        if (shallPixelFilled)
            fillPixel(pixel);
        else
            clearPixel(pixel);
    }

    private void clearPixel(Point pixel){
        int accessIndex = getAccessIndexOfWord(pixel);
        bitmap.words[accessIndex] = bitmap.words[accessIndex] & ~bitmap.getMaskForPosition(pixel.x);
    }

    private void fillPixel(Point pixel) {
        int accessIndex = getAccessIndexOfWord(pixel);
        bitmap.words[accessIndex] = bitmap.words[accessIndex] | bitmap.getMaskForPosition(pixel.x);
    }

    private void setLineToSpecificValue(int c, int y) {
        for (int dyIndex = 0; dyIndex < bitmap.wordsPerScanLine; dyIndex ++) {
            setPotraceWordToSpecificValue(c, y, dyIndex);
        }
    }

    private void setPotraceWordToSpecificValue(int c, int y, int dyIndex) {
        bitmap.words[bitmap.wordsPerScanLine * y + dyIndex] = getClearedValue(c, dyIndex);
    }

    private long getClearedValue(int c, int dyIndex) {
        long clearedValue = (c == 1 ? -1 : 0);
        clearedValue = shiftClearedValueIfNeccessary(dyIndex, clearedValue);
        return clearedValue;
    }

    private long shiftClearedValueIfNeccessary(int dyIndex, long clearedValue) {
        if (dyIndex == bitmap.wordsPerScanLine -1) {
            clearedValue = shiftValueForLastWordInLine(clearedValue);
        }
        return clearedValue;
    }

    private long shiftValueForLastWordInLine(long value) {
        return value << (Bitmap.PIXELINWORD - (bitmap.width % Bitmap.PIXELINWORD));
    }
}
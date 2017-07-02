package refactored.potrace;

import java.awt.*;

/**
 * Created by andreydelany on 30.06.17.
 */
public class BitmapPixelHandler {

    Bitmap bitmap;

    public BitmapPixelHandler(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    boolean isPixelInRange(Point pixel) {
        return isCoordinateInRange(pixel.x, bitmap.width) && isCoordinateInRange(pixel.y, bitmap.height);
    }

    private boolean isCoordinateInRange(int coordinate, int upperBound) {
        return (coordinate) >= 0 && (coordinate) < (upperBound);
    }

    static long getMultiplePixelMaskUntilPosition(int position){
        return getMask(Bitmap.BM_ALLBITS, position );
    }

    static long getOnePixelMaskForPosition(int position) {
        return getMask(1l, position + 1 );
    }

    static long getMask(long pattern,int position) {
        return (pattern) << (Bitmap.PIXELINWORD  - (position));
    }

    private boolean getPixelValueWithoutBoundChecking(Point pixel) {
        long pixelValue = getWordWherePixelIsContained(pixel) & getOnePixelMaskForPosition(pixel.x);
        return(pixelValue != 0);
    }

    public boolean getPixelValue(Point pixel) {
        return isPixelInRange(pixel) && getPixelValueWithoutBoundChecking(pixel);
    }

    private long[] getLineWherePixelIsContained(int y) {
        long[] scanLine = new long[bitmap.wordsPerScanLine];
        for (int i = 0; i < bitmap.wordsPerScanLine; i ++) {
            scanLine[i] = bitmap.words[(y * bitmap.wordsPerScanLine) + i];
        }
        return scanLine;
    }

    public long getWordWherePixelIsContained(Point pixel) {
        return getLineWherePixelIsContained(pixel.y)[pixel.x/Bitmap.PIXELINWORD];
    }

    public void setPixelToValue(Point pixel, boolean b) {
        if (isPixelInRange(pixel))
            setPixelToValueWithoutBoundChecking(pixel, b);
    }

    public void setWholeBitmapToSpecificValue(int c) {
        for (int y = 0; y < bitmap.height; y ++) {
            setLineToSpecificValue(c, y);
        }
    }

    void clearExcessPixelsOfBitmap() {
        if (bitmap.width % Bitmap.PIXELINWORD != 0) {
            long mask = shiftValueForLastWordInLine(Bitmap.BM_ALLBITS);
            for (int y = 0; y < bitmap.height; y ++) {
                bitmap.words[y * bitmap.wordsPerScanLine + bitmap.wordsPerScanLine - 1] = getWordWherePixelIsContained(new Point(bitmap.width, y)) & mask;
            }
        }
    }

    protected int getAccessIndexOfWord(Point pixel) {
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
        bitmap.words[accessIndex] = bitmap.words[accessIndex] & ~getOnePixelMaskForPosition(pixel.x);
    }

    private void fillPixel(Point pixel) {
        int accessIndex = getAccessIndexOfWord(pixel);
        bitmap.words[accessIndex] = bitmap.words[accessIndex] | getOnePixelMaskForPosition(pixel.x);
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
        int indexOfLastBitInLastWordInLine = bitmap.width % Bitmap.PIXELINWORD;
        return getMask(value,indexOfLastBitInLastWordInLine);
    }

    protected int getBeginningIndexOfWord(int index) {
        return (index) & -Bitmap.PIXELINWORD;
    }
}
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

    private void setPixelToValueWithoutBoundChecking(Point pixel, boolean shallPixelFilled) {
        if (shallPixelFilled)
            fillPixel(pixel);
        else
            clearPixel(pixel);
    }

    private void clearPixel(Point pixel){
        int accessIndex = bitmap.getAccessIndexOfWord(pixel);
        bitmap.words[accessIndex] = bitmap.words[accessIndex] & ~bitmap.getMaskForPosition(pixel.x);
    }

    private void fillPixel(Point pixel) {
        int accessIndex = bitmap.getAccessIndexOfWord(pixel);
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
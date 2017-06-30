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

    public void invertPathOnBitmap(Path path) {
        if (path.priv.len <= 0) {  /* a Path of length 0 is silly, but legal */
            return;
        }

        int y1 = path.priv.pt[path.priv.len-1].y;
        int xa = path.priv.pt[0].x & - Bitmap.PIXELINWORD;

        for (int k = 0; k < path.priv.len; k ++) {
            int x = path.priv.pt[k].x;
            int y = path.priv.pt[k].y;

            if (y != y1) {
                /* efficiently invert the rectangle [x,xa] x [y,y1] */
                invertBitsInWordsWhichAreInRangeFromXToXAInLine( x, Auxiliary.min(y,y1), xa);
                y1 = y;
            }
        }
    }

    public void invertBitsInWordsWhichAreInRangeFromXToXAInLine(int x, int y, int xa) {
        int beginningIndexOfStartWord = x & - Bitmap.PIXELINWORD;
        int indexOfXInStartWord = x & (Bitmap.PIXELINWORD-1);

        flipAllContainedWordsInLineBetweenToValues(xa,beginningIndexOfStartWord,y);
        flipBitsUnitStartPositionOfStartWord(y, beginningIndexOfStartWord, indexOfXInStartWord);
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

    private void flipBitsUnitStartPositionOfStartWord(int y, int beginningPositionOfStartWord, int indexOfXInStartWord) {
        if (indexOfXInStartWord > 0) {
            int accessIndex = getAccessIndexOfWord(new Point(beginningPositionOfStartWord,y));
            long mask = Bitmap.BM_ALLBITS << (Bitmap.PIXELINWORD - indexOfXInStartWord);
            bitmap.words[accessIndex] = bitmap.words[accessIndex]  ^ mask;
        }
    }

    private void flipAllContainedWordsInLineBetweenToValues(int firstValue, int secondValue, int y) {
        int startX = firstValue;
        int endX = secondValue;

        if (endX < startX) {
            int temp = startX;
            startX = endX;
            endX = temp;
        }

        for(int i = startX; i < endX; i += Bitmap.PIXELINWORD) {
            flipAllBitsInWord(new Point(i, y));
        }
    }

    private void flipAllBitsInWord(Point wordIdentificationPixel) {
        int indexOfWord = getAccessIndexOfWord(wordIdentificationPixel);
        bitmap.words[indexOfWord] = bitmap.words[indexOfWord]  ^ Bitmap.BM_ALLBITS;
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
package refactored.potrace;

import java.awt.*;

/**
 * Created by andreydelany on 02.07.17.
 */
public class PathOnBitmapInverter {

    BitmapHandlerInterface bitmapHandler;

    public PathOnBitmapInverter(Bitmap bitmap){
        this.bitmapHandler = new BitmapHandler(bitmap);
    }

    public void invertPathOnBitmap(Path path) {

        int y1 = path.priv.pt[path.priv.len-1].y;
        int xa = bitmapHandler.getBeginningIndexOfWordWithPixel(new Point(path.priv.pt[0]));


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
        int beginningIndexOfStartWord = bitmapHandler.getBeginningIndexOfWordWithPixel(new Point(x,0));
        int indexOfXInStartWord = x & (Bitmap.PIXELINWORD-1);

        flipAllContainedWordsInLineBetweenToValues(xa,beginningIndexOfStartWord,y);
        flipBitsUnitStartPositionOfStartWord(y, beginningIndexOfStartWord, indexOfXInStartWord);
    }

    private void flipBitsUnitStartPositionOfStartWord(int y, int beginningPositionOfStartWord, int indexOfXInStartWord) {
        if (indexOfXInStartWord > 0) {
            invertWordWithIndexUntilPosition(new Point(beginningPositionOfStartWord,y),indexOfXInStartWord);
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
        invertWordWithIndexUntilPosition(wordIdentificationPixel,Bitmap.PIXELINWORD);
    }

    private void invertWordWithIndexUntilPosition(Point index, int position) {
        long mask = BitMask.getMultiplePixelMaskUntilPosition(position);
        bitmapHandler.flipBitsInWordWithMask(index,mask);
    }
}

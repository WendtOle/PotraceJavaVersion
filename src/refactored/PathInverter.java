package refactored;

import General.*;
import java.awt.*;

/**
 * Created by andreydelany on 02.07.17.
 */
public class PathInverter {

    BitmapHandlerInterface bitmapHandler;
    private Point currentPoint;
    private Point previousePoint;

    public PathInverter(Bitmap bitmap){
        this.bitmapHandler = new BitmapHandler(bitmap);
    }

    public void invertPathOnBitmap(Path path) {

        int verticalFixPunkt = path.priv.pt[path.priv.len-1].y;
        int horizontalFixPunkt = bitmapHandler.getBeginningIndexOfWordWithPixel(new Point(path.priv.pt[0]));
        previousePoint = new Point(horizontalFixPunkt,verticalFixPunkt);

        invertingPathByIntervingRectangleBetweenPointsOfPath(path);
    }

    private void invertingPathByIntervingRectangleBetweenPointsOfPath(Path path) {
        for (int k = 0; k < path.priv.len; k ++) {
            currentPoint = path.priv.pt[k];
            tryToInvertRectangleBetweenCurrentAndPreviousePoint();
        }
    }

    private void tryToInvertRectangleBetweenCurrentAndPreviousePoint() {
        if (isRectangle()) {
            invertRangeInOneLine();
            previousePoint.y = currentPoint.y;
        }
    }

    private int getLineToInvert() {
        return min(currentPoint.y, previousePoint.y);
    }

    private int min(int a, int b) {
        return (a)<(b) ? (a) : (b);
    }

    private boolean isRectangle() {
        return currentPoint.y != previousePoint.y;
    }

    public void invertRangeInOneLine() {
        invertLeadingWordsOfRange();
        invertLeadingWordUntilStartOfRange();
    }

    private void invertLeadingWordsOfRange() {
        int startWordIdentifier = getWordIdentifierOfPreviousePoint();
        int lastWordIdentifier = getWordIdentifierOfCurrentWord();

        if (lastWordIdentifier < startWordIdentifier) {
            int temp = startWordIdentifier;
            startWordIdentifier = lastWordIdentifier;
            lastWordIdentifier = temp;
        }

        for(int i = startWordIdentifier; i < lastWordIdentifier; i += Bitmap.PIXELINWORD) {
            flipAllBitsInWord(new Point(i, getLineToInvert()));
        }
    }

    private int getWordIdentifierOfCurrentWord() {
        return bitmapHandler.getBeginningIndexOfWordWithPixel(new Point(currentPoint.x,0));
    }

    private int getWordIdentifierOfPreviousePoint() {
        return previousePoint.x;
    }

    private void flipAllBitsInWord(Point wordIdentificationPixel) {
        invertWordWithIndexUntilPosition(wordIdentificationPixel,Bitmap.PIXELINWORD);
    }

    private void invertWordWithIndexUntilPosition(Point index, int position) {
        long mask = BitMask.getMultiplePixelMaskUntilPosition(position);
        bitmapHandler.flipBitsInWordWithMask(index,mask);
    }

    private void invertLeadingWordUntilStartOfRange() {
        int startOfRange = currentPoint.x & (Bitmap.PIXELINWORD-1);
        if (startOfRange > 0) {
            Point leadingWordIdentifierPoint = new Point(getWordIdentifierOfCurrentWord(),getLineToInvert());
            invertWordWithIndexUntilPosition(leadingWordIdentifierPoint,startOfRange);
        }
    }
}

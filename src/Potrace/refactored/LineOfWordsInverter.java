package Potrace.refactored;

import Potrace.General.Bitmap;

import java.awt.*;

/**
 * Created by andreydelany on 13.07.17.
 */
public class LineOfWordsInverter {

    BitmapHandlerInterface bitmapHandler;
    Point currentPoint, previousPoint;
    int startWordIdentifier, lastWordIdentifier;
    int line;

    public LineOfWordsInverter(Bitmap bitmap){
        bitmapHandler = new BitmapHandler(bitmap);
    }

    public void invertRangeInOneLine(Point currentPoint, Point previousPoint, int line) {
        setFields(currentPoint,previousPoint,line);
        invertLeadingWordsOfRange();
        if(rangeDidntStartAtWordBeginning())
            invertWordUntilStartOfRange();
    }

    private void setFields(Point currentPoint, Point previousPoint, int line) {
        this.currentPoint = currentPoint;
        this.previousPoint = previousPoint;
        setStartAndLastWordIdentifier();
        this.line = line;
    }

    private void setStartAndLastWordIdentifier() {
        startWordIdentifier = getWordIdentifierOfPreviousPoint();
        lastWordIdentifier = getWordIdentifierOfCurrentWord();
        swapWordIdentifierIfTransposed();
    }

    private int getWordIdentifierOfPreviousPoint() {
        return previousPoint.x;
    }

    private int getWordIdentifierOfCurrentWord() {
        return bitmapHandler.getBeginningIndexOfWordWithPixel(new Point(currentPoint.x,0));
    }

    private void swapWordIdentifierIfTransposed() {
        if (areWordIdentifierTransposed())
            swapWordIdentifier();
    }

    private boolean areWordIdentifierTransposed() {
        return lastWordIdentifier < startWordIdentifier;
    }

    private void swapWordIdentifier() {
        int temp = startWordIdentifier;
        startWordIdentifier = lastWordIdentifier;
        lastWordIdentifier = temp;
    }

    private void invertLeadingWordsOfRange(){
        for(int i = startWordIdentifier; i < lastWordIdentifier; i += Bitmap.PIXELINWORD)
            flipAllBitsInWord(new Point(i, line));
    }

    private void flipAllBitsInWord(Point wordIdentificationPixel) {
        invertWordWithIndexUntilPosition(wordIdentificationPixel,Bitmap.PIXELINWORD);
    }

    private void invertWordWithIndexUntilPosition(Point index, int position) {
        long mask = BitMask.getMultiplePixelMaskFromStartUntilPosition(position);
        bitmapHandler.invertPotraceWordWithMask(index,mask);
    }

    private boolean rangeDidntStartAtWordBeginning() {
        return getStartOfRange() > 0;
    }

    private int getStartOfRange() {
        return currentPoint.x & (Bitmap.PIXELINWORD-1);
    }

    private void invertWordUntilStartOfRange() {
        Point leadingWordIdentifierPoint = new Point(getWordIdentifierOfCurrentWord(),line);
        invertWordWithIndexUntilPosition(leadingWordIdentifierPoint,getStartOfRange());
    }
}
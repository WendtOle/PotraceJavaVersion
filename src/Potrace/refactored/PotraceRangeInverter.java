package Potrace.refactored;

import Potrace.General.Bitmap;

import java.awt.*;

/**
 * Created by andreydelany on 13.07.17.
 */
public class PotraceRangeInverter {

    BitmapHandlerInterface bitmapHandler;
    Point upperBoundIdentificationPixel, lowerBoundIdentificationPixel;
    int startWordIdentifier, lastWordIdentificationPixel;
    int line;

    public PotraceRangeInverter(Bitmap bitmap){
        bitmapHandler = new BitmapHandler(bitmap);
    }

    public void invertRangeInLine(Point upperBoundIdentificationPixel, Point lowerBoundIdentificationPixel, int line) {
        setFields(upperBoundIdentificationPixel,lowerBoundIdentificationPixel,line);
        invertCompletWordsOfRange();
        if(hasRangeExcessPixels())
            invertExcessPixels();
    }

    private void setFields(Point upperBoundIdentificationPixel, Point lowerBoundIdentificationPixel, int line) {
        this.upperBoundIdentificationPixel = upperBoundIdentificationPixel;
        this.lowerBoundIdentificationPixel = lowerBoundIdentificationPixel;
        setRangeIdentifier();
        this.line = line;
    }

    private void setRangeIdentifier() {
        setStartWordIdentifier();
        setLastWordIdentifier();
        swapRangeIdentifierIfTransposed();
    }

    private void setStartWordIdentifier() {
        startWordIdentifier = lowerBoundIdentificationPixel.x;
    }

    private void setLastWordIdentifier() {
        lastWordIdentificationPixel = getWordIdentifierOfUpperBound();
    }

    private int getWordIdentifierOfUpperBound() {
        return bitmapHandler.getBeginningIndexOfWordWithPixel(new Point(upperBoundIdentificationPixel.x,0));
    }

    private void swapRangeIdentifierIfTransposed() {
        if (areWordIdentifierTransposed())
            swapWordIdentifier();
    }

    private boolean areWordIdentifierTransposed() {
        return lastWordIdentificationPixel < startWordIdentifier;
    }

    private void swapWordIdentifier() {
        int temp = startWordIdentifier;
        startWordIdentifier = lastWordIdentificationPixel;
        lastWordIdentificationPixel = temp;
    }

    private void invertCompletWordsOfRange(){
        for(int i = startWordIdentifier; i < lastWordIdentificationPixel; i += Bitmap.PIXELINWORD) {
            Point potraceWordIdentificationPixel = new Point(i,line);
            flipAllBitsInWord(potraceWordIdentificationPixel);
        }
    }

    private void flipAllBitsInWord(Point potraceWordIdentificationPixel) {
        invertWordUntilPosition(potraceWordIdentificationPixel,Bitmap.PIXELINWORD);
    }

    private void invertWordUntilPosition(Point potraceWordIdentificationPixel, int position) {
        long invertingMask = BitMask.getMultiplePixelMaskFromStartUntilPosition(position);
        bitmapHandler.invertPotraceWordWithMask(potraceWordIdentificationPixel,invertingMask);
    }

    private boolean hasRangeExcessPixels() {
        return getAmountOfExcessPixels() > 0;
    }

    private int getAmountOfExcessPixels() {
        return upperBoundIdentificationPixel.x & (Bitmap.PIXELINWORD-1);
    }

    private void invertExcessPixels() {
        Point leadingWordIdentifierPoint = new Point(getWordIdentifierOfUpperBound(),line);
        invertWordUntilPosition(leadingWordIdentifierPoint, getAmountOfExcessPixels());
    }
}
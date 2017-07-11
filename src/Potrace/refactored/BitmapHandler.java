package Potrace.refactored;

import Potrace.General.*;
import java.awt.*;

/**
 * Created by andreydelany on 03.07.17.
 */
public class BitmapHandler implements BitmapHandlerInterface{

    public Bitmap bitmap;

    public BitmapHandler (Bitmap bitmap) {
        this.bitmap = bitmap;
        clearExcessPixel();
    }

    public void flipBitsInWordWithMask(Point positionOfWord, long mask) {
        bitmap.map[getAccessIndex(positionOfWord)] ^= mask;
    }

    public void setWordToNull(Point positionOfWord) {
        bitmap.map[getAccessIndex(positionOfWord)] = 0;
    }

    public boolean areThereFilledPixelInWord(Point positionOfWord) {
        return bitmap.map[getAccessIndex(positionOfWord)] != 0;
    }

    public boolean isPixelFilled(Point positionOfPixel) {
        if (isPixelInRange(positionOfPixel))
            return getPixelValueWithoutBoundChecking(positionOfPixel);
        else
            return false;
    }

    public void setPixel(Point positionOfPixel) {
        if (isPixelInRange(positionOfPixel))
            bitmap.map[getAccessIndex(positionOfPixel)] |= BitMask.getOnePixelMaskForPosition(positionOfPixel.x);
    }

    public int getBeginningIndexOfWordWithPixel(Point positionOfPixel){
        return (positionOfPixel.x) & -Bitmap.PIXELINWORD;
    }

    public int getWithOfBitmap(){
        return bitmap.w;
    }

    public void clearCompleteBitmap() {
        for(int i = 0; i < bitmap.map.length; i ++) {
            bitmap.map[i] = 0;
        }
    }

    private void clearExcessPixel() {
        if (areThereExcessPixel()) {
            clearExcessPixelOfLastWordInLines();
        }
    }

    private boolean areThereExcessPixel() {
        return getPositionOfLastLineBitInWord() != 0;
    }

    private void clearExcessPixelOfLastWordInLines() {
        long mask = getMaskForExcessPixel();
        for (int y = 0; y < bitmap.h; y ++) {
            Point lastPixelInCurrentLine = new Point(bitmap.w,y);
            ANDWordWithMask(lastPixelInCurrentLine,mask);
        }
    }

    private long getMaskForExcessPixel() {
        int indexOfLastBitInLastWordInLine = getPositionOfLastLineBitInWord();
        return BitMask.getMultiplePixelMaskUntilPosition(indexOfLastBitInLastWordInLine);
    }

    private void ANDWordWithMask(Point positionOfWord, long mask) {
        bitmap.map[getAccessIndex(positionOfWord)] &= mask;
    }

    private int getPositionOfLastLineBitInWord() {
        return bitmap.w % Bitmap.PIXELINWORD;
    }

    private int getAccessIndex(Point pixelPosition) {
        return pixelPosition.y * bitmap.dy + (pixelPosition.x / Bitmap.PIXELINWORD);
    }

    private boolean getPixelValueWithoutBoundChecking(Point pixel) {
        long pixelValue = getAndWordWithMask(pixel, BitMask.getOnePixelMaskForPosition(pixel.x));
        return(pixelValue != 0);
    }

    private long getAndWordWithMask(Point positionOfWord, long mask) {
        return bitmap.map[getAccessIndex(positionOfWord)] & mask;
    }

    private boolean isPixelInRange(Point pixel) {
        return isCoordinateInRange(pixel.x, bitmap.w) && isCoordinateInRange(pixel.y, bitmap.h);
    }

    private boolean isCoordinateInRange(int coordinate, int upperBound) {
        return (coordinate) >= 0 && (coordinate) < (upperBound);
    }
}
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

    public void invertPotraceWordWithMask(Point potraceWordIdentificationPixel, long mask) {
        int potraceWordArrayIndex = getPotraceWordArrayIndex(potraceWordIdentificationPixel);
        bitmap.map[potraceWordArrayIndex] ^= mask;
    }

    public void clearPotraceWord(Point potraceWordIdentificationPixel) {
        int potraceWordArrayIndex = getPotraceWordArrayIndex(potraceWordIdentificationPixel);
        bitmap.map[potraceWordArrayIndex] = 0;
    }

    public boolean areThereFilledPixelInWord(Point potraceWordIdentificationPixel) {
        int potraceWordArrayIndex = getPotraceWordArrayIndex(potraceWordIdentificationPixel);
        return bitmap.map[potraceWordArrayIndex] != 0;
    }

    public boolean isPixelFilled(Point pixel) {
        return isPixelInBitmap(pixel) && isPixelFilledWithoutBoundcheck(pixel);
    }

    public void setPixel(Point pixel) {
        long setPixelMask = BitMask.getOnePixelMaskForPosition(pixel.x);
        int potraceWordArrayIndex = getPotraceWordArrayIndex(pixel);
        if (isPixelInBitmap(pixel))
            bitmap.map[potraceWordArrayIndex] |=  setPixelMask;
    }

    public int getBeginningIndexOfWordWithPixel(Point pixel){
        return (pixel.x) & -Bitmap.PIXELINWORD;
    }

    public void clearCompleteBitmap() {
        for(int i = 0; i < bitmap.map.length; i ++)
            bitmap.map[i] = 0;
    }

    private void clearExcessPixel() {
        if (areThereExcessPixel())
            clearExcessPixelOfLastWordInLines();
    }

    private boolean areThereExcessPixel() {
        return getAmountOfExcessPixel() != 0;
    }

    private int getAmountOfExcessPixel() {
        return bitmap.w % Bitmap.PIXELINWORD;
    }

    private void clearExcessPixelOfLastWordInLines() {
        long mask = getMaskForExcessPixel();
        for (int y = 0; y < bitmap.h; y ++) 
            clearExcessPixelWithMaskInLine(mask, y);
    }

    private long getMaskForExcessPixel() {
        int indexOfLastPixelInLastWord = getAmountOfExcessPixel();
        return BitMask.getMultiplePixelMaskFromStartUntilPosition(indexOfLastPixelInLastWord);
    }

    private void clearExcessPixelWithMaskInLine(long excessPixelMask, int line) {
        Point lastPotraceWordInLineIdentificationPixel = new Point(bitmap.w,line);
        clearMultiplePixelOfWordWithMask(lastPotraceWordInLineIdentificationPixel,excessPixelMask);
    }

    private void clearMultiplePixelOfWordWithMask(Point potraceWordIdentificationPixel, long mask) {
        int potraceWordArrayIndex = getPotraceWordArrayIndex(potraceWordIdentificationPixel);
        bitmap.map[potraceWordArrayIndex] &= mask;
    }

    private int getPotraceWordArrayIndex(Point potraceWordIdentificationPixel) {
        int verticalPotraceWordLocation = potraceWordIdentificationPixel.y * bitmap.dy;
        int horizontalPotraceWordLocation = potraceWordIdentificationPixel.x / Bitmap.PIXELINWORD;
        return verticalPotraceWordLocation + horizontalPotraceWordLocation;
    }

    private boolean isPixelFilledWithoutBoundcheck(Point pixel) {
        long onePixelMask = BitMask.getOnePixelMaskForPosition(pixel.x);
        long pixelValue = getPixelValueWithMask(pixel, onePixelMask);
        return pixelValue != 0;
    }

    private long getPixelValueWithMask(Point potraceWordIdentificationPixel, long onePixelMask) {
        int potraceWordArrayIndex = getPotraceWordArrayIndex(potraceWordIdentificationPixel);
        return bitmap.map[potraceWordArrayIndex] & onePixelMask;
    }

    public boolean isPixelInBitmap(Point pixel) {
        return isCoordinateInRange(pixel.x, bitmap.w) && isCoordinateInRange(pixel.y, bitmap.h);
    }

    private boolean isCoordinateInRange(int coordinate, int upperBound) {
        return (coordinate) >= 0 && (coordinate) < (upperBound);
    }
}
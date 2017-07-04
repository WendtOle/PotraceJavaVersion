package refactored.potrace;

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

    public void ANDWordWithMask(Point positionOfWord, long mask) {
        bitmap.words[getAccessIndex(positionOfWord)] &= mask;
    }

    public long getAndWordWithMask(Point positionOfWord, long mask) {
        return bitmap.words[getAccessIndex(positionOfWord)] & mask;
    }

    public void flipBitsInWordWithMask(Point positionOfWord, long mask) {
        bitmap.words[getAccessIndex(positionOfWord)] ^= mask;
    }

    public void setWordToNull(Point positionOfWord) {
        bitmap.words[getAccessIndex(positionOfWord)] = 0;
    }

    public boolean areThereFilledPixelInWord(Point positionOfWord) {
        return bitmap.words[getAccessIndex(positionOfWord)] != 0;
    }

    public boolean isPixelFilled(Point positionOfPixel) {
        if (isPixelInRange(positionOfPixel))
            return getPixelValueWithoutBoundChecking(positionOfPixel);
        else
            return false;
    }

    public void setPixel(Point positionOfPixel) {
        if (isPixelInRange(positionOfPixel))
            bitmap.words[getAccessIndex(positionOfPixel)] |= MaskCreator.getOnePixelMaskForPosition(positionOfPixel.x);
    }

    public int getBeginningIndexOfWordWithPixel(Point positionOfPixel){
        return (positionOfPixel.x) & -Bitmap.PIXELINWORD;
    }

    public int getWithOfBitmap(){
        return bitmap.width;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void clearCompleteBitmap() {
        for(int i = 0; i < bitmap.words.length; i ++) {
            bitmap.words[i] = 0;
        }
    }

    private void clearExcessPixel() {
        if (areThereExcessPixel()) {
            clearExcessPixelOfLastWordInLines();
        }
    }

    private void clearExcessPixelOfLastWordInLines() {
        long mask = getMaskForExcessPixel();
        for (int y = 0; y < bitmap.height; y ++) {
            Point pixelInLastWordInCurrentLine = new Point(bitmap.width,y);
            ANDWordWithMask(pixelInLastWordInCurrentLine,mask);
        }
    }

    private boolean areThereExcessPixel() {
        return getPositionOfLastLineBitInWord() != 0;
    }

    private long getMaskForExcessPixel() {
        int indexOfLastBitInLastWordInLine = getPositionOfLastLineBitInWord();
        return MaskCreator.getMultiplePixelMaskUntilPosition(indexOfLastBitInLastWordInLine);
    }

    private int getPositionOfLastLineBitInWord() {
        return bitmap.width % Bitmap.PIXELINWORD;
    }

    private int getAccessIndex(Point pixelPosition) {
        return pixelPosition.y * bitmap.wordsPerScanLine + (pixelPosition.x / Bitmap.PIXELINWORD);
    }

    private boolean getPixelValueWithoutBoundChecking(Point pixel) {
        long pixelValue = getAndWordWithMask(pixel,MaskCreator.getOnePixelMaskForPosition(pixel.x));
        return(pixelValue != 0);
    }

    private boolean isPixelInRange(Point pixel) {
        return isCoordinateInRange(pixel.x, bitmap.width) && isCoordinateInRange(pixel.y, bitmap.height);
    }

    private boolean isCoordinateInRange(int coordinate, int upperBound) {
        return (coordinate) >= 0 && (coordinate) < (upperBound);
    }
}
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

    public void ORWordWithMask(Point positionOfWord, long mask) {
    }

    public void setWordToNull(Point positionOfWord) {
        bitmap.words[getAccessIndex(positionOfWord)] = 0;
    }

    public boolean areThereFilledPixelInWord(Point positionOfWord) {
        return bitmap.words[getAccessIndex(positionOfWord)] != 0;
    }

    public boolean isPixelFilled(Point positionOfPixel) {
        return isPixelInRange(positionOfPixel) && getPixelValueWithoutBoundChecking(positionOfPixel);
    }

    public void setPixel(Point positionOfPixel) {
        if (isPixelInRange(positionOfPixel))
            setPixelToValueWithoutBoundChecking(positionOfPixel);
    }

    public int getBeginningIndexOfWordWithPixel(Point positionOfPixel){
        return (positionOfPixel.x) & -Bitmap.PIXELINWORD;
    }

    public int getWithOfBitmap(){
        return bitmap.width;
    }

    private void clearExcessPixel() {
        if (bitmap.width % Bitmap.PIXELINWORD != 0) {
            long mask = shiftValueForLastWordInLine();
            for (int y = 0; y < bitmap.height; y ++) {
                ANDWordWithMask(new Point(bitmap.width,y),mask);
            }
        }
    }

    private long shiftValueForLastWordInLine() {
        int indexOfLastBitInLastWordInLine = bitmap.width % Bitmap.PIXELINWORD;
        return MaskCreator.getMultiplePixelMaskUntilPosition(indexOfLastBitInLastWordInLine);
    }

    private int getAccessIndex(Point pixelPosition) {
        return pixelPosition.y * bitmap.wordsPerScanLine + (pixelPosition.x / Bitmap.PIXELINWORD);
    }

    private void setPixelToValueWithoutBoundChecking(Point positionOfPixel) {
        bitmap.words[getAccessIndex(positionOfPixel)] |= MaskCreator.getOnePixelMaskForPosition(positionOfPixel.x);
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

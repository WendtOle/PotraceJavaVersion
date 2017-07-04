package refactored.potrace;

import java.awt.*;

/**
 * Created by andreydelany on 03.07.17.
 */
public class BitmapHandler implements BitmapHandlerInterface{

    public Bitmap bitmap;

    public BitmapHandler (Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void ANDWordWithMask(Point positionOfWord, long mask) {
        bitmap.words[positionOfWord.y * bitmap.wordsPerScanLine + (positionOfWord.x / Bitmap.PIXELINWORD)] &= mask;
    }

    public long getAndWordWithMask(Point positionOfWord, long mask) {
        return bitmap.words[positionOfWord.y * bitmap.wordsPerScanLine + (positionOfWord.x / Bitmap.PIXELINWORD)] & mask;
    }

    public void flipBitsInWordWithMask(Point positionOfWord, long mask) {
        bitmap.words[positionOfWord.y * bitmap.wordsPerScanLine + (positionOfWord.x / Bitmap.PIXELINWORD)] ^= mask;
    }

    public void ORWordWithMask(Point positionOfWord, long mask) {
    }

    public void setWordToNull(Point positionOfWord) {
        bitmap.words[positionOfWord.y * bitmap.wordsPerScanLine + (positionOfWord.x / Bitmap.PIXELINWORD)] = 0;
    }

    public boolean areThereFilledPixelInWord(Point positionOfWord) {
        return bitmap.words[positionOfWord.y * bitmap.wordsPerScanLine + (positionOfWord.x / Bitmap.PIXELINWORD)] != 0;
    }

    public boolean isPixelFilled(Point positionOfPixel) {
        return isPixelInRange(positionOfPixel) && getPixelValueWithoutBoundChecking(positionOfPixel);
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

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

    @Override
    public void flipBitsInWordWithMask(Point positionOfWord, long mask) {
        bitmap.words[positionOfWord.y * bitmap.wordsPerScanLine + (positionOfWord.x / Bitmap.PIXELINWORD)] ^= mask;
    }

    @Override
    public void ORWordWithMask(Point positionOfWord, long mask) {

    }

    public void setWordToNull(Point positionOfWord) {
        bitmap.words[positionOfWord.y * bitmap.wordsPerScanLine + (positionOfWord.x / Bitmap.PIXELINWORD)] = 0;
    }
}

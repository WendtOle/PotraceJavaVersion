package refactored.potrace;

import java.awt.*;

/**
 * Created by andreydelany on 03.07.17.
 */
public interface BitmapHandlerInterface {
    public void flipBitsInWordWithMask(Point positionOfWord, long mask);

    public void setWordToNull(Point positionOfWord);

    public void ANDWordWithMask(Point positionOfWord, long mask);

    public long getAndWordWithMask(Point positionOfWord, long mask);

    public boolean areThereFilledPixelInWord(Point positionOfWord);

    public void ORWordWithMask(Point positionOfWord, long mask);

    public boolean isPixelFilled(Point positionOfPixel);

    public void setPixel(Point positionOfPixel);

    public int getBeginningIndexOfWordWithPixel(Point positionOfPixel);

    public int getWithOfBitmap();

    public Bitmap getBitmap();

    public void clearCompleteBitmap();
}

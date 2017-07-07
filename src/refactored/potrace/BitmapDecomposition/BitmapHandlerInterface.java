package refactored.potrace.BitmapDecomposition;

import java.awt.*;

/**
 * Created by andreydelany on 03.07.17.
 */
public interface BitmapHandlerInterface {

    void flipBitsInWordWithMask(Point positionOfWord, long mask);

    void setWordToNull(Point positionOfWord);

    boolean areThereFilledPixelInWord(Point positionOfWord);

    boolean isPixelFilled(Point positionOfPixel);

    void setPixel(Point positionOfPixel);

    int getBeginningIndexOfWordWithPixel(Point positionOfPixel);

    int getWithOfBitmap();

    void clearCompleteBitmap();
}

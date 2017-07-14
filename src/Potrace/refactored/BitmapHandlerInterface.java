package Potrace.refactored;

import java.awt.*;

/**
 * Created by andreydelany on 03.07.17.
 */
public interface BitmapHandlerInterface {

    void invertPotraceWordWithMask(Point positionOfWord, long mask);

    void clearPotraceWord(Point positionOfWord);

    boolean areThereFilledPixelInWord(Point positionOfWord);

    boolean isPixelFilled(Point positionOfPixel);

    void setPixel(Point positionOfPixel);

    int getBeginningIndexOfWordWithPixel(Point positionOfPixel);

    void clearCompleteBitmap();

    boolean isPixelInBitmap(Point pixel);
}

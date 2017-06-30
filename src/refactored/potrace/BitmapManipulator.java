package refactored.potrace;

import java.awt.*;

/**
 * Created by andreydelany on 30.06.17.
 */
public class BitmapManipulator {

    Bitmap bitmap;

    public BitmapManipulator(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    void setPixelToValue(Point pixel, boolean b) {
        if (bitmap.isPixelInRange(pixel))
            setPixelToValueWithoutBoundChecking(pixel, b);
    }

    private void setPixelToValueWithoutBoundChecking(Point pixel, boolean shallPixelFilled) {
        if (shallPixelFilled)
            fillPixel(pixel);
        else
            clearPixel(pixel);
    }

    private void clearPixel(Point pixel){
        int accessIndex = bitmap.getAccessIndexOfWord(pixel);
        bitmap.words[accessIndex] = bitmap.words[accessIndex] & ~bitmap.getMaskForPosition(pixel.x);
    }

    private void fillPixel(Point pixel) {
        int accessIndex = bitmap.getAccessIndexOfWord(pixel);
        bitmap.words[accessIndex] = bitmap.words[accessIndex] | bitmap.getMaskForPosition(pixel.x);
    }
}

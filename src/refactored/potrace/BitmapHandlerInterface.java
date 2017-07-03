package refactored.potrace;

import java.awt.*;

/**
 * Created by andreydelany on 03.07.17.
 */
public interface BitmapHandlerInterface {
    public void flipBitsInWordWithMask(Point positionOfWord, long mask);

    public void setWordToNull(Point positionOfWord);

    public void ANDWordWithMask(Point positionOfWord, long mask);

    public void ORWordWithMask(Point positionOfWord, long mask);
}

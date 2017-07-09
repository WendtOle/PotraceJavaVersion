package DecompositionKindsForTesting;

import General.DecompositionInterface;
import refactored.Decompose;

import java.awt.*;

/**
 * Created by andreydelany on 09.07.17.
 */
public class OrigFindNextFilledPixel extends Decompose implements DecompositionInterface{

    @Override
    protected void findPathesOnBitmap() {
        if (startPointOfCurrentPath == null)
            startPointOfCurrentPath = new Point(0,workCopy.h-1);
        while ((original.Decompose.findnext(workCopy,startPointOfCurrentPath)))
            findAndAddPathToPathlist();

    }

}

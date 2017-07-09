package DecompositionKindsForTesting;

import General.Path;
import refactored.Decompose;

import java.awt.*;

/**
 * Created by andreydelany on 09.07.17.
 */
public class OriginFindFilledPixelAndFindPath extends Decompose {

    @Override
    protected void findPathesOnBitmap() {
        if (startPointOfCurrentPath == null)
            startPointOfCurrentPath = new Point(0,workCopy.h-1);
        while ((original.Decompose.findnext(workCopy,startPointOfCurrentPath)))
            findAndAddPathToPathlist();

    }

    @Override
    protected Path findPath() {
        int signOfPath = getSignOfPathFromOriginalBitmap(startPointOfCurrentPath);
        return original.Decompose.findpath(workCopy,startPointOfCurrentPath.x,startPointOfCurrentPath.y+1,signOfPath,param.turnpolicy);
    }
}

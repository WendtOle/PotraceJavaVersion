package DecompositionKindsForTesting;

import General.Path;
import refactored.Decompose;

/**
 * Created by andreydelany on 09.07.17.
 */
public class OriginalFindPath extends Decompose{

    @Override
    protected Path findPath() {
        int signOfPath = getSignOfPathFromOriginalBitmap(startPointOfCurrentPath);
        return original.Decompose.findpath(workCopy,startPointOfCurrentPath.x,startPointOfCurrentPath.y+1,signOfPath,param.turnpolicy);
    }
}

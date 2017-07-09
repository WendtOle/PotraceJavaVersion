package DecompositionKindsForTesting;

import General.Path;
import refactored.*;

import java.awt.*;

/**
 * Created by andreydelany on 09.07.17.
 */
public class FakeOriginal extends Decompose {
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

    @Override
    protected void structurePathlistAsTree(){
        original.Decompose.pathlist_to_tree(pathList,workCopy);
    }

    @Override
    protected void addPathToPathListIfBigEnough(Path currentPath){
        original.Decompose.xor_path(workCopy,currentPath);
        if (isPathBigEnough(currentPath.area,param.turdsize)) {
            pathList = refactored.List.elementInsertAtTheLastNextOfList(currentPath,pathList);
        }
    }

    private boolean isPathBigEnough(int actualArea, int areaOfTurd) {
        return actualArea > areaOfTurd;
    }
}

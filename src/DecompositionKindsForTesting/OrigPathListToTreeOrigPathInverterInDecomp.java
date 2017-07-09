package DecompositionKindsForTesting;

import General.List;
import General.Path;
import refactored.Decompose;

/**
 * Created by andreydelany on 09.07.17.
 */
public class OrigPathListToTreeOrigPathInverterInDecomp extends Decompose {

    @Override
    protected void structurePathlistAsTree(){
        original.Decompose.pathlist_to_tree(pathList,workCopy);
    }

    @Override
    protected void addPathToPathListIfBigEnough(Path currentPath){
        original.Decompose.xor_path(workCopy,currentPath);
        //pathInverterForWorkCopy.invertPathOnBitmap(currentPath);
        if (isPathBigEnough(currentPath.area,param.turdsize)) {
            pathList = List.elementInsertAtTheLastNextOfList(currentPath,pathList);
        }
    }

    private boolean isPathBigEnough(int actualArea, int areaOfTurd) {
        return actualArea > areaOfTurd;
    }
}

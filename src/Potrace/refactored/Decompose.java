package Potrace.refactored;

import Potrace.General.*;

public class Decompose implements DecompositionInterface {
    Bitmap bitmap;
    Param param;
    Path pathList;

    public Path getPathList(Bitmap bitmap, Param param) {
        this.bitmap = bitmap;
        this.param = param;
        decomposeBitmapIntoListOfPaths();
        return pathList;
    }

    private void decomposeBitmapIntoListOfPaths() {
        findPathsOnBitmap();
        structurePathsAsTree();
    }

    private void findPathsOnBitmap() {
        FindAllPathsOnBitmap findPathsOnBitmap = new FindAllPathsOnBitmap(bitmap,param);
        pathList = findPathsOnBitmap.getPathList();
    }

    private void structurePathsAsTree() {
        ListToTreeTransformationInterface pathListToTree = new ListToTreeTransformation(pathList,bitmap);
        pathList = pathListToTree.getTreeStructure();
    }
}
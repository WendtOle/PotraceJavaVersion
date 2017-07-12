package Potrace.refactored;

import Potrace.General.*;

public class Decompose implements DecompositionInterface {
    Bitmap bitmap;
    Param param;
    Path pathList;

    public Path getPathList(Bitmap generalBitmap, Param param) {
        this.bitmap = generalBitmap;
        this.param = param;
        decomposeBitmapIntoPathList();
        return pathList;
    }

    private void decomposeBitmapIntoPathList() {
        findPathsOnBitmap();
        structurePathListAsTree();
    }

    private void findPathsOnBitmap() {
        FindPathsOnBitmap findPathsOnBitmap = new FindPathsOnBitmap(bitmap,param);
        pathList = findPathsOnBitmap.getPathList();
    }

    private void structurePathListAsTree() {
        TreeStructurTransformationInterface pathListToTree = new TreeStructurTransformation(pathList,bitmap);
        pathList = pathListToTree.getTreeStructure();
    }

    @Override
    public Bitmap getWorkCopy() {
        return null;
    }
}
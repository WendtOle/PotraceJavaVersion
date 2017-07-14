package Potrace.refactored;

import Potrace.General.*;

public class ListToTreeTransformation implements ListToTreeTransformationInterface {
    private Path pathList;
    private Bitmap bitmap;

    public ListToTreeTransformation(Path pathList, Bitmap bitmap) {
        this.pathList = pathList;
        this.bitmap = bitmap;
        clearBitmap();
        transformPathIntoTree();
    }

    public Path getTreeStructure() {
        return pathList;
    }

    private void clearBitmap() {
        BitmapHandlerInterface manipulator = new BitmapHandler(bitmap);
        manipulator.clearCompleteBitmap();
    }

    private void transformPathIntoTree() {
        saveOriginalNextPointerToSiblingComponent();
        findSiblingAndChildrenAndSaveThemIntoNextAndChildrenComponent();
        copySiblingStructureFromNextToSiblingComponent();
        reconstructNextComponentFromChildrenAndSiblingComponent();
    }

    private void saveOriginalNextPointerToSiblingComponent() {
        PathRestructuring pathRestructuring = new PathRestructuring(pathList);
        pathList = pathRestructuring.saveOriginalNextPointerToSiblingComponent();
    }

    private void findSiblingAndChildrenAndSaveThemIntoNextAndChildrenComponent() {
        ChildrenAndSiblingFinder childrenAndSiblingFinder = new ChildrenAndSiblingFinder(pathList,bitmap);
        pathList = childrenAndSiblingFinder.getTreeTransformedPathStructure();
    }

    private void copySiblingStructureFromNextToSiblingComponent() {
        PathRestructuring pathRestructuring = new PathRestructuring(pathList);
        pathList = pathRestructuring.copySiblingStructureFromNextToSiblingComponent();
    }

    private void reconstructNextComponentFromChildrenAndSiblingComponent() {
        NextComponentReconstruction reconstruction = new NextComponentReconstruction(pathList);
        pathList = reconstruction.getPathWithReconstructedNext();
    }
}
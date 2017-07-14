package Potrace.refactored;

import Potrace.General.*;

/**
 * Created by andreydelany on 28.06.17.
 */
public class ListToTreeTransformation implements ListToTreeTransformationInterface {
    Path pathList;
    Bitmap bitmap;

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
        BitmapHandlerInterface manipulator = new BitmapHandler(this.bitmap);
        manipulator.clearCompleteBitmap();
    }

    private void transformPathIntoTree() {
        saveOriginalNextPointerToSiblingComponent();
        findChildrenAndSiblingsAndSaveThemIntoNextAndChildrenComponent();
        copySiblingStructurFromNextToSiblingComponent();
        reconstructNextComponentFromChildrenAndSiblingComponent();
    }

    private void saveOriginalNextPointerToSiblingComponent() {
        PathRestructerer pathRestructerer = new PathRestructerer(pathList);
        pathList = pathRestructerer.saveOriginalNextPointerToSiblingComponent();
    }

    private void findChildrenAndSiblingsAndSaveThemIntoNextAndChildrenComponent() {
        ChildrenAndSiblingFinder childrenAndSiblingFinder = new ChildrenAndSiblingFinder(pathList,bitmap);
        pathList = childrenAndSiblingFinder.getTreeTransformedPathStructure();
    }

    private void copySiblingStructurFromNextToSiblingComponent() {
        PathRestructerer pathRestructerer = new PathRestructerer(pathList);
        pathList = pathRestructerer.copySiblingStructurFromNextToSiblingComponent();
    }

    private void reconstructNextComponentFromChildrenAndSiblingComponent() {
        NextComponentReconstruction reconstruction = new NextComponentReconstruction(pathList);
        pathList = reconstruction.getPathWithReconstructedNext();
    }
}
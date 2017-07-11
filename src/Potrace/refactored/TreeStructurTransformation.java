package Potrace.refactored;

import Potrace.General.*;

/**
 * Created by andreydelany on 28.06.17.
 */
public class TreeStructurTransformation implements TreeStructurTransformationInterface{
    Path pathList;
    Bitmap bitmap;

    public TreeStructurTransformation(Path pathList, Bitmap bitmap) {
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
        findChildrenAndSiblings();
        copySiblingStructurFromNextToSiblingComponent();
        reconstructNextComponentFromChildrenAndSiblingComponent();
    }

    private void saveOriginalNextPointerToSiblingComponent() {
        pathList = PathReStructrurer.saveOriginalNextPointerToSiblingComponent(pathList);
    }

    private void findChildrenAndSiblings() {
        ChildrenAndSiblingFinder childrenAndSiblingFinder = new ChildrenAndSiblingFinder(pathList,bitmap);
        pathList = childrenAndSiblingFinder.getTreeTransformedPathStructure();
    }

    private void copySiblingStructurFromNextToSiblingComponent() {
        pathList = PathReStructrurer.copySiblingStructurFromNextToSiblingComponent(pathList);
    }

    private void reconstructNextComponentFromChildrenAndSiblingComponent() {
        NextComponentReconstruction reconstruction = new NextComponentReconstruction(pathList);
        pathList = reconstruction.getPathWithReconstructedNext();
    }
}
package refactored.potrace;

import java.awt.*;

/**
 * Created by andreydelany on 29.06.17.
 */
public class ChildrenAndSiblingFinder {

    Path pathList;
    BitmapHandlerInterface bitmapHandler;
    ClearBitmapWithBBox bitmapClearer;
    PathOnBitmapInverter inverter;
    Path pathesToOrder, pathesThatNeedToProcess, referencePath;
    BBox boundingBox;

    public ChildrenAndSiblingFinder(Path pathList, Bitmap bitmap){
        this.pathList = pathList;
        this.bitmapHandler = new BitmapHandler(bitmap);
        this.bitmapClearer = new ClearBitmapWithBBox(bitmap);
        this.inverter = new PathOnBitmapInverter(bitmap);
        transformIntoTreeStructure();
    }

    public Path getPath(){
        return pathList;
    }

    private void transformIntoTreeStructure() {
        pathesThatNeedToProcess = pathList;
        while (pathesThatNeedToProcess != null) {
            processPathes();
        }
    }

    private void processPathes() {
        initializePathes();
        inverter.invertPathOnBitmap(referencePath);
        determineChildrenAndSiblings();
        scheduleAddedChildrenAndSiblingsForFurtherProcessing();
    }

    private void initializePathes() {
        // unlink first sublist
        pathesToOrder = pathesThatNeedToProcess;
        pathesThatNeedToProcess = pathesThatNeedToProcess.childlist;
        pathesToOrder.childlist = null;

        // unlink first Path
        referencePath = pathesToOrder;
        pathesToOrder = pathesToOrder.next;
        referencePath.next = null;
    }

    private void determineChildrenAndSiblings() {
        boundingBox = new BBox(referencePath);
        orderPathListWetherInsideOrOutsideOfBoundingBox();
        bitmapClearer.clearBitmapWithBBox(boundingBox);
    }

    private void orderPathListWetherInsideOrOutsideOfBoundingBox() {
        for (Path currentPath=pathesToOrder; currentPath != null; currentPath=pathesToOrder) {
            pathesToOrder=currentPath.next;
            currentPath.next=null;

            if (isPathBelowBoundingBox(currentPath)) {
                addRemainingPathesAsSiblings(currentPath);
                return;
            } else {
                if (isPathInsideReferencePath(currentPath)) {
                    addPathAsChild(currentPath);
                } else {
                    addPathAsSibling(currentPath);
                }
            }
        }
    }

    private void addRemainingPathesAsSiblings(Path currentPath) {
        addPathAsSibling(currentPath);
        referencePath.next = Path.insertListAtTheEndOfList(pathesToOrder,referencePath.next);
    }

    private void addPathAsChild(Path currentPath) {
        referencePath.childlist = Path.insertElementAtTheEndOfList(currentPath,referencePath.childlist);
    }

    private void addPathAsSibling(Path currentPath) {
        referencePath.next = Path.insertElementAtTheEndOfList(currentPath,referencePath.next);
    }

    private boolean isPathBelowBoundingBox(Path path){
        return path.priv.pt[0].y <= boundingBox.y0;
    }

    private boolean isPathInsideReferencePath(Path path){
        return bitmapHandler.isPixelFilled(new Point(path.priv.pt[0].x, path.priv.pt[0].y - 1));
    }

    private void scheduleAddedChildrenAndSiblingsForFurtherProcessing() {
        if (hasReferencePathSiblings())
            schedulePathesForFurtherProcessing(referencePath.next);
        if (hasReferencePathChildren())
            schedulePathesForFurtherProcessing(referencePath.childlist);
    }

    private void schedulePathesForFurtherProcessing(Path path) {
        path.childlist = pathesThatNeedToProcess;
        pathesThatNeedToProcess = path;
    }

    private boolean hasReferencePathSiblings() {
        return referencePath.next != null;
    }

    private boolean hasReferencePathChildren() {
        return referencePath.childlist != null;
    }
}

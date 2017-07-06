package refactored.potrace;

import java.awt.*;

/**
 * Created by andreydelany on 29.06.17.
 */
public class ChildrenAndSiblingFinder {

    Path pathList;
    BitmapHandlerInterface bitmapHandler;
    ClearBitmapWithBBox bitmapClearer;
    PathInverter inverter;
    Path pathesToOrder, referencePath;
    BBox boundingBox;
    PathQueueInterface pathQueue;

    public ChildrenAndSiblingFinder(Path pathList, Bitmap bitmap){
        this.pathList = pathList;
        this.bitmapHandler = new BitmapHandler(bitmap);
        this.bitmapClearer = new ClearBitmapWithBBox(bitmap);
        this.inverter = new PathInverter(bitmap);
        transformIntoTreeStructure();
    }

    public Path getPath(){
        return pathList;
    }

    private void transformIntoTreeStructure() {
        pathQueue = new PathQueue(pathList);
        while (pathQueue.stillNeedToProcessPathes()) {
            processPathes();
        }
    }

    private void processPathes(){
        assignNextPathesToProcess();
        inverter.invertPathOnBitmap(referencePath);
        determineChildrenAndSiblings();
        pathQueue.updateQueue(referencePath);
    }

    private void assignNextPathesToProcess() {
        Path[] pathesNextToProcess = pathQueue.getNextPathes();
        referencePath = pathesNextToProcess[0];
        pathesToOrder = pathesNextToProcess[1];
    }

    private void determineChildrenAndSiblings() {
        boundingBox = new BBox(referencePath);
        orderPathListWetherInsideOrOutsideOfBoundingBox();
        bitmapClearer.clearBitmapWithBBox(boundingBox);
    }

    private void orderPathListWetherInsideOrOutsideOfBoundingBox() {
        Path currentPath=pathesToOrder;
        Boolean isNotFinishedWithOrdering = true;
        while(currentPath != null && isNotFinishedWithOrdering) {
            pathesToOrder=currentPath.next;
            currentPath.next=null;

            if (isPathBelowBoundingBox(currentPath)) {
                addRemainingPathesAsSiblings(currentPath);
                isNotFinishedWithOrdering = false;
            } else {
                if (isPathInsideReferencePath(currentPath)) {
                    addPathAsChild(currentPath);
                } else {
                    addPathAsSibling(currentPath);
                }
            }
            currentPath=pathesToOrder;
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
}

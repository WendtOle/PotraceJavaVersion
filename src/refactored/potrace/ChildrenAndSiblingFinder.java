package refactored.potrace;

/**
 * Created by andreydelany on 29.06.17.
 */
public class ChildrenAndSiblingFinder {

    Path pathList;
    Bitmap bitmap;
    Path pathesToOrder, pathesThatNeedToProcess, currentPath;

    public ChildrenAndSiblingFinder(Path pathList, Bitmap bitmap){
        this.pathList = pathList;
        this.bitmap = bitmap;
        transformIntoTreeStructure();
    }

    private void transformIntoTreeStructure() {
        pathesThatNeedToProcess = pathList;
        while (pathesThatNeedToProcess != null) {

            initializePathes();

            bitmap.invertPathOnBitmap(currentPath);

            determineChildrenAndSiblings();

            pathesThatNeedToProcess = scheduleOrderdPathesForFurtherProcessing();
        }
    }

    private void initializePathes() {
        // unlink first sublist
        pathesToOrder = pathesThatNeedToProcess;
        pathesThatNeedToProcess = pathesThatNeedToProcess.childlist;
        pathesToOrder.childlist = null;

        // unlink first Path
        currentPath = pathesToOrder;
        pathesToOrder = pathesToOrder.next;
        currentPath.next = null;
    }

    private void determineChildrenAndSiblings() {
        BBox boundingBoxOfOuterPath = new BBox(currentPath);
        orderPathListWetherInsideOrOutsideOfBoundingBox(boundingBoxOfOuterPath);
        bitmap.clearBitmapWithBBox(boundingBoxOfOuterPath);
    }

    private void orderPathListWetherInsideOrOutsideOfBoundingBox(BBox boundingBoxOfOuterPath) {
        for (Path currentPath=pathesToOrder; currentPath != null; currentPath=pathesToOrder) {
            pathesToOrder=currentPath.next;
            currentPath.next=null;

            boolean isCurrentPathBelowBoundingBox = currentPath.priv.pt[0].y <= boundingBoxOfOuterPath.y0;
            if (isCurrentPathBelowBoundingBox) {
                this.currentPath.next = Path.insertElementAtTheEndOfList(currentPath,this.currentPath.next);
                this.currentPath.next = Path.insertListAtTheEndOfList(pathesToOrder,this.currentPath.next);
                return;
            }
            boolean isCurrentPathInsideBoundingBox = bitmap.getPixelValue(currentPath.priv.pt[0].x, currentPath.priv.pt[0].y - 1);
            if (isCurrentPathInsideBoundingBox) {
                this.currentPath.childlist = Path.insertElementAtTheEndOfList(currentPath,this.currentPath.childlist);
            } else {
                this.currentPath.next = Path.insertElementAtTheEndOfList(currentPath,this.currentPath.next);
            }
        }
    }

    private Path scheduleOrderdPathesForFurtherProcessing() {
        boolean hasCurrentPathOtherPathesOutside = currentPath.next != null;
        if (hasCurrentPathOtherPathesOutside) {
            currentPath.next.childlist = pathesThatNeedToProcess;
            pathesThatNeedToProcess = currentPath.next;
        }
        boolean hasCurrentPathOtherPathesInside = currentPath.childlist != null;
        if (hasCurrentPathOtherPathesInside) {
            currentPath.childlist.childlist = pathesThatNeedToProcess;
            pathesThatNeedToProcess = currentPath.childlist;
        }
        return pathesThatNeedToProcess;
    }

    public Path getPath(){
        return pathList;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

}

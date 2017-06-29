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

            determineChildrenAndSiblings(pathesToOrder, currentPath);

            pathesThatNeedToProcess = scheduleOrderdPathesForFurtherProcessing(pathesThatNeedToProcess, currentPath);
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

    private Path scheduleOrderdPathesForFurtherProcessing(Path pathesThatNeedToProcess, Path currentPath) {
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

    private void determineChildrenAndSiblings(Path pathListToTest, Path outerPath) {
        BBox boundingBoxOfOuterPath = new BBox(outerPath);
        orderPathListWetherInsideOrOutsideOfBoundingBox(pathListToTest, outerPath, boundingBoxOfOuterPath);
        bitmap.clearBitmapWithBBox(boundingBoxOfOuterPath);
    }

    private void orderPathListWetherInsideOrOutsideOfBoundingBox(Path pathListToTest, Path outerPath, BBox boundingBoxOfOuterPath) {
        for (Path currentPath=pathListToTest; currentPath != null; currentPath=pathListToTest) {
            pathListToTest=currentPath.next;
            currentPath.next=null;

            boolean isCurrentPathBelowBoundingBox = currentPath.priv.pt[0].y <= boundingBoxOfOuterPath.y0;
            if (isCurrentPathBelowBoundingBox) {
                outerPath.next = Path.insertElementAtTheEndOfList(currentPath,outerPath.next);
                outerPath.next = Path.insertListAtTheEndOfList(pathListToTest,outerPath.next);
                return;
            }
            boolean isCurrentPathInsideBoundingBox = bitmap.getPixelValue(currentPath.priv.pt[0].x, currentPath.priv.pt[0].y - 1);
            if (isCurrentPathInsideBoundingBox) {
                outerPath.childlist = Path.insertElementAtTheEndOfList(currentPath,outerPath.childlist);
            } else {
                outerPath.next = Path.insertElementAtTheEndOfList(currentPath,outerPath.next);
            }
        }
    }

    public Path getPath(){
        return pathList;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

}

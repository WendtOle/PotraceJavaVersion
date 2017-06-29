package refactored.potrace;

/**
 * Created by andreydelany on 29.06.17.
 */
public class ChildrenAndSiblingFinder {

    Path pathList;
    Bitmap bitmap;
    Path pathesToOrder, pathesThatNeedToProcess, referencePath;
    BBox boundingBox;

    public ChildrenAndSiblingFinder(Path pathList, Bitmap bitmap){
        this.pathList = pathList;
        this.bitmap = bitmap;
        transformIntoTreeStructure();
    }

    private void transformIntoTreeStructure() {
        pathesThatNeedToProcess = pathList;
        while (pathesThatNeedToProcess != null) {

            initializePathes();

            bitmap.invertPathOnBitmap(referencePath);

            determineChildrenAndSiblings();

            scheduleAddedChildrenAndSiblingsForFurtherProcessing();
        }
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
        bitmap.clearBitmapWithBBox(boundingBox);
    }

    private void orderPathListWetherInsideOrOutsideOfBoundingBox() {
        for (Path currentPath=pathesToOrder; currentPath != null; currentPath=pathesToOrder) {
            pathesToOrder=currentPath.next;
            currentPath.next=null;

            if (isPathBelowBoundingBox(currentPath)) {
                referencePath.next = Path.insertElementAtTheEndOfList(currentPath,referencePath.next);
                referencePath.next = Path.insertListAtTheEndOfList(pathesToOrder,referencePath.next);
                return;
            }

            if (isPathInsideReferencePath(currentPath)) {
                referencePath.childlist = Path.insertElementAtTheEndOfList(currentPath,referencePath.childlist);
            } else {
                referencePath.next = Path.insertElementAtTheEndOfList(currentPath,referencePath.next);
            }
        }
    }

    private boolean isPathBelowBoundingBox(Path path){
        return path.priv.pt[0].y <= boundingBox.y0;
    }

    private boolean isPathInsideReferencePath(Path path){
        return bitmap.getPixelValue(path.priv.pt[0].x, path.priv.pt[0].y - 1);
    }

    private void scheduleAddedChildrenAndSiblingsForFurtherProcessing() {
        if (hasReferencePathSiblings()) {
            referencePath.next.childlist = pathesThatNeedToProcess;
            pathesThatNeedToProcess = referencePath.next;
        }
        if (hasReferencePathChildren()) {
            referencePath.childlist.childlist = pathesThatNeedToProcess;
            pathesThatNeedToProcess = referencePath.childlist;
        }
    }

    private boolean hasReferencePathSiblings() {
        return referencePath.next != null;
    }

    private boolean hasReferencePathChildren() {
        return referencePath.childlist != null;
    }

    public Path getPath(){
        return pathList;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

}

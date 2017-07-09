package refactored;

import General.*;
import java.awt.*;

/**
 * Created by andreydelany on 07.07.17.
 */
public class DetermineHirachy {

    BitmapHandlerInterface bitmapHandler;
    ClearBitmapWithBoundingBox bitmapClearer;
    PathInverter inverter;
    BoundingBox boundingBox;
    Path referencePath, pathesToOrder;

    public DetermineHirachy(Bitmap bitmap) {
        this.bitmapHandler = new BitmapHandler(bitmap);
        this.bitmapClearer = new ClearBitmapWithBoundingBox(bitmap);
        this.inverter = new PathInverter(bitmap);
    }

    public Path getHierarchicallyOrderedPathes(Path referencePath, Path pathesToOrder) {
        saveInputParameteres(referencePath, pathesToOrder);
        determineHirachyBetweenPathes();
        return referencePath;
    }

    private void saveInputParameteres(Path referencePath, Path pathesToOrder) {
        this.referencePath = referencePath;
        this.pathesToOrder = pathesToOrder;
    }

    private void determineHirachyBetweenPathes() {
        markReferencePath();
        determinePositionToReferencePath();
        unmarkReferencePath();
    }

    private void markReferencePath() {
        inverter.invertPathOnBitmap(referencePath);
        boundingBox = new BoundingBox(referencePath);
    }

    private void unmarkReferencePath() {
        bitmapClearer.clearBitmapWithBBox(boundingBox);
    }

    private void determinePositionToReferencePath() {
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
        referencePath.next = List.listInsertAtTheLastNextOfList(pathesToOrder,referencePath.next);
    }

    private void addPathAsChild(Path currentPath) {
        referencePath.childlist = List.elementInsertAtTheLastNextOfList(currentPath,referencePath.childlist);
    }

    private void addPathAsSibling(Path currentPath) {
        referencePath.next = List.elementInsertAtTheLastNextOfList(currentPath,referencePath.next);
    }

    private boolean isPathBelowBoundingBox(Path path){
        return path.priv.pt[0].y <= boundingBox.y0;
    }

    private boolean isPathInsideReferencePath(Path path){
        return bitmapHandler.isPixelFilled(new Point(path.priv.pt[0].x, path.priv.pt[0].y - 1));
    }
}
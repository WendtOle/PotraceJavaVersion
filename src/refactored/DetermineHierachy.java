package refactored;

import General.Bitmap;
import General.List;
import General.Path;

import java.awt.*;

/**
 * Created by andreydelany on 07.07.17.
 */
public class DetermineHierachy {

    BitmapHandlerInterface bitmapHandler;
    ClearBitmapWithBoundingBox bitmapClearer;
    PathInverter inverter;
    BoundingBox boundingBox;
    Path referencePath, pathesToOrder;
    private Boolean orderingProcessCanceled;
    private Path currentPath;

    public DetermineHierachy(Bitmap bitmap) {
        this.bitmapHandler = new BitmapHandler(bitmap);
        this.bitmapClearer = new ClearBitmapWithBoundingBox(bitmap);
        this.inverter = new PathInverter(bitmap);
    }

    public Path getHierarchicallyOrderedPathes(Path referencePath, Path pathesToOrder) {
        setInitialState(referencePath, pathesToOrder);
        determineHirachyBetweenPathes();
        return this.referencePath;
    }

    private void setInitialState(Path referencePath, Path pathesToOrder) {
        this.referencePath = referencePath;
        this.pathesToOrder = pathesToOrder;
        orderingProcessCanceled = false;
    }

    private void determineHirachyBetweenPathes() {
        markLocationOfReferencePath();
        determinePositionOfPathesRelativeToReferencePath();
        unMarkLocationOfReferencePath();
    }

    private void markLocationOfReferencePath() {
        inverter.invertPathOnBitmap(referencePath);
        boundingBox = new BoundingBox(referencePath);
    }

    private void determinePositionOfPathesRelativeToReferencePath() {
        setNextPathesToOrder();
        while(shouldContinueOrdering())
            setPositionOfNextPathRelativeToReferencePath();
    }

    private void setNextPathesToOrder() {
        currentPath = pathesToOrder;
    }

    private boolean shouldContinueOrdering() {
        boolean shouldOrderingProcessContinue = !orderingProcessCanceled;
        return currentPath != null && shouldOrderingProcessContinue;
    }

    private void setPositionOfNextPathRelativeToReferencePath() {
        setCurrentPath();
        setPositionOfCurrentPath();
        setNextPathesToOrder();
    }

    private void setCurrentPath() {
        pathesToOrder= currentPath.next;
        currentPath.next=null;
    }

    private void setPositionOfCurrentPath() {
        if (isPathBelowBoundingBox())
            cancelOrderingProcessAndSaveRemainingPathes();
        else
            addPathToListDependingOnLocation();
    }

    private boolean isPathBelowBoundingBox(){
        return currentPath.priv.pt[0].y <= boundingBox.y0;
    }

    private void cancelOrderingProcessAndSaveRemainingPathes() {
        addRemainingPathesAsSiblings();
        orderingProcessCanceled = true;
    }

    private void addRemainingPathesAsSiblings() {
        addPathAsSibling();
        referencePath.next = List.listInsertAtTheLastNextOfList(pathesToOrder,referencePath.next);
    }

    private void addPathToListDependingOnLocation() {
        if (isPathInsideReferencePath())
            addPathAsChild();
        else
            addPathAsSibling();
    }

    private boolean isPathInsideReferencePath(){
        return bitmapHandler.isPixelFilled(new Point(currentPath.priv.pt[0].x, currentPath.priv.pt[0].y - 1));
    }

    private void addPathAsChild() {
        referencePath.childlist = List.elementInsertAtTheLastNextOfList(currentPath,referencePath.childlist);
    }

    private void addPathAsSibling() {
        referencePath.next = List.elementInsertAtTheLastNextOfList(currentPath,referencePath.next);
    }

    private void unMarkLocationOfReferencePath() {
        bitmapClearer.clearBitmapWithBBox(boundingBox);
    }
}
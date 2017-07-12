package Potrace.refactored;

import Potrace.General.Bitmap;
import Potrace.General.Path;

/**
 * Created by andreydelany on 29.06.17.
 */
public class ChildrenAndSiblingFinder {
    Path pathList;
    ClearPathWithBoundingBox bitmapClearer;
    PathInverter inverter;
    BitmapHandlerInterface bitmapHandler;
    PathManager pathManager;
    BoundingBox boundingBox;
    boolean shouldContinueOrdering;

    public ChildrenAndSiblingFinder(Path pathList, Bitmap bitmap){
        this.pathList = pathList;
        bitmapClearer = new ClearPathWithBoundingBox(bitmap);
        this.inverter = new PathInverter(bitmap);
        bitmapHandler = new BitmapHandler(bitmap);
        pathManager = new PathManager(pathList);
    }

    public Path getTreeTransformedPathStructure(){
        orderPathsOnFirstLevel();
        return pathList;
    }

    private void orderPathsOnFirstLevel() {
        while (stillNeedToOrderPathsOnFirstLevel()) {
            pathManager.initializingPathForFirstLevelOrdering();
            orderOnFirstLevelInRelationToReferencePath();
        }
    }

    private boolean stillNeedToOrderPathsOnFirstLevel() {
        return pathManager.stillNeedToProcessPathes();
    }

    private void orderOnFirstLevelInRelationToReferencePath() {
        markLocationOfReferencePath();
        orderPathsRelativeToReferencePath();
        unmarkLocationOfReferencePath();
    }

    private void markLocationOfReferencePath() {
        Path referencePath = pathManager.getCurrentReferencePath();
        inverter.invertPathOnBitmap(referencePath);
        boundingBox = new BoundingBox(referencePath);
    }

    private void orderPathsRelativeToReferencePath() {
        shouldContinueOrdering = true;
        while (shouldContinueOrdering())
            ordering();
    }

    private void ordering() {
        pathManager.preparePathsThatWillLaterBeLookedAt();
        if (isCurrentPathBelowReferencePath())
            cancelOrderingProcess();
        else
            addCurrentPathToReferencePath();
    }

    private boolean shouldContinueOrdering() {
        return pathManager.stillNeedToOrderPaths() && shouldContinueOrdering;
    }

    private void cancelOrderingProcess() {
        pathManager.addRemainingPathsAsSibling();
        shouldContinueOrdering = false;
    }

    private void addCurrentPathToReferencePath() {
        if (isCurrentPathInsideReferencePath())
            pathManager.addPathAsChild();
        else
            pathManager.addPathAsSibling();
    }

    private boolean isCurrentPathInsideReferencePath() {
        return bitmapHandler.isPixelFilled(pathManager.getFirstPointOfCurrentPath());
    }

    private boolean isCurrentPathBelowReferencePath() {
        return pathManager.getUpperBoundOfPath() <= boundingBox.y0;
    }

    private void unmarkLocationOfReferencePath() {
        bitmapClearer.clearBitmapWithBoundingBox(boundingBox);
    }
}
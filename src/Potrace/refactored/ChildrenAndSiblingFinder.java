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
    PathOrganizer pathOrganizer;
    PathBoundingBox boundingBox;
    boolean shouldContinueOrdering;

    public ChildrenAndSiblingFinder(Path pathList, Bitmap bitmap){
        this.pathList = pathList;
        initializeHelperClasses(bitmap);
    }

    public Path getTreeTransformedPathStructure(){
        orderPathsOnFirstLevel();
        return pathList;
    }

    private void initializeHelperClasses(Bitmap bitmap) {
        bitmapClearer = new ClearPathWithBoundingBox(bitmap);
        inverter = new PathInverter(bitmap);
        bitmapHandler = new BitmapHandler(bitmap);
        pathOrganizer = new PathOrganizer(pathList);
    }

    private void orderPathsOnFirstLevel() {
        while (pathOrganizer.stillNeedToLevelOneOrder()) {
            pathOrganizer.initializingPathForLevelOneOrdering();
            orderOnLevelOne();
        }
    }

    private void orderOnLevelOne() {
        markLocationOfReferencePath();
        orderPathsRelativeToReferencePath();
        unmarkLocationOfReferencePath();
        scheduleOrderPathsForNextOrderingStep();
    }

    private void scheduleOrderPathsForNextOrderingStep() {
        pathOrganizer.scheduleOrderdPathsForNextOrderingStep();
    }

    private void markLocationOfReferencePath() {
        Path referencePath = pathOrganizer.getCurrentReferencePath();
        inverter.invertPathOnBitmap(referencePath);
        boundingBox = new PathBoundingBox(referencePath);
    }

    private void orderPathsRelativeToReferencePath() {
        shouldContinueOrdering = true;
        while(shouldContinueOrdering()){
            levelTwoOrdering();
        }
    }

    private void levelTwoOrdering() {
        pathOrganizer.initializePathForLevelTwoOrdering();
        ordering();
    }

    private void ordering() {
        if (isCurrentPathBelowReferencePath())
            cancelOrderingProcess();
        else
            addCurrentPathToReferencePath();
    }

    private boolean shouldContinueOrdering() {
        return pathOrganizer.stillNeedToLevelTwoOrder() && shouldContinueOrdering;
    }

    private void cancelOrderingProcess() {
        pathOrganizer.addRemainingPathsAsSibling();
        shouldContinueOrdering = false;
    }

    private void addCurrentPathToReferencePath() {
        if (isCurrentPathInsideReferencePath())
            pathOrganizer.addPathAsChild();
        else
            pathOrganizer.addPathAsSibling();
    }

    private boolean isCurrentPathInsideReferencePath() {
        return bitmapHandler.isPixelFilled(pathOrganizer.getFirstPointOfCurrentPath());
    }

    private boolean isCurrentPathBelowReferencePath() {
        return pathOrganizer.getUpperBoundOfPath() <= boundingBox.y0;
    }

    private void unmarkLocationOfReferencePath() {
        bitmapClearer.clear(boundingBox);
    }
}
package Potrace.refactored;

import Potrace.General.Bitmap;
import Potrace.General.Path;

public class ChildrenAndSiblingFinder {
    private Path pathList;
    private ClearPathWithBoundingBox bitmapClearer;
    private PathInverter inverter;
    private BitmapHandlerInterface bitmapHandler;
    private PathOrganizer pathOrganizer;
    private PathBoundingBox boundingBox;
    private boolean shouldContinueDetermineChildrenAndSiblings;

    public ChildrenAndSiblingFinder(Path pathList, Bitmap bitmap){
        this.pathList = pathList;
        initializeHelperClasses(bitmap);
    }

    public Path getTreeTransformedPathStructure(){
        goThroughAllPathsAndDetermineChildrenAndSiblings();
        return pathList;
    }

    private void initializeHelperClasses(Bitmap bitmap) {
        bitmapClearer = new ClearPathWithBoundingBox(bitmap);
        inverter = new PathInverter(bitmap);
        bitmapHandler = new BitmapHandler(bitmap);
        pathOrganizer = new PathOrganizer(pathList);
    }

    private void goThroughAllPathsAndDetermineChildrenAndSiblings() {
        while (pathOrganizer.stillNeedToProcessPaths()) {
            pathOrganizer.unlinkAllDifferentParts();
            determineChildrenAndSiblingForReferencePath();
        }
    }

    private void determineChildrenAndSiblingForReferencePath() {
        markLocationOfReferencePath();
        determineChildrenAndSiblingsRelativeToReferencePath();
        unmarkLocationOfReferencePath();
        scheduleOrderPathsForNextOrderingStep();
    }

    private void markLocationOfReferencePath() {
        Path referencePath = pathOrganizer.getCurrentReferencePath();
        inverter.invertPathOnBitmap(referencePath);
        boundingBox = new PathBoundingBox(referencePath);
    }

    private void determineChildrenAndSiblingsRelativeToReferencePath() {
        shouldContinueDetermineChildrenAndSiblings = true;
        while(shouldContinueDetermine())
            determineChildrenAndSiblingsOfReferencePath();
    }

    private boolean shouldContinueDetermine() {
        return pathOrganizer.stillNeedToDetermineRelationOfPathsToReferencePath() && shouldContinueDetermineChildrenAndSiblings;
    }

    private void determineChildrenAndSiblingsOfReferencePath() {
        pathOrganizer.initializePathsForDetermineRelationToReferencePath();
        determineForCurrentPath();
    }

    private void determineForCurrentPath() {
        if (isCurrentPathBelowReferencePath())
            cancelOrderingProcess();
        else
            addCurrentPathToReferencePath();
    }

    private boolean isCurrentPathInsideReferencePath() {
        return bitmapHandler.isPixelFilled(pathOrganizer.getFirstPointOfCurrentPath());
    }

    private void cancelOrderingProcess() {
        pathOrganizer.addRemainingPathsAsSibling();
        shouldContinueDetermineChildrenAndSiblings = false;
    }

    private void addCurrentPathToReferencePath() {
        if (isCurrentPathInsideReferencePath())
            pathOrganizer.addPathAsChild();
        else
            pathOrganizer.addPathAsSibling();
    }

    private boolean isCurrentPathBelowReferencePath() {
        return pathOrganizer.getUpperBoundOfPath() <= boundingBox.y0;
    }

    private void scheduleOrderPathsForNextOrderingStep() {
        pathOrganizer.scheduleOrderedPathsForNextOrderingStep();
    }

    private void unmarkLocationOfReferencePath() {
        bitmapClearer.clear(boundingBox);
    }
}
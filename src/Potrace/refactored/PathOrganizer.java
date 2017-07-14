package Potrace.refactored;

import Potrace.General.List;
import Potrace.General.Path;

import java.awt.*;

class PathOrganizer {
    private Path pathsThatNeedToProcess;
    private Path pathsToOrder;
    private Path referencePath;
    private Path currentPath;

    public PathOrganizer(Path pathList){
        pathsThatNeedToProcess = pathList;
    }

    public boolean stillNeedToProcessPaths() {
        return pathsThatNeedToProcess != null;
    }

    public boolean stillNeedToDetermineRelationOfPathsToReferencePath(){
        currentPath = pathsToOrder;
        return currentPath != null;
    }

    public void unlinkAllDifferentParts() {
        setPathsForLaterOrdering();
        setPathsForCurrentOrdering();
    }

    public void initializePathsForDetermineRelationToReferencePath(){
        pathsToOrder = currentPath.next;
        currentPath.next=null;
    }

    public Path getCurrentReferencePath() {
        return referencePath;
    }

    public void addRemainingPathsAsSibling(){
        addPathAsSibling();
        addPathsThatWillBeOrderedAsSibling();
    }

    public void addPathAsSibling() {
        referencePath.next = List.elementInsertAtTheLastNextOfList(currentPath,referencePath.next);
    }

    public void addPathAsChild() {
        referencePath.childlist = List.elementInsertAtTheLastNextOfList(currentPath,referencePath.childlist);
    }

    public Point getFirstPointOfCurrentPath(){
        return new Point(currentPath.priv.pt[0].x, currentPath.priv.pt[0].y - 1);
    }

    public int getUpperBoundOfPath(){
        return currentPath.priv.pt[0].y;
    }

    public void scheduleOrderedPathsForNextOrderingStep() {
        if (referencePath.next != null)
            scheduleSiblingsForNextOrderingStep();
        if (referencePath.childlist != null)
            scheduleChildrenForNextOrderingStep();
    }

    private void setPathsForLaterOrdering() {
        pathsToOrder = pathsThatNeedToProcess;
        pathsThatNeedToProcess = pathsThatNeedToProcess.childlist;
    }

    private void setPathsForCurrentOrdering() {
        pathsToOrder.childlist = null;
        referencePath = pathsToOrder;
        pathsToOrder = pathsToOrder.next;
        referencePath.next = null;
    }

    private void addPathsThatWillBeOrderedAsSibling() {
        referencePath.next = List.listInsertAtTheLastNextOfList(pathsToOrder,referencePath.next);
    }

    private void scheduleSiblingsForNextOrderingStep() {
        referencePath.next.childlist = pathsThatNeedToProcess;
        pathsThatNeedToProcess = referencePath.next;
    }

    private void scheduleChildrenForNextOrderingStep() {
        referencePath.childlist.childlist = pathsThatNeedToProcess;
        pathsThatNeedToProcess = referencePath.childlist;
    }
}
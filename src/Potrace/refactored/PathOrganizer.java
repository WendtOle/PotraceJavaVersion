package Potrace.refactored;

import Potrace.General.List;
import Potrace.General.Path;

import java.awt.*;

/**
 * Created by andreydelany on 12.07.17.
 */
public class PathOrganizer {
    Path pathList;
    Path pathsThatNeedToProcess;
    Path pathsToOrder;
    Path referencePath;
    Path currentPath;

    public PathOrganizer(Path pathList){
        this.pathList = pathList;
        pathsThatNeedToProcess = pathList;
    }

    public boolean stillNeedToProcessPathes() {
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

    private void addPathsThatWillBeOrderedAsSibling() {
        referencePath.next = List.listInsertAtTheLastNextOfList(pathsToOrder,referencePath.next);
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

    public void scheduleOrderdPathsForNextOrderingStep() {
        if (referencePath.next != null)
            scheduleSiblingsForNextOrderingStep();
        if (referencePath.childlist != null)
            scheduleChildrenForNextOrderingStep();
    }

    private void scheduleChildrenForNextOrderingStep() {
        referencePath.childlist.childlist = pathsThatNeedToProcess;
        pathsThatNeedToProcess = referencePath.childlist;
    }

    private void scheduleSiblingsForNextOrderingStep() {
        referencePath.next.childlist = pathsThatNeedToProcess;
        pathsThatNeedToProcess = referencePath.next;
    }
}
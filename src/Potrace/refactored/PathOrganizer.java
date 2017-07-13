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

    public boolean stillNeedToLevelOneOrder() {
        return pathsThatNeedToProcess != null;
    }

    public boolean stillNeedToLevelTwoOrder(){
        return currentPath != null;
    }

    public void initializingPathForLevelOneOrdering() {
        setPathsForLaterLevelOneOrdering();
        setPathsForCurrentLevelOneOrdering();
    }

    private void setPathsForLaterLevelOneOrdering() {
        pathsToOrder = pathsThatNeedToProcess;
        pathsThatNeedToProcess = pathsThatNeedToProcess.childlist;
    }

    private void setPathsForCurrentLevelOneOrdering() {
        pathsToOrder.childlist = null;
        referencePath = pathsToOrder;
        pathsToOrder = pathsToOrder.next;
        referencePath.next = null;
    }

    public void initializePathForLevelTwoOrdering(){
        currentPath = pathsToOrder;
        pathsToOrder = currentPath.next;
        currentPath.next=null;
    }

    public Path getCurrentReferencePath() {
        return referencePath;
    }

    public void addRemainingPathsAsSibling(){
        addPathAsSibling();
        addPathesThatWillBeOrderedAsSibling();
    }

    public void addPathAsSibling() {
        referencePath.next = List.elementInsertAtTheLastNextOfList(currentPath,referencePath.next);
    }

    private void addPathesThatWillBeOrderedAsSibling() {
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
}

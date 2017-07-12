package Potrace.refactored;

import Potrace.General.List;
import Potrace.General.Path;

import java.awt.*;

/**
 * Created by andreydelany on 12.07.17.
 */
public class PathManager {

    Path pathList;
    Path pathsThatNeedToProcess;
    Path pathsToOrder;
    Path referencePath;
    Path currentPath;

    public PathManager(Path pathList){
        this.pathList = pathList;
        pathsThatNeedToProcess = pathList;
    }

    public boolean stillNeedToProcessPathes() {
        return pathsThatNeedToProcess != null;
    }

    public void initializingPathForFirstLevelOrdering() {
        pathsToOrder = pathsThatNeedToProcess; //todo to long
        pathsThatNeedToProcess = pathsThatNeedToProcess.childlist;
        pathsToOrder.childlist = null;

        referencePath = pathsToOrder;
        pathsToOrder = pathsToOrder.next;
        referencePath.next = null;
    }

    public Path getCurrentReferencePath() {
        return referencePath;
    }

    public boolean stillNeedToOrderPaths(){
        return (currentPath != null);
    }

    public void preparePathsThatWillLaterBeLookedAt(){
        currentPath = pathsToOrder;
        pathsToOrder = currentPath.next;
        currentPath.next=null;
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

    public void addRemainingPathsAsSibling(){
        addPathAsSibling();
        addPathesThatWillBeOrderedAsSibling();
    }

    public int getUpperBoundOfPath(){
        return currentPath.priv.pt[0].y;
    }
}

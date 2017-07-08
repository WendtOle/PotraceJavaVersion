package refactored;

import General.*;
/**
 * Created by andreydelany on 29.06.17.
 */
public class NextComponentReconstruction {
    Path originPath;
    Path pathesThatNeedToProcess;
    Path currentPath;

    public NextComponentReconstruction(Path pathlist) {
        this.originPath = pathlist;
        initializeCurrentPathAndOriginPath();
        reconstructNextComponent();
    }

    public Path getPathWithReconstructedNext(){
        return originPath;
    }

    private void initializeCurrentPathAndOriginPath() {
        currentPath = originPath;
        if (currentPath != null) {
            currentPath.next = null;
        }
        originPath = null;
    }

    private void reconstructNextComponent() {
        while (currentPath != null) {
            pathesThatNeedToProcess = currentPath.next;
            addAllSiblingsWithTrailingChildren();
            currentPath = pathesThatNeedToProcess;
        }
    }

    private void addAllSiblingsWithTrailingChildren() {
        for (Path currentSibling = currentPath; currentSibling != null; currentSibling=currentSibling.sibling) {
            addPathToNextComponent(currentSibling);
            addAllChildrenOfPath(currentSibling);
        }
    }

    private void addAllChildrenOfPath(Path path) {
        for (Path currentChild=path.childlist; currentChild != null; currentChild=currentChild.sibling) {
            addPathToNextComponent(currentChild);
            scheduleChildrenOfCurrentChildForLaterProcessing(currentChild);
        }
    }

    private void addPathToNextComponent(Path path) {
        originPath = List.elementInsertAtTheLastNextOfList(path, originPath);
    }

    private void scheduleChildrenOfCurrentChildForLaterProcessing(Path path) {
        if (path.childlist != null) {
            pathesThatNeedToProcess = List.elementInsertAtTheLastNextOfList(path.childlist,pathesThatNeedToProcess);
        }
    }
}
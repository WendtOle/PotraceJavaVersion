package refactored;

import General.*;
/**
 * Created by andreydelany on 29.06.17.
 */
public class NextComponentReconstruction {
    Path originPath;
    Path pathesThatNeedToProcess;
    Path currentPath;

    public NextComponentReconstruction(Path pathList) {
        initializeFields(pathList);
        reconstructNextComponent();
    }

    public Path getPathWithReconstructedNext(){
        return originPath;
    }

    private void initializeFields(Path pathList) {
        originPath = pathList;
        currentPath = originPath;
        if (currentPath != null)
            currentPath.next = null;
        originPath = null;
    }

    private void reconstructNextComponent() {
        while (areTherePathesToProcess()) {
            pathesThatNeedToProcess = currentPath.next;
            addAllSiblingsWithTrailingChildrenOfPath(currentPath);
            currentPath = pathesThatNeedToProcess;
        }
    }

    private boolean areTherePathesToProcess() {
        return currentPath != null;
    }

    private void addAllSiblingsWithTrailingChildrenOfPath(Path path) {
        for (Path currentSibling = path; currentSibling != null; currentSibling=currentSibling.sibling) {
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
        if (path.childlist != null)
            pathesThatNeedToProcess = List.elementInsertAtTheLastNextOfList(path.childlist,pathesThatNeedToProcess);
    }
}
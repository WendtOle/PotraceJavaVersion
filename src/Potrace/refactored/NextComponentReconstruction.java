package Potrace.refactored;

import Potrace.General.*;
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
        setCurrentPath();
        originPath = null;
    }

    private void setCurrentPath() {
        currentPath = originPath;
        if (currentPath != null)
            currentPath.next = null;
    }

    private void reconstructNextComponent() {
        while (areTherePathsToProcess())
            processPaths();
    }

    private boolean areTherePathsToProcess() {
        return currentPath != null;
    }

    private void processPaths() {
        pathesThatNeedToProcess = currentPath.next;
        addAllSiblingsWithTrailingChildren(currentPath);
        currentPath = pathesThatNeedToProcess;
    }

    private void addAllSiblingsWithTrailingChildren(Path currentSibling) {
        while(currentSibling != null) {
            addSiblingWithTrailingChildrentoPath(currentSibling);
            currentSibling=currentSibling.sibling;
        }
    }

    private void addSiblingWithTrailingChildrentoPath(Path currentSibling) {
        addPathToNextComponent(currentSibling);
        addAllChildrenOfPath(currentSibling.childlist);
    }

    private void addAllChildrenOfPath(Path currentChild) {
        while (currentChild != null) {
            addChildrenToPath(currentChild);
            currentChild = currentChild.sibling;
        }
    }

    private void addChildrenToPath(Path child) {
        addPathToNextComponent(child);
        scheduleChildrenOfCurrentChildForLaterProcessing(child);
    }

    private void addPathToNextComponent(Path path) {
        originPath = List.elementInsertAtTheLastNextOfList(path, originPath);
    }

    private void scheduleChildrenOfCurrentChildForLaterProcessing(Path path) {
        if (path.childlist != null)
            pathesThatNeedToProcess = List.elementInsertAtTheLastNextOfList(path.childlist,pathesThatNeedToProcess);
    }
}
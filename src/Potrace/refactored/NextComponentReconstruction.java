package Potrace.refactored;

import Potrace.General.*;

class NextComponentReconstruction {
    private Path pathList;
    private Path pathsThatNeedToAdd;
    private Path currentPathToAdd;

    public NextComponentReconstruction(Path pathList) {
        initializeFields(pathList);
        reconstructNextComponent();
    }

    public Path getPathWithReconstructedNext(){
        return pathList;
    }

    private void initializeFields(Path pathList) {
        this.pathList = pathList;
        setCurrentPath();
        this.pathList = null;
    }

    private void setCurrentPath() {
        currentPathToAdd = pathList;
        if (currentPathToAdd != null)
            currentPathToAdd.next = null;
    }

    private void reconstructNextComponent() {
        while (areTherePathsAdd())
            addPaths();
    }

    private boolean areTherePathsAdd() {
        return currentPathToAdd != null;
    }

    private void addPaths() {
        pathsThatNeedToAdd = currentPathToAdd.next;
        addAllSiblingsWithTrailingChildren(currentPathToAdd);
        currentPathToAdd = pathsThatNeedToAdd;
    }

    private void addAllSiblingsWithTrailingChildren(Path currentSibling) {
        while(currentSibling != null) {
            addSiblingWithTrailingChildrenToPath(currentSibling);
            currentSibling=currentSibling.sibling;
        }
    }

    private void addSiblingWithTrailingChildrenToPath(Path currentSibling) {
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
        pathList = List.elementInsertAtTheLastNextOfList(path, pathList);
    }

    private void scheduleChildrenOfCurrentChildForLaterProcessing(Path path) {
        if (path.childlist != null)
            pathsThatNeedToAdd = List.elementInsertAtTheLastNextOfList(path.childlist, pathsThatNeedToAdd);
    }
}
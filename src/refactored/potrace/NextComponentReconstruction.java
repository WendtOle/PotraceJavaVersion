package refactored.potrace;

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
            addAllSiblingsWithTrailingChildrenOfCurrentPath();
            currentPath = pathesThatNeedToProcess;
        }
    }

    private void addAllSiblingsWithTrailingChildrenOfCurrentPath() {
        for (Path currentSibling=currentPath; currentSibling != null; currentSibling=currentSibling.sibling) {
            originPath = Path.insertElementAtTheEndOfList(currentSibling, originPath);
            addAllChildrenOfPath(currentSibling);
        }
    }

    private void addAllChildrenOfPath(Path path) {
        for (Path currentChild=path.childlist; currentChild != null; currentChild=currentChild.sibling) {
            originPath = Path.insertElementAtTheEndOfList(currentChild, originPath);
            scheduleChildrenOfCurrentChildIfNeccessaryForLaterProcessing(currentChild);
        }
    }

    private void scheduleChildrenOfCurrentChildIfNeccessaryForLaterProcessing(Path path) {
        if (path.childlist != null) {
            pathesThatNeedToProcess = Path.insertElementAtTheEndOfList(path.childlist,pathesThatNeedToProcess);
        }
    }

    public Path getPathWithReconstructedNext(){
        return originPath;
    }
}
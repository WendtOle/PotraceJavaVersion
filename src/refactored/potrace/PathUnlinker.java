package refactored.potrace;

/**
 * Created by andreydelany on 06.07.17.
 */
public class PathUnlinker {
    Path referencePath, pathesToOrder, pathesThatNeedToProcess;
    Boolean isFirstRun = true;

    public PathUnlinker(Path path){
        pathesThatNeedToProcess = path;
        //unlink();
    }

     void unlink() {
        pathesToOrder = pathesThatNeedToProcess;
        pathesThatNeedToProcess = pathesThatNeedToProcess.childlist;
        pathesToOrder.childlist = null;

        referencePath = pathesToOrder;
        pathesToOrder = pathesToOrder.next;
        referencePath.next = null;
    }

    public void updatePathesThatNeedToProcess(Path referencePath) {
        if (hasReferencePathSiblings(referencePath))
            schedulePathesForFurtherProcessing(referencePath.next);
        if (hasReferencePathChildren(referencePath))
            schedulePathesForFurtherProcessing(referencePath.childlist);
        //unlink(pathesThatNeedToProcess);
    }

    public boolean stillNeedToProcessPathes() {
        return (pathesThatNeedToProcess != null);
    }

    private boolean hasReferencePathSiblings(Path path) {
        return path.next != null;
    }

    private boolean hasReferencePathChildren(Path path) {
        return path.childlist != null;
    }

    private void schedulePathesForFurtherProcessing(Path path) {
        path.childlist = pathesThatNeedToProcess;
        pathesThatNeedToProcess = path;
    }

    public Path getReferencePath(){
        return referencePath;
    }

    public Path getPathesToOrder(){
        return pathesToOrder;
    }

    public Path getPathesThatNeedToProcess() {
        return pathesThatNeedToProcess;
    }
}

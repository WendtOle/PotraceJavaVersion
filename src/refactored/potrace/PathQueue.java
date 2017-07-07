package refactored.potrace;

/**
 * Created by andreydelany on 06.07.17.
 */
public class PathQueue implements PathQueueInterface {
    Path referencePath, pathesToOrder, pathesThatNeedToProcess;

    public PathQueue(Path path){
        pathesThatNeedToProcess = path;
    }

    public boolean stillNeedToProcessPathes() {
        return (pathesThatNeedToProcess != null);
    }

    public Path[] getNextPathes() {
        schedulePathesForNextProcessingStep();
        return new Path[]{referencePath,pathesToOrder};
    }

    public void updateQueue(Path path) {
        appendFoundChildrenInFrontOfSiblingsToQueue(path);
    }

    private void appendFoundChildrenInFrontOfSiblingsToQueue(Path path) {
        schedulePathesForFurtherProcessing(path.next);
        schedulePathesForFurtherProcessing(path.childlist);
    }

    private void schedulePathesForFurtherProcessing(Path path) {
        if (path != null) {
            path.childlist = pathesThatNeedToProcess;
            pathesThatNeedToProcess = path;
        }
    }

    private void schedulePathesForNextProcessingStep() {
        pathesToOrder = pathesThatNeedToProcess;
        pathesThatNeedToProcess = pathesThatNeedToProcess.childlist;
        pathesToOrder.childlist = null;

        referencePath = pathesToOrder;
        pathesToOrder = pathesToOrder.next;
        referencePath.next = null;
    }
}
package refactored;

import General.*;

/**
 * Created by andreydelany on 29.06.17.
 */
public class ChildrenAndSiblingFinder {

    Path pathList;
    Path pathesToOrder, referencePath;
    PathQueueInterface pathQueue;
    DetermineHirachy hirachyBuilder;

    public ChildrenAndSiblingFinder(Path pathList, Bitmap bitmap){
        this.pathList = pathList;
        hirachyBuilder = new DetermineHirachy(bitmap);
        pathQueue = new PathQueue(pathList);
    }

    public Path getTreeTransformedPathStructure(){
        transformIntoTreeStructure();
        return pathList;
    }

    private void transformIntoTreeStructure() {
        while (pathQueue.stillNeedToProcessPathes()) {
            processPathes();
        }
    }

    private void processPathes() {
        getPathesToProcess();
        determineHirachyBetweenPathes();
        scheduleFoundChildrenAndSiblingsForFurtherProcessing();
    }

    private void getPathesToProcess() {
        Path[] pathesNextToProcess = pathQueue.getNextPathes();
        referencePath = pathesNextToProcess[0];
        pathesToOrder = pathesNextToProcess[1];
    }

    private void determineHirachyBetweenPathes(){
        referencePath = hirachyBuilder.getHierarchicallyOrderedPathes(referencePath,pathesToOrder);
    }

    private void scheduleFoundChildrenAndSiblingsForFurtherProcessing() {
        pathQueue.updateQueue(referencePath);
    }
}
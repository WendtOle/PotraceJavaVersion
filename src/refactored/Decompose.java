package refactored;

import General.*;
import General.List;

import java.awt.*;

public class Decompose implements DecompositionInterface {
    protected Param param;
    protected Bitmap workCopy;
    BitmapHandlerInterface bitmapHandler;
    PathInverter pathInverterForWorkCopy;
    protected Point startPointOfCurrentPath;
    public Path pathList = null;

    public Path getPathList(Bitmap generalBitmap, General.Param param) {
        this.workCopy = generalBitmap.bm_dup();
        this.bitmapHandler = new BitmapHandler(generalBitmap);
        this.pathInverterForWorkCopy = new PathInverter(workCopy); //TODO
        this.param = param;
        decomposeBitmapIntoPathlistNew();
        return pathList;
    }

    private void decomposeBitmapIntoPathlistNew() {
        findPathesOnBitmap();
        structurePathlistAsTree();
    }

    protected void findPathesOnBitmap() {
        NextFilledPixelFinder nextFilledPixelFinder = new NextFilledPixelFinder(workCopy); //TODO
        while(nextFilledPixelFinder.isThereAFilledPixel()) {
            startPointOfCurrentPath = nextFilledPixelFinder.getPositionOfNextFilledPixel();
            findAndAddPathToPathlist();
        }
    }

    protected void findAndAddPathToPathlist() {
        Path currentPath = findPath();
        addPathToPathListIfBigEnough(currentPath);
    }

    protected Path findPath() {
        int signOfPath = getSignOfPathFromOriginalBitmap(startPointOfCurrentPath);
        FindPath pathFinder = new FindPath(workCopy, startPointOfCurrentPath, signOfPath, TurnPolicyEnum.values()[param.turnpolicy]); //TODO
        return pathFinder.getPath();
    }

    protected int getSignOfPathFromOriginalBitmap(Point currentPoint) { //TODO
        boolean isPathFilled = bitmapHandler.isPixelFilled(currentPoint);
        if (isPathFilled)
            return '+';
        else
            return '-';
    }

    protected void addPathToPathListIfBigEnough(Path currentPath) {
        pathInverterForWorkCopy.invertPathOnBitmap(currentPath);
        if (isPathBigEnough(currentPath.area,param.turdsize)) {
            pathList = List.elementInsertAtTheLastNextOfList(currentPath,pathList);
        }
    }

    protected void structurePathlistAsTree() {
        TreeStructurTransformationInterface pathListToTree = new TreeStructurTransformation(pathList,workCopy); //TODO
        pathList = pathListToTree.getTreeStructure();
    }

    private boolean isPathBigEnough(int actualArea, int areaOfTurd) {
        return actualArea > areaOfTurd;
    }
}
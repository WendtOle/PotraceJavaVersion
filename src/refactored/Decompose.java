package refactored;

import General.*;

import java.awt.*;

public class Decompose implements DecompositionInterface {
    General.Param param;
    Bitmap workCopy;
    BitmapHandlerInterface bitmapHandler;
    PathInverter pathInverterForWorkCopy;
    Point startPointOfCurrentPath;
    Path pathList = null;

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

    private void findPathesOnBitmap() {
        NextFilledPixelFinder nextFilledPixelFinder = new NextFilledPixelFinder(workCopy); //TODO
        while(nextFilledPixelFinder.isThereAFilledPixel()) {
            startPointOfCurrentPath = nextFilledPixelFinder.getPositionOfNextFilledPixel();
            findAndAddPathToPathlist();
        }
    }

    private void findAndAddPathToPathlist() {
        Path currentPath = findPath();
        addPathToPathListIfBigEnough(currentPath);
    }

    private Path findPath() {
        int signOfPath = getSignOfPathFromOriginalBitmap(startPointOfCurrentPath);
        FindPath pathFinder = new FindPath(workCopy, startPointOfCurrentPath, signOfPath, TurnPolicyEnum.values()[param.turnpolicy]); //TODO
        return pathFinder.getPath();
    }

    private int getSignOfPathFromOriginalBitmap(Point currentPoint) {
        boolean isPathFilled = bitmapHandler.isPixelFilled(currentPoint);
        if (isPathFilled)
            return '+';
        else
            return '-';
    }

    private void addPathToPathListIfBigEnough(Path currentPath) {
        pathInverterForWorkCopy.invertPathOnBitmap(currentPath);
        if (isPathBigEnough(currentPath.area,param.turdsize)) {
            pathList = List.elementInsertAtTheLastNextOfList(currentPath,pathList);
        }
    }

    private void structurePathlistAsTree() {
        TreeStructurTransformationInterface pathListToTree = new TreeStructurTransformation(pathList,workCopy); //TODO
        pathList = pathListToTree.getTreeStructure();
    }

    private boolean isPathBigEnough(int actualArea, int areaOfTurd) {
        return actualArea > areaOfTurd;
    }
}
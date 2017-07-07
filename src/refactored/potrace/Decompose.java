package refactored.potrace;

import java.awt.*;

public class Decompose {
    Param param;
    Bitmap workCopy;
    BitmapHandlerInterface bitmapHandler;
    PathInverter pathInverterForWorkCopy;
    Point startPointOfCurrentPath;
    Path pathList = null;

    public Decompose(Bitmap bitmap, Param param) {
        this.workCopy = bitmap.duplicate();
        this.bitmapHandler = new BitmapHandler(bitmap);
        this.pathInverterForWorkCopy = new PathInverter(workCopy);
        this.param = param;
        decomposeBitmapIntoPathlistNew();
    };

    public Path getPathList() {
        return pathList;
    }

    private void decomposeBitmapIntoPathlistNew() {
        findPathesOnBitmap();
        structurePathlistAsTree();
    }

    private void findPathesOnBitmap() {
        NextFilledPixelFinder nextFilledPixelFinder = new NextFilledPixelFinder(workCopy);
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
        FindPath pathFinder = new FindPath(workCopy, startPointOfCurrentPath, signOfPath, TurnPolicyEnum.values()[param.turnpolicy]);
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
            pathList = Path.insertElementAtTheEndOfList(currentPath,pathList);
        }
    }

    private void structurePathlistAsTree() {
        TreeStructurTransformationInterface pathListToTree = new TreeStructurTransformation(pathList,workCopy);
        pathList = pathListToTree.getTreeStructure();
    }

    private boolean isPathBigEnough(int actualArea, int areaOfTurd) {
        return actualArea > areaOfTurd;
    }
}
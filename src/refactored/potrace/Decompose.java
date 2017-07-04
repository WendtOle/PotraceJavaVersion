package refactored.potrace;

import java.awt.*;

public class Decompose {
    Param param;
    Bitmap workCopy;
    BitmapPixelHandler bitmapHandler;
    PathOnBitmapInverter pathInverterForWorkCopy;
    Point startPointOfCurrentPath;
    Path pathList = null;

    public Decompose(Bitmap bitmap, Param param) {
        this.workCopy = bitmap.duplicate();
        this.bitmapHandler = new BitmapPixelHandler(bitmap);
        this.pathInverterForWorkCopy = new PathOnBitmapInverter(workCopy);
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
        FindPath pathFinder = new FindPath(workCopy, new Point(startPointOfCurrentPath.x, startPointOfCurrentPath.y + 1), signOfPath, param.turnpolicy);
        return pathFinder.getPath();
    }

    private int getSignOfPathFromOriginalBitmap(Point currentPoint) {
        boolean isPathFilled = bitmapHandler.getPixelValue(currentPoint);
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
        TreeStructurTransformation pathListToTree = new TreeStructurTransformation(pathList,workCopy);
        pathList = pathListToTree.getTreeStructure();
    }

    private boolean isPathBigEnough(int actualArea, int areaOfTurd) {
        return actualArea > areaOfTurd;
    }
}
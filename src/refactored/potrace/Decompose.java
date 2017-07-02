package refactored.potrace;

import java.awt.*;

public class Decompose {
    Param param;
    Bitmap bitmap, workCopy;
    BitmapManipulator manipulatorForWorkCopy;
    Point startPointOfCurrentPath;
    Path pathList = null;

    public Decompose(Bitmap bitmap, Param param) {
        this.bitmap = bitmap;
        this.param = param;
        initializeValues();
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

    private void initializeValues() {
        workCopy = bitmap.duplicate();
        manipulatorForWorkCopy = new BitmapManipulator(workCopy);
        manipulatorForWorkCopy.clearExcessPixelsOfBitmap();
    }

    private void findAndAddPathToPathlist() {
        Path currentPath = findPath();
        addPathToPathListIfBigEnough(currentPath);
    }

    private Path findPath() {
        int signOfPath = getSignOfPathFromOriginalBitmap(bitmap, startPointOfCurrentPath);
        FindPath pathFinder = new FindPath(workCopy, new Point(startPointOfCurrentPath.x, startPointOfCurrentPath.y + 1), signOfPath, param.turnpolicy);
        return pathFinder.getPath();
    }

    private int getSignOfPathFromOriginalBitmap(Bitmap bitmap, Point currentPoint) {
        boolean isPathFilled = bitmap.getPixelValue(currentPoint);
        if (isPathFilled)
            return '+';
        else
            return '-';
    }

    private void addPathToPathListIfBigEnough(Path currentPath) {
        manipulatorForWorkCopy.invertPathOnBitmap(currentPath);
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
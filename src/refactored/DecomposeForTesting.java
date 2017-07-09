package refactored;

import General.*;

import java.awt.*;

public abstract class DecomposeForTesting implements DecompositionInterface {
    Param param;
    Bitmap workCopy;
    BitmapHandlerInterface bitmapHandler;
    PathInverter pathInverterForWorkCopy;
    Point startPointOfCurrentPath;
    Path pathList = null;

    public Path getPathList(BitmapInterface generalBitmap, Param param) {
        Bitmap bitmap = translateBitmap(generalBitmap);
        this.workCopy = bitmap.bm_dup();
        this.bitmapHandler = new BitmapHandler(bitmap);
        this.pathInverterForWorkCopy = new PathInverter(workCopy);
        this.param = param;
        decomposeBitmapIntoPathlistNew();
        return pathList;
    }

    private Bitmap translateBitmap(BitmapInterface generalBitmap) {
        Bitmap bitmap = new Bitmap(generalBitmap.getWidth(),generalBitmap.getHeight());
        bitmap.map = generalBitmap.getWords();
        return bitmap;
    }

    private void decomposeBitmapIntoPathlistNew() {
        findPathesOnBitmap();
        structurePathlistAsTree();
    }

    protected abstract void findPathesOnBitmap();

    protected void findAndAddPathToPathlist() {
        Path currentPath = findPath();
        addPathToPathListIfBigEnough(currentPath);
    }

    protected abstract Path findPath();

    private void addPathToPathListIfBigEnough(Path currentPath) {
        invertPathOnBitmap(currentPath);
        if (isPathBigEnough(currentPath.area,param.turdsize)) {
            pathList = List.elementInsertAtTheLastNextOfList(currentPath,pathList);
        }
    }

    protected abstract void invertPathOnBitmap(Path currentPath);

    protected  abstract void structurePathlistAsTree();

    private boolean isPathBigEnough(int actualArea, int areaOfTurd) {
        return actualArea > areaOfTurd;
    }
}
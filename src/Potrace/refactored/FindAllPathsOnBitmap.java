package Potrace.refactored;

import Potrace.General.Bitmap;
import Potrace.General.List;
import Potrace.General.Param;
import Potrace.General.Path;

import java.awt.*;

public class FindAllPathsOnBitmap {

    private Bitmap workCopy;
    private BitmapHandlerInterface bitmapHandler;
    private PathInverter pathInverter;
    private NextFilledPixelFinder nextFilledPixelFinder;
    private Param param;
    private Path pathList = null;
    private Point startPointOfCurrentPath;

    public FindAllPathsOnBitmap(Bitmap bitmap, Param param){
        initializeFields(bitmap, param);
        findAllPathsOnBitmap();
    }

    public Path getPathList() {
        return pathList;
    }

    private void initializeFields(Bitmap bitmap, Param param) {
        this.workCopy = bitmap.bm_dup();
        this.param = param;
        initializeHelperClasses(bitmap);
        setStartPointForNextPath();
    }

    private void initializeHelperClasses(Bitmap bitmap) {
        this.bitmapHandler = new BitmapHandler(bitmap);
        this.pathInverter = new PathInverter(workCopy);
        this.nextFilledPixelFinder = new NextFilledPixelFinder(workCopy);
    }

    private void setStartPointForNextPath() {
        startPointOfCurrentPath = nextFilledPixelFinder.getPositionOfNextFilledPixel();
    }

    private void findAllPathsOnBitmap() {
        while(isThereAnotherPath()) {
            findAndProcessPath();
            setStartPointForNextPath();
        }
    }

    private boolean isThereAnotherPath() {
        return !startPointOfCurrentPath.equals(new NoFilledPixelFound());
    }

    private void findAndProcessPath() {
        Path path = findPath();
        invertPath(path);
        addPath(path);
    }

    private Path findPath() {
        FindPath pathFinder = getPathFinder();
        return pathFinder.getPath();
    }

    private FindPath getPathFinder() {
        PathKindEnum kindOfPath = getKindOfPath(startPointOfCurrentPath);
        TurnPolicyEnum turnPolicy = TurnPolicyEnum.values()[param.turnpolicy];
        PathFindingCharacteristics pathFindingCharacteristics = new PathFindingCharacteristics(turnPolicy,kindOfPath);
        return new FindPath(workCopy, startPointOfCurrentPath, pathFindingCharacteristics);
    }

    private PathKindEnum getKindOfPath(Point pointOfPath) {
        if (isPathFilled(pointOfPath))
            return PathKindEnum.POSITIV;
        else
            return PathKindEnum.NEGATIV;
    }

    private boolean isPathFilled(Point pointOfPath) {
        return bitmapHandler.isPixelFilled(pointOfPath);
    }

    private void invertPath(Path path) {
        pathInverter.invertPathOnBitmap(path);
    }

    private void addPath(Path path) {
        if (isPathBigEnough(path.area))
            addPathToPathList(path);
    }

    private boolean isPathBigEnough(int actualArea) {
        return actualArea > param.turdsize;
    }

    private void addPathToPathList(Path path) {
        pathList = List.elementInsertAtTheLastNextOfList(path,pathList);
    }
}
package Potrace.refactored;

import Potrace.General.Bitmap;
import Potrace.General.List;
import Potrace.General.Param;
import Potrace.General.Path;

import java.awt.*;

/**
 * Created by andreydelany on 12.07.17.
 */
public class FindAllPathsOnBitmap {

    Bitmap workCopy;
    BitmapHandlerInterface bitmapHandler;
    PathInverter pathInverter;
    NextFilledPixelFinder nextFilledPixelFinder;
    Param param;
    Path pathList = null;
    Point startPointOfCurrentPath;

    public FindAllPathsOnBitmap(Bitmap bitmap, Param param){
        initializeFields(bitmap, param);
        findAllPathsOnBitmap();
    }

    public Path getPathList() {
        return pathList;
    }

    private void initializeFields(Bitmap bitmap, Param param) {
        this.workCopy = bitmap.bm_dup();
        this.bitmapHandler = new BitmapHandler(bitmap);
        this.pathInverter = new PathInverter(workCopy);
        this.nextFilledPixelFinder = new NextFilledPixelFinder(workCopy);
        this.param = param;
    }

    private void findAllPathsOnBitmap() {
        setNextFilledPixelToStartPointOfNextPath();
        while(isThereAFilledPixel())
            findAndAddPathToPathList();
    }

    private boolean isThereAFilledPixel() {
        return !startPointOfCurrentPath.equals(new NoPointFound());
    }

    private void findAndAddPathToPathList() {
        Path path = findPath();
        invertAndAddPath(path);
        setNextFilledPixelToStartPointOfNextPath();
    }

    private Path findPath() {
        return findPathAtStartPoint();
    }

    private void setNextFilledPixelToStartPointOfNextPath() {
        startPointOfCurrentPath = nextFilledPixelFinder.getPositionOfNextFilledPixel();
    }

    private Path findPathAtStartPoint() {
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

    private void invertAndAddPath(Path path) {
        invertPath(path);
        addToPathListIfBigEnough(path);
    }

    private void invertPath(Path path) {
        pathInverter.invertPathOnBitmap(path);
    }

    private void addToPathListIfBigEnough(Path path) {
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
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
        while(isThereAFilledPixel())
            findAndAddPathToPathList();
    }

    private boolean isThereAFilledPixel() {
        return nextFilledPixelFinder.isThereAFilledPixel();
    }

    private void findAndAddPathToPathList() {
        Path path = findPath();
        invertAndAddPath(path);
    }

    private Path findPath() {
        Point startPointOfPath = determineStartPointOfPath();
        return findPathWhichStartsAt(startPointOfPath);
    }

    private Point determineStartPointOfPath() {
        return nextFilledPixelFinder.getPositionOfNextFilledPixel();
    }

    private Path findPathWhichStartsAt(Point startPointOfPath) {
        FindPath pathFinder = getPathFinder(startPointOfPath);
        return pathFinder.getPath();
    }

    private FindPath getPathFinder(Point startPointOfPath) {
        PathKindEnum kindOfPath = getKindOfPath(startPointOfPath);
        TurnPolicyEnum turnPolicy = TurnPolicyEnum.values()[param.turnpolicy];
        PathFindingCharacteristics pathFindingCharacteristics = new PathFindingCharacteristics(turnPolicy,kindOfPath);
        return new FindPath(workCopy, startPointOfPath, pathFindingCharacteristics);
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
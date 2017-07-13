package Potrace.refactored;

import Potrace.General.Bitmap;
import Potrace.General.List;
import Potrace.General.Param;
import Potrace.General.Path;

import java.awt.*;

/**
 * Created by andreydelany on 12.07.17.
 */
public class FindPathsOnBitmap {

    Bitmap workCopy;
    BitmapHandlerInterface bitmapHandler;
    PathInverter pathInverterForWorkCopy;
    NextFilledPixelFinder nextFilledPixelFinder;
    Param param;
    Path pathList = null;

    public FindPathsOnBitmap(Bitmap bitmap, Param param){
        initializeFields(bitmap, param);
        findAllPathesOnBitmap();
    }

    public Path getPathList() {
        return pathList;
    }

    private void initializeFields(Bitmap bitmap, Param param) {
        this.workCopy = bitmap.bm_dup();
        this.bitmapHandler = new BitmapHandler(bitmap);
        this.pathInverterForWorkCopy = new PathInverter(workCopy);
        this.nextFilledPixelFinder = new NextFilledPixelFinder(workCopy);
        this.param = param;
    }

    private void findAllPathesOnBitmap() {
        while(isThereAFilledPixel())
            findAndAddPathToPathList();
    }

    private boolean isThereAFilledPixel() {
        return nextFilledPixelFinder.isThereAFilledPixel();
    }

    private void findAndAddPathToPathList() {
        Path currentPath = findPath();
        invertAndAddPath(currentPath);
    }

    private Path findPath() {
        Point startPointOfCurrentPath = determineBeginningPixelOfPath();
        return findPathWhichStartsAt(startPointOfCurrentPath);
    }

    private Point determineBeginningPixelOfPath() {
        return nextFilledPixelFinder.getPositionOfNextFilledPixel();
    }

    private Path findPathWhichStartsAt(Point startPointOfCurrentPath) {
        FindPath pathFinder = getPathFinder(startPointOfCurrentPath);
        return pathFinder.getPath();
    }

    private FindPath getPathFinder(Point startPointOfCurrentPath) {
        PathKindEnum kindOfPath = getKindOfPath(startPointOfCurrentPath);
        DirectionChooserIdentificator directionIdentificator = new DirectionChooserIdentificator(TurnPolicyEnum.values()[param.turnpolicy],kindOfPath);
        return new FindPath(workCopy, startPointOfCurrentPath, directionIdentificator);
    }

    private PathKindEnum getKindOfPath(Point currentPoint) {
        if (isPathFilled(currentPoint))
            return PathKindEnum.POSITIV;
        else
            return PathKindEnum.NEGATIV;
    }

    private boolean isPathFilled(Point currentPoint) {
        return bitmapHandler.isPixelFilled(currentPoint);
    }

    private void invertAndAddPath(Path currentPath) {
        invertPathOnBitmap(currentPath);
        addPathToPathListIfBigEnough(currentPath);
    }

    private void invertPathOnBitmap(Path currentPath) {
        pathInverterForWorkCopy.invertPathOnBitmap(currentPath);
    }

    private void addPathToPathListIfBigEnough(Path currentPath) {
        if (isPathBigEnough(currentPath.area))
            addPathToPathList(currentPath);
    }

    private boolean isPathBigEnough(int actualArea) {
        return actualArea > param.turdsize;
    }

    private void addPathToPathList(Path currentPath) {
        pathList = List.elementInsertAtTheLastNextOfList(currentPath,pathList);
    }
}
package Potrace.refactored;

import Potrace.General.*;
import Potrace.General.List;

import java.awt.*;

public class Decompose implements DecompositionInterface {
    protected Param param;                                                  //ToDO gefühlt zu viele felder
    protected Bitmap workCopy;
    BitmapHandlerInterface bitmapHandler;
    PathInverter pathInverterForWorkCopy;
    NextFilledPixelFinder nextFilledPixelFinder;
    public Path pathList = null;

    public Path getPathList(Bitmap generalBitmap, Param param) {
        initializeFields(generalBitmap, param);
        decomposeBitmapIntoPathlistNew();
        return pathList;
    }

    private void initializeFields(Bitmap generalBitmap, Param param) {
        this.workCopy = generalBitmap.bm_dup();                             //TODO kürzer!
        this.bitmapHandler = new BitmapHandler(generalBitmap);
        this.pathInverterForWorkCopy = new PathInverter(workCopy);
        this.nextFilledPixelFinder = new NextFilledPixelFinder(workCopy);
        this.param = param;
    }

    private void decomposeBitmapIntoPathlistNew() {
        findPathsOnBitmap();
        structurePathListAsTree();
    }

    protected void findPathsOnBitmap() {
        while(isThereAFilledPixel())
            findAndAddPathToPathList();
    }

    private boolean isThereAFilledPixel() {
        return nextFilledPixelFinder.isThereAFilledPixel();
    }

    protected void findAndAddPathToPathList() {
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

    protected Path findPathWhichStartsAt(Point startPointOfCurrentPath) {
        int signOfPath = getSignOfPathFromOriginalBitmap(startPointOfCurrentPath);
        FindPath pathFinder = new FindPath(workCopy, startPointOfCurrentPath, signOfPath, TurnPolicyEnum.values()[param.turnpolicy]);
        return pathFinder.getPath();
    }

    protected int getSignOfPathFromOriginalBitmap(Point currentPoint) {
        if (isPathFilled(currentPoint))                     //TODO anders ausdrücken. so sieht es zu doof aus
            return '+';
        else
            return '-';
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

    protected void addPathToPathListIfBigEnough(Path currentPath) {
        if (isPathBigEnough(currentPath.area))
            addPathToPathList(currentPath);
    }

    private boolean isPathBigEnough(int actualArea) {
        return actualArea > param.turdsize;
    }

    private void addPathToPathList(Path currentPath) {
        pathList = List.elementInsertAtTheLastNextOfList(currentPath,pathList);
    }

    protected void structurePathListAsTree() {
        TreeStructurTransformationInterface pathListToTree = new TreeStructurTransformation(pathList,workCopy);
        pathList = pathListToTree.getTreeStructure();
    }

    @Override
    public Bitmap getWorkCopy() {
        return null;
    }
}
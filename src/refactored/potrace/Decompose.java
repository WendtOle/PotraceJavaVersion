package refactored.potrace;

import java.awt.*;

public class Decompose {
    Param param;
    Bitmap bitmap;
    Path pathList = null;

    public Decompose(Bitmap bitmap, Param param) {
        this.bitmap = bitmap;
        this.param = param;
        bm_to_pathlist();
    };

    /* Decompose the given Bitmap into paths. Returns a linked List of
    path_t objects with the fields len, pt, area, sign filled
    in. Returns 0 on success with plistp set, or -1 on error with errno
    set. */

    public void bm_to_pathlist() {
        Bitmap workCopy = bitmap.duplicate();
        workCopy.clearExcessPixelsOfBitmap();

        // iterate through components
        Point startPointOfPath = new Point(0,workCopy.height-1);

        while ((startPointOfPath = workCopy.findNextFilledPixel(startPointOfPath)) != null ) {

            int signOfPath = getSignOfPathFromOriginalBitmap(bitmap,startPointOfPath);

            FindPath pathFinder = new FindPath(workCopy, new Point(startPointOfPath.x, startPointOfPath.y + 1), signOfPath, param.turnpolicy);
            Path currentPath = pathFinder.getPath();

            workCopy.invertPathOnBitmap(currentPath);

            if (isPathBigEnough(currentPath.area,param.turdsize)) {
                pathList = Path.insertElementAtTheEndOfList(currentPath,pathList);
            }
        }

        TreeStructurTransformation pathListToTree = new TreeStructurTransformation(pathList,workCopy);
        pathList = pathListToTree.getTreeStructure();
    }

    private static boolean isPathBigEnough(int actualArea, int areaOfTurd) {
        return actualArea > areaOfTurd;
    }

    private static int getSignOfPathFromOriginalBitmap(Bitmap bm, Point startPointOfPath) {
        boolean isPathFilled = bm.getPixelValue(startPointOfPath.x, startPointOfPath.y);
        if (isPathFilled)
            return '+';
        else
            return '-';

    }

    public Path getPathList() {
        return pathList;
    }
}
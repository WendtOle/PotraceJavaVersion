package refactored.potrace;

import java.awt.*;

public class Decompose {

    static boolean detrand(int x, int y) {
        int z; //TODO unsigned
        char t[] = {
        /* non-linear sequence: constant term of inverse in GF(8),
        mod x^8+x^4+x^3+x+1 */
            0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1,
                    0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0,
                    0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1,
                    1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1,
                    0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0,
                    0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0,
                    0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0,
                    0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1,
                    1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0,
                    0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1,
                    1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0,
        };

        /* 0x04b3e375 and 0x05a8ef93 are chosen to contain every possible
        5-bit sequence */
        z = ((0x04b3e375 * x) ^ y) * 0x05a8ef93;
        z = t[z & 0xff] ^ t[(z>>8) & 0xff] ^ t[(z>>16) & 0xff] ^ t[(z>>24) & 0xff];
        return z == 1 ? true : false;
    }

    /* Decompose the given Bitmap into paths. Returns a linked List of
    path_t objects with the fields len, pt, area, sign filled
    in. Returns 0 on success with plistp set, or -1 on error with errno
    set. */

    public static Path bm_to_pathlist(Bitmap bitmap, Param param) {
        Path pathList = null;
        Bitmap workCopy = bitmap.duplicate();
        workCopy.clearExcessPixelsOfBitmap();

        // iterate through components
        Point startPointOfPath = new Point(0,workCopy.height-1);

        while ((startPointOfPath = workCopy.findNextFilledPixel(startPointOfPath)) != null ) {

            int signOfPath = getSignOfPathFromOriginalBitmap(bitmap,startPointOfPath);

            FindPath pathFinder = new FindPath(workCopy, new Point(startPointOfPath.x, startPointOfPath.y + 1), signOfPath, param.turnpolicy);
            Path currentPath = pathFinder.getPath();

            workCopy.removePathFromBitmap(currentPath);

            if (isPathBigEnough(currentPath.area,param.turdsize)) {
                pathList = Path.insertElementAtTheEndOfList(currentPath,pathList);
            }
        }

        PathListToTree.pathlist_to_tree(pathList, workCopy);
        return pathList;
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
}
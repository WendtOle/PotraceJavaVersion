package Potrace.General;

import Potrace.General.*;

public class BitmapManipulator {

    /* macros for accessing pixel at index (x,y). U* macros omit the bounds check. */
    private static boolean bm_range(int x, int a) {
        return (x) >= 0 && (x) < (a);
    }

    static boolean bm_safe(Bitmap bm, int x, int y) {
        return bm_range(x, bm.w) && bm_range(y, bm.h);
    }

    static long bm_mask(int x) {
        return ((1L) << (Bitmap.PIXELINWORD-1-(x)));
    }

    private static boolean BM_UGET(Bitmap bm, int x, int y) {
        return(bm_index(bm, x, y) & bm_mask(x)) != 0;
    }

    public static boolean BM_GET(Bitmap bm, int x, int y) {
        return bm_safe(bm, x, y) ? BM_UGET(bm, x, y) : false;
    }

    private static long[] bm_scanline(Bitmap bm, int y) {
        long[] scanLine = new long[bm.dy];
        for (int i = 0; i < bm.dy ; i ++) {
            scanLine[i] = bm.map[(y * bm.dy) + i];
        }
        return scanLine;
    }

    public static long bm_index(Bitmap bm, int x, int y) {
        return bm_scanline(bm, y)[x/Bitmap.PIXELINWORD];
    }

    private static void BM_UCLR(Bitmap bm, int x, int y){
        bm.map[y * bm.dy + (x / Bitmap.PIXELINWORD)] = bm.map[y * bm.dy + (x / Bitmap.PIXELINWORD)] & ~bm_mask(x);
    }

    private static void BM_USET(Bitmap bm, int x, int y) {
        bm.map[y * bm.dy + (x / Bitmap.PIXELINWORD)] = bm.map[y * bm.dy + (x / Bitmap.PIXELINWORD)] | bm_mask(x);
    }

    private static void BM_UPUT(Bitmap bm, int x, int y, boolean b) {
        if (b)
            BM_USET(bm,x, y);
        else
            BM_UCLR(bm, x, y);
    }

    public static void BM_PUT(Bitmap bm, int x, int y, boolean b) {
        if (bm_safe(bm, x, y))
            BM_UPUT(bm, x, y, b);
    }

    /* clear the given BitmapManipulator. Set all bits to c. Assumes a well-formed
    BitmapManipulator. */

    static Bitmap bm_clear(Bitmap bm, int c) {
        for (int y = 0; y < bm.h; y ++) {
            for (int dy = 0; dy < bm.dy; dy ++) {
                int clearedValue = (c == 1 ? -1 : 0);
                if (dy == bm.dy -1) {
                    clearedValue =  clearedValue << (Bitmap.PIXELINWORD - (bm.w % Bitmap.PIXELINWORD));
                }
                bm.map[bm.dy * y + dy] = clearedValue;
            }
        }
        return bm;
    }
}
package AdditionalCode;


import Potrace.General.Bitmap;

/**
 * Created by andreydelany on 24.06.17.
 */
public class BitmapManipulator {

    private static boolean bm_range(int x, int a) {
        return (x) >= 0 && (x) < (a);
    }

    static boolean bm_safe(Bitmap bm, int x, int y) {
        return bm_range(x, bm.w) && bm_range(y, bm.h);
    }

    static long bm_mask(int x) {
        return ((1L) << (Bitmap.PIXELINWORD-1-(x)));
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
}

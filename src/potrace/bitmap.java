package potrace;

public class bitmap {

    public static int PIXELINWORD = 64;
    static long BM_ALLBITS = (~0);
    static long BM_HIBIT = 1 << PIXELINWORD -1;

    public int w, h;              /* width and height, in pixels */
    public int dy;                /* words per scanline (not bytes) */
    public long[] map;             /* raw data, dy*h words */


    public bitmap() {};
    public bitmap(int w, int h) {
        this.w = w;
        this.h = h;
        this.dy = (w - 1) / PIXELINWORD + 1;
        this.map = new long[this.dy * this.h];
    }

    /* macros for accessing pixel at index (x,y). U* macros omit the bounds check. */
    private static boolean bm_range(int x, int a) {
        return (x) >= 0 && (x) < (a);
    }

    static boolean bm_safe(bitmap bm, int x, int y) {
        return bm_range(x, bm.w) && bm_range(y, bm.h);
    }

    static long bm_mask(int x) {
        return ((1L) << (PIXELINWORD-1-(x)));
    }

    private static boolean BM_UGET(bitmap bm, int x, int y) {
        return(bm_index(bm, x, y) & bm_mask(x)) != 0;
    }

    public static boolean BM_GET(bitmap bm, int x, int y) {
        return bm_safe(bm, x, y) ? BM_UGET(bm, x, y) : false;
    }

    private static long[] bm_scanline(bitmap bm, int y) {
        long[] scanLine = new long[bm.dy];
        for (int i = 0; i < bm.dy ; i ++) {
            scanLine[i] = bm.map[(y * bm.dy) + i];
        }
        return scanLine;
    }

    public static long bm_index(bitmap bm, int x, int y) {
        return bm_scanline(bm, y)[x/PIXELINWORD];
    }

    private static void BM_UCLR(bitmap bm, int x, int y){
        bm.map[y * bm.dy + (x / PIXELINWORD)] = bm.map[y * bm.dy + (x / PIXELINWORD)] & ~bm_mask(x);
    }

    private static void BM_USET(bitmap bm, int x, int y) {
        bm.map[y * bm.dy + (x / PIXELINWORD)] = bm.map[y * bm.dy + (x / PIXELINWORD)] | bm_mask(x);
    }

    private static void BM_UPUT(bitmap bm, int x, int y, boolean b) {
        if (b)
            BM_USET(bm,x, y);
        else
            BM_UCLR(bm, x, y);
    }

    public static void BM_PUT(bitmap bm, int x, int y, boolean b) {
        if (bm_safe(bm, x, y))
            BM_UPUT(bm, x, y, b);
    }

    /* clear the given bitmap. Set all bits to c. Assumes a well-formed
    bitmap. */

    static bitmap bm_clear(bitmap bm, int c) {
        for (int y = 0; y < bm.h; y ++) {
            for (int dy = 0; dy < bm.dy; dy ++) {
                int clearedValue = (c == 1 ? -1 : 0);
                if (dy == bm.dy -1) {
                    clearedValue =  clearedValue << (PIXELINWORD - (bm.w % PIXELINWORD));
                }
                bm.map[bm.dy * y + dy] = clearedValue;
            }
        }
        return bm;
    }

    /* duplicate the given bitmap. Return NULL on error with errno set. Assumes a well-formed bitmap. */

    public bitmap bm_dup() {
        bitmap bm1 = new bitmap(this.w, this.h);

        for (int y=0; y < this.h; y++) {
            for (int dy = 0; dy < this.dy; dy ++) {
                bm1.map[y * this.dy + dy] = this.map[y * this.dy + dy];
            }
        }
        return bm1;
    }
}
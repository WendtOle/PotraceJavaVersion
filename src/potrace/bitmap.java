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
    private boolean bm_range(int x, int a) {
        return (x) >= 0 && (x) < (a);
    }

    private boolean bm_range(double x, double a) {
        return bm_range((int)x,(int)a);
    }

    private boolean bm_safe(int x, int y) {
        return bm_range(x, this.w) && bm_range(y, this.h);
    }

    private long bm_mask(int x) {
        return ((1L) << (PIXELINWORD-1-(x)));
    }

    private boolean BM_UGET(int x, int y) {
        return(bm_index(x, y) & bm_mask(x)) != 0;
    }

    public boolean BM_GET(int x, int y) {
        return bm_safe(x, y) ? BM_UGET(x, y) : false;
    }

    private long[] bm_scanline(int y) {
        long[] scanLine = new long[this.dy];
        for (int i = 0; i < this.dy ; i ++) {
            scanLine[i] = this.map[(y * this.dy) + i];
        }
        return scanLine;
    }

    public long bm_index(int x, int y) {
        return bm_scanline(y)[x/PIXELINWORD];
    }

    private void BM_UCLR(int x, int y){
        this.map[y * this.dy + (x / PIXELINWORD)] = this.map[y * this.dy + (x / PIXELINWORD)] & ~bm_mask(x);
    }

    private void BM_USET(int x, int y) {
        this.map[y * this.dy + (x / PIXELINWORD)] = this.map[y * this.dy + (x / PIXELINWORD)] | bm_mask(x);
    }

    private void BM_UPUT(int x, int y, boolean b) {
        if (b)
            BM_USET(x, y);
        else
            BM_UCLR(x, y);
    }

    public void BM_PUT(int x, int y, boolean b) {
        if (bm_safe( x, y))
            BM_UPUT(x, y, b);
    }

    private int getsize(int dy, int h) {
        int size;

        if (dy < 0) {
            dy = -dy;
        }

        size = dy * h * bitmap.PIXELINWORD;

        /* check for overflow error */
        if (size < 0 || (h != 0 && dy != 0 && size / h / dy != bitmap.PIXELINWORD)) {
            return -1;
        }

        return size;
    }

    public int bm_size() {
        return getsize(this.dy, this.h);
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

        if (this == null)
            return null;

        for (int y=0; y < this.h; y++) {
            for (int dy = 0; dy < this.dy; dy ++) {
                bm1.map[y * this.dy + dy] = this.map[y * this.dy + dy];
            }
        }
        return bm1;
    }
}
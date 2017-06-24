package refactored.potrace;

public class Bitmap {

    public static int PIXELINWORD = 64;
    static long BM_ALLBITS = (~0);
    static long BM_HIBIT = 1 << PIXELINWORD -1;

    public int w, h;              /* width and height, in pixels */
    public int dy;                /* words per scanline (not bytes) */
    public long[] map;             /* raw data, potraceWordsInOneLine*height words */


    public Bitmap() {};
    public Bitmap(int w, int h) {
        this.w = w;
        this.h = h;
        this.dy = (w - 1) / PIXELINWORD + 1;
        this.map = new long[this.dy * this.h];
    }

    /* macros for accessing pixel at index (x,y). U* macros omit the bounds check. */
    private boolean bm_range(int x, int a) {
        return (x) >= 0 && (x) < (a);
    }

    boolean bm_safe(int x, int y) {
        return bm_range(x, w) && bm_range(y, h);
    }

    long bm_mask(int x) {
        return ((1L) << (PIXELINWORD-1-(x)));
    }

    private boolean BM_UGET(int x, int y) {
        return(bm_index( x, y) & bm_mask(x)) != 0;
    }

    boolean BM_GET(int x, int y) {
        return bm_safe(x, y) ? BM_UGET(x, y) : false;
    }

    private long[] bm_scanline(int y) {
        long[] scanLine = new long[dy];
        for (int i = 0; i < dy ; i ++) {
            scanLine[i] = map[(y * dy) + i];
        }
        return scanLine;
    }

    long bm_index(int x, int y) {
        return bm_scanline(y)[x/PIXELINWORD];
    }

    private void BM_UCLR(int x, int y){
        map[y * dy + (x / PIXELINWORD)] = map[y * dy + (x / PIXELINWORD)] & ~bm_mask(x);
    }

    private void BM_USET(int x, int y) {
        map[y * dy + (x / PIXELINWORD)] = map[y * dy + (x / PIXELINWORD)] | bm_mask(x);
    }

    private void BM_UPUT(int x, int y, boolean b) {
        if (b)
            BM_USET(x, y);
        else
            BM_UCLR(x, y);
    }

    void BM_PUT(int x, int y, boolean b) {
        if (bm_safe(x, y))
            BM_UPUT(x, y, b);
    }

     void bm_clear_andSetToC(int c) {
        for (int y = 0; y < h; y ++) {
            for (int dyIndex = 0; dyIndex < dy; dyIndex ++) {
                int clearedValue = (c == 1 ? -1 : 0);
                if (dyIndex == dy -1) {
                    clearedValue =  clearedValue << (PIXELINWORD - (w % PIXELINWORD));
                }
                map[dy * y + dyIndex] = clearedValue;
            }
        }
    }

    public Bitmap bm_dup() {
        Bitmap bm1 = new Bitmap(this.w, this.h);

        for (int y=0; y < this.h; y++) {
            for (int dy = 0; dy < this.dy; dy ++) {
                bm1.map[y * this.dy + dy] = this.map[y * this.dy + dy];
            }
        }
        return bm1;
    }

    void bm_clearexcess() {
        long mask;
        int y;

        if (w % PIXELINWORD != 0) {
            mask = BM_ALLBITS << (PIXELINWORD - (w % PIXELINWORD));
            for (y=0; y<h; y++) {
                map[y * dy + dy - 1] = bm_index(w, y) & mask;
            }
        }
    }

    void clear_bm_with_bbox(BBox bbox) {
        int imin = (bbox.x0 / Bitmap.PIXELINWORD);
        int imax = ((bbox.x1 + Bitmap.PIXELINWORD-1) / Bitmap.PIXELINWORD);
        int i, y;

        for (y=bbox.y0; y<bbox.y1; y++) {
            for (i=imin; i<imax; i++) {
                map[y * dy + i] = 0;
            }
        }
    }

    /* return the "majority" value of Bitmap bm at intersection (x,y). We
    assume that the Bitmap is balanced at "radius" 1.  */

    boolean majority(int x, int y) {
        int i, a, ct;

        for (i=2; i<5; i++) { /* check at "radius" i */
            ct = 0;
            for (a=-i+1; a<=i-1; a++) {
                ct += BM_GET(x+a, y+i-1) ? 1 : -1;
                ct += BM_GET(x+i-1, y+a-1) ? 1 : -1;
                ct += BM_GET(x+a-1, y-i) ? 1 : -1;
                ct += BM_GET(x-i, y+a) ? 1 : -1;
            }
            if (ct>0) {
                return true;
            } else if (ct<0) {
                return false;
            }
        }
        return false;
    }
}
package refactored.potrace;

public class Bitmap {

    public static int PIXELINWORD = 64;
    static long BM_ALLBITS = (~0);
    static long BM_HIBIT = 1 << PIXELINWORD -1;

    public int width, height;
    public int wordsPerScanLine;
    public long[] words;


    public Bitmap() {};
    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;
        this.wordsPerScanLine = (width - 1) / PIXELINWORD + 1;
        this.words = new long[this.wordsPerScanLine * this.height];
    }

    /* macros for accessing pixel at index (x,y). U* macros omit the bounds check. */
    private boolean bm_range(int x, int a) {
        return (x) >= 0 && (x) < (a);
    }

    boolean bm_safe(int x, int y) {
        return bm_range(x, width) && bm_range(y, height);
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
        long[] scanLine = new long[wordsPerScanLine];
        for (int i = 0; i < wordsPerScanLine; i ++) {
            scanLine[i] = words[(y * wordsPerScanLine) + i];
        }
        return scanLine;
    }

    long bm_index(int x, int y) {
        return bm_scanline(y)[x/PIXELINWORD];
    }

    private void BM_UCLR(int x, int y){
        words[y * wordsPerScanLine + (x / PIXELINWORD)] = words[y * wordsPerScanLine + (x / PIXELINWORD)] & ~bm_mask(x);
    }

    private void BM_USET(int x, int y) {
        words[y * wordsPerScanLine + (x / PIXELINWORD)] = words[y * wordsPerScanLine + (x / PIXELINWORD)] | bm_mask(x);
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
        for (int y = 0; y < height; y ++) {
            for (int dyIndex = 0; dyIndex < wordsPerScanLine; dyIndex ++) {
                int clearedValue = (c == 1 ? -1 : 0);
                if (dyIndex == wordsPerScanLine -1) {
                    clearedValue =  clearedValue << (PIXELINWORD - (width % PIXELINWORD));
                }
                words[wordsPerScanLine * y + dyIndex] = clearedValue;
            }
        }
    }

    public Bitmap bm_dup() {
        Bitmap bm1 = new Bitmap(this.width, this.height);

        for (int y = 0; y < this.height; y++) {
            for (int dy = 0; dy < this.wordsPerScanLine; dy ++) {
                bm1.words[y * this.wordsPerScanLine + dy] = this.words[y * this.wordsPerScanLine + dy];
            }
        }
        return bm1;
    }

    void bm_clearexcess() {
        long mask;
        int y;

        if (width % PIXELINWORD != 0) {
            mask = BM_ALLBITS << (PIXELINWORD - (width % PIXELINWORD));
            for (y=0; y< height; y++) {
                words[y * wordsPerScanLine + wordsPerScanLine - 1] = bm_index(width, y) & mask;
            }
        }
    }

    void clear_bm_with_bbox(BBox bbox) {
        int imin = (bbox.x0 / Bitmap.PIXELINWORD);
        int imax = ((bbox.x1 + Bitmap.PIXELINWORD-1) / Bitmap.PIXELINWORD);
        int i, y;

        for (y=bbox.y0; y<bbox.y1; y++) {
            for (i=imin; i<imax; i++) {
                words[y * wordsPerScanLine + i] = 0;
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
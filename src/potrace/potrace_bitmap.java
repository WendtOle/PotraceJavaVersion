package potrace;

/**
 * Created by andreydelany on 04/03/2017.
 */
public class potrace_bitmap {

    public static int PIXELINWORD = 32;
    static int BM_ALLBITS = (~0);

    public int w, h;              /* width and height, in pixels */
    public int dy;                /* words per scanline (not bytes) */
    public int[] map;             /* raw data, dy*h words */ //TODO changed representation of potrace words

    static int bm_hibit(){
        return 1 << PIXELINWORD -1;
    }

    static boolean bm_range(int x, int a) {
        return (x) >= 0 && (x) < (a);
    }

    static boolean bm_range(double x, double a) {
        return bm_range((int)x,(int)a);
    }

    static boolean bm_safe(potrace_bitmap bm, int x, int y) {
        return bm_range(x, bm.w) && bm_range(y, bm.h);
    }

    static int bm_mask(int x) {
        return ((1) << (PIXELINWORD-1-x));
    }

    static boolean BM_UGET(potrace_bitmap bm, int x, int y) {
        return(bm_index(bm, x, y) & bm_mask(x)) != 0;
    }

    static boolean BM_GET(potrace_bitmap bm, int x, int y) {
        boolean test = bm_safe(bm, x, y) ? BM_UGET(bm, x, y) : false;
        return bm_safe(bm, x, y) ? BM_UGET(bm, x, y) : false;
    }

    static int[] bm_scanline(potrace_bitmap bm, int y) {
        int[] scanLine = new int[bm.dy];
        for (int i = 0; i < bm.dy ; i ++) {
            scanLine[i] = bm.map[(y * bm.dy) + i];
        }
        return scanLine;
    }

    static int bm_index(potrace_bitmap bm, int x, int y) {
        return bm_scanline(bm,y)[x/PIXELINWORD];
    }


    static int getsize(int dy, int h) {
        int size;

        if (dy < 0) {
            dy = -dy;
        }

        size = dy * h * potrace_bitmap.PIXELINWORD;

  /* check for overflow error */
        if (size < 0 || (h != 0 && dy != 0 && size / h / dy != potrace_bitmap.PIXELINWORD)) {
            return -1;
        }

        return size;
    }

    static int bm_size(potrace_bitmap bm) {
        return getsize(bm.dy, bm.h);
    }

    static potrace_bitmap bm_clear(potrace_bitmap bm, int c) {
        int filler = (c == 0? 0 : -1);
        for (int i = 0; i < bm.map.length; i ++) {
            bm.map[i] = filler;
        }
        potrace_bitmap.bm_clearexcess(bm);
        return bm;
    }

    //TODO new written because in c you it is no difference wether you want to get the value or you want to set the value
    static potrace_bitmap bm_setPotraceWord_WithX(potrace_bitmap bm, int x, int y, int newValue) {
        bm.map[(y * bm.dy)+(x / PIXELINWORD)] = newValue;
        return bm;
    }

    static potrace_bitmap bm_clearexcess(potrace_bitmap bm) {
        int mask;
        int y;

        if (bm.w % potrace_bitmap.PIXELINWORD != 0) {
            mask = BM_ALLBITS << (potrace_bitmap.PIXELINWORD - (bm.w % potrace_bitmap.PIXELINWORD));
            for (y=0; y<bm.h; y++) {
                bm.map[y * bm.dy + bm.dy - 1] = potrace_bitmap.bm_index(bm, bm.w, y) & mask;
            }
        }
        return bm;
    }

    public potrace_bitmap(int w, int h) {
        this.w = w;
        this.h = h;
        this.dy = (w - 1) / PIXELINWORD + 1;
        this.map = new int[this.dy * this.h];
    }


    static potrace_bitmap bm_dup(potrace_bitmap bm) {
        potrace_bitmap bm1 = new potrace_bitmap(bm.w, bm.h);

        for (int y=0; y < bm.h; y++) {
            for (int dy = 0; dy < bm.dy; dy ++) {                   //TODO check wether this works correct
                bm1.map[y * bm.dy + dy] = bm.map[y * bm.dy + dy];
            }
        }
        return bm1;
    }
}

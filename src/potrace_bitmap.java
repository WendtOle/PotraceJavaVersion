/**
 * Created by andreydelany on 04/03/2017.
 */
public class potrace_bitmap {

    static int PIXELINWORD = 32;

    int w, h;              /* width and height, in pixels */
    int dy;                /* words per scanline (not bytes) */
    int[] map;             /* raw data, dy*h words */ //TODO changed representation of potrace words

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
        boolean test = (bm_index(bm, x, y) & bm_mask(x)) != 0;
        int test1 = bm_index(bm, x, y);
        int test2 = bm_mask(x);
        return(bm_index(bm, x, y) & bm_mask(x)) != 0;
    }

    static boolean BM_GET(potrace_bitmap bm, int x, int y) {
        boolean test = bm_safe(bm, x, y) ? BM_UGET(bm, x, y) : false;
        return bm_safe(bm, x, y) ? BM_UGET(bm, x, y) : false;
    }

    static int[] bm_scanline(potrace_bitmap bm, int y) {
        int[] scanLine = new int[bm.dy];
        for (int i = 0; i < bm.dy ; i ++) {
            scanLine[i] = bm.map[y * bm.dy + i];
        }
        return scanLine;
    }

    static int bm_index(potrace_bitmap bm, int x, int y) {
        return bm_scanline(bm,y)[x/PIXELINWORD];
    }

    //TODO new written because in c you it is no difference wether you want to get the value or you want to set the value
    static potrace_bitmap bm_setPotraceWord(potrace_bitmap bm, int x, int y, int newValue) {
        bm.map[y + bm.dy - 1] = newValue;
        return bm;
    }

    public potrace_bitmap (){
    }

    public potrace_bitmap(int w, int h) {
        this.w = w;
        this.h = h;
        this.dy = (w - 1) / PIXELINWORD + 1;
        this.map = new int[this.dy * this.h];
    }

    public void default_bitmap_simple() {
        this.map[3]= 0x90000000;            // X o o X
        this.map[2]= 0xE0000000;            // X X X o
        this.map[1]= 0x30000000;            // o o X X
        this.map[0]= 0x90000000;            // X o o X
    }

    public void default_bitmap_normal() {
        this.map[6]= 0xfe000000;            //  X X X X X X X
        this.map[5]= 0x82000000;            //  X o o o o o X
        this.map[4]= 0xba000000;            //  X o X X X o X
        this.map[3]= 0xaa000000;            //  X o X o X o X
        this.map[2]= 0xba000000;            //  X o X X X o X
        this.map[1]= 0x82000000;            //  X o o o o o X
        this.map[0]= 0xfe000000;            //  X X X X X X X
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

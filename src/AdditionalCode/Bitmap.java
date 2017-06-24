package AdditionalCode;

/**
 * Created by andreydelany on 24.06.17.
 */
public class Bitmap {
    public static int PIXELINWORD = 64;

    public int width, height;
    public int potraceWordsInOneLine;
    public long[] potraceWords;

    public Bitmap(){};
    public Bitmap(int w, int h) {
        setDimensions(w,h);
    }

    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
        this.potraceWordsInOneLine = (width - 1) / PIXELINWORD + 1;
        this.potraceWords = new long[this.potraceWordsInOneLine * this.height];
    }

    private static boolean bm_range(int x, int a) {
        return (x) >= 0 && (x) < (a);
    }

    static boolean bm_safe(Bitmap bm, int x, int y) {
        return bm_range(x, bm.width) && bm_range(y, bm.height);
    }

    static long bm_mask(int x) {
        return ((1L) << (PIXELINWORD-1-(x)));
    }

    private static void BM_UCLR(Bitmap bm, int x, int y){
        int indexOfPotraceWord = y * bm.potraceWordsInOneLine + (x / PIXELINWORD);
        bm.potraceWords[indexOfPotraceWord] = bm.potraceWords[indexOfPotraceWord] & ~bm_mask(x);
    }

    private static void BM_USET(Bitmap bm, int x, int y) {
        int indexOfPotraceWord = y * bm.potraceWordsInOneLine + (x / PIXELINWORD);
        bm.potraceWords[indexOfPotraceWord] = bm.potraceWords[indexOfPotraceWord] | bm_mask(x);
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

package refactored;

public class Bitmap{

    public static int PIXELINWORD = 64;
    public static long BM_ALLBITS = (~0);
    public static long BM_HIBIT = 1 << PIXELINWORD -1;

    public int width, height;
    public int wordsPerScanLine;
    public long[] words;


    public Bitmap() {}

    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;
        this.wordsPerScanLine = (width - 1) / PIXELINWORD + 1;
        this.words = new long[this.wordsPerScanLine * this.height];
    }

    public Bitmap duplicate() {
        Bitmap duplicatedBitmap = new Bitmap(this.width, this.height);

        for (int y = 0; y < height; y++) {
            for (int dy = 0; dy < wordsPerScanLine; dy ++) {
                duplicatedBitmap.words[y * wordsPerScanLine + dy] = words[y * wordsPerScanLine + dy];
            }
        }
        return duplicatedBitmap;
    }
}
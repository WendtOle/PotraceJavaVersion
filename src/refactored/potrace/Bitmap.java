package refactored.potrace;

import java.awt.*;

public class Bitmap {

    public static int PIXELINWORD = 64;
    static long BM_ALLBITS = (~0);
    static long BM_HIBIT = 1 << PIXELINWORD -1;

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

    private boolean isCoordinateInRange(int coordinate, int upperBound) {
        return (coordinate) >= 0 && (coordinate) < (upperBound);
    }

    boolean isPixelInRange(Point pixel) {
        return isCoordinateInRange(pixel.x, width) && isCoordinateInRange(pixel.y, height);
    }

    long getMaskForPosition(int position) {
        return ((1L) << (PIXELINWORD-1-(position)));
    }

    private boolean getPixelValueWithoutBoundChecking(Point pixel) {
        long pixelValue = getWordWherePixelIsContained(pixel) & getMaskForPosition(pixel.x);
        return(pixelValue != 0);
    }

    boolean getPixelValue(Point pixel) {
        return isPixelInRange(pixel) && getPixelValueWithoutBoundChecking(pixel);
    }

    private long[] getLineWherePixelIsContained(int y) {
        long[] scanLine = new long[wordsPerScanLine];
        for (int i = 0; i < wordsPerScanLine; i ++) {
            scanLine[i] = words[(y * wordsPerScanLine) + i];
        }
        return scanLine;
    }

    public long getWordWherePixelIsContained(Point pixel) {
        return getLineWherePixelIsContained(pixel.y)[pixel.x/PIXELINWORD];
    }

    public Bitmap duplicate() {
        Bitmap duplicatedBitmap = new Bitmap(this.width, this.height);

        for (int y = 0; y < this.height; y++) {
            for (int dy = 0; dy < this.wordsPerScanLine; dy ++) {
                duplicatedBitmap.words[y * this.wordsPerScanLine + dy] = this.words[y * this.wordsPerScanLine + dy];
            }
        }
        return duplicatedBitmap;
    }
}
package refactored.potrace;

import java.awt.*;

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

    long getWordWherePixelIsContained(Point pixel) {
        return getLineWherePixelIsContained(pixel.y)[pixel.x/PIXELINWORD];
    }

    private void clearPixel(Point pixel){
        int accessIndex = pixel.y * wordsPerScanLine + (pixel.x / PIXELINWORD);
        words[accessIndex] = words[accessIndex] & ~getMaskForPosition(pixel.x);
    }

    private void fillPixel(Point pixel) {
        int accessIndex = pixel.y * wordsPerScanLine + (pixel.x / PIXELINWORD);
        words[accessIndex] = words[accessIndex] | getMaskForPosition(pixel.x);
    }

    private void setPixelToValueWithoutBoundChecking(Point pixel, boolean shallPixelFilled) {
        if (shallPixelFilled)
            fillPixel(pixel);
        else
            clearPixel(pixel);
    }

    void setPixelToValue(Point pixel, boolean b) {
        if (isPixelInRange(pixel))
            setPixelToValueWithoutBoundChecking(pixel, b);
    }

     void setWholeBitmapToSpecificValue(int c) {
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

    public Bitmap duplicate() {
        Bitmap duplicatedBitmap = new Bitmap(this.width, this.height);

        for (int y = 0; y < this.height; y++) {
            for (int dy = 0; dy < this.wordsPerScanLine; dy ++) {
                duplicatedBitmap.words[y * this.wordsPerScanLine + dy] = this.words[y * this.wordsPerScanLine + dy];
            }
        }
        return duplicatedBitmap;
    }

    void clearExcessPixelsOfBitmap() {
        if (width % PIXELINWORD != 0) {
            long mask = BM_ALLBITS << (PIXELINWORD - (width % PIXELINWORD));
            for (int y = 0; y < height; y ++) {
                words[y * wordsPerScanLine + wordsPerScanLine - 1] = getWordWherePixelIsContained(new Point(width, y)) & mask;
            }
        }
    }

    void clearBitmapWithBBox(BBox bbox) {
        int imin = (bbox.x0 / Bitmap.PIXELINWORD);
        int imax = ((bbox.x1 + Bitmap.PIXELINWORD-1) / Bitmap.PIXELINWORD);

        for (int y = bbox.y0; y < bbox.y1; y ++) {
            for (int i = imin; i<imax; i++) {
                words[y * wordsPerScanLine + i] = 0;
            }
        }
    }

    /* We assume that the Bitmap is balanced at "radius" 1.  */

    boolean getMajorityValueAtIntersection(int x, int y) {
        int i, a, ct;

        for (i=2; i<5; i++) { /* check at "radius" i */
            ct = 0;
            for (a=-i+1; a<=i-1; a++) {
                ct += getPixelValue(new Point(x+a, y+i-1)) ? 1 : -1;
                ct += getPixelValue(new Point(x+i-1, y+a-1)) ? 1 : -1;
                ct += getPixelValue(new Point(x+a-1, y-i)) ? 1 : -1;
                ct += getPixelValue(new Point(x-i, y+a)) ? 1 : -1;
            }
            if (ct>0) {
                return true;
            } else if (ct<0) {
                return false;
            }
        }
        return false;
    }

    /* Here xa must be a multiple of BM_WORDBITS. */

    void invertBitsInWordsWhichAreInRangeFromXToXAInLine(int x, int y, int xa) {
        int beginningIndexOfStartWord = x & - Bitmap.PIXELINWORD;
        int indexOfXInStartWord = x & (Bitmap.PIXELINWORD-1);

        flipAllContainedWordsInLineBetweenToValues(xa,beginningIndexOfStartWord,y);
        flipBitsUnitStartPositionOfStartWord(y, beginningIndexOfStartWord, indexOfXInStartWord);
    }

    private void flipBitsUnitStartPositionOfStartWord(int y, int beginningPositionOfStartWord, int indexOfXInStartWord) {
        if (indexOfXInStartWord > 0) {
            int accessIndex = (wordsPerScanLine * y) + (beginningPositionOfStartWord / Bitmap.PIXELINWORD);
            long mask = Bitmap.BM_ALLBITS << (Bitmap.PIXELINWORD - indexOfXInStartWord);
            words[accessIndex] = words[accessIndex]  ^ mask;
        }
    }

    private void flipAllContainedWordsInLineBetweenToValues(int firstValue, int secondValue, int y) {
        int startX = firstValue;
        int endX = secondValue;

        if (endX < startX) {
            int temp = startX;
            startX = endX;
            endX = temp;
        }

        for(int i = startX; i < endX; i += Bitmap.PIXELINWORD) {
            flipAllBitsInWord(i, y);
        }
    }

    private void flipAllBitsInWord(int x, int y) {
        int indexOfWord = (wordsPerScanLine * y) + (x / Bitmap.PIXELINWORD);
        words[indexOfWord] = words[indexOfWord]  ^ Bitmap.BM_ALLBITS;
    }

    /* Note: the Path must be within the dimensions of the pixmap. */

    public void invertPathOnBitmap(Path path) {
        if (path.priv.len <= 0) {  /* a Path of length 0 is silly, but legal */
            return;
        }

        int y1 = path.priv.pt[path.priv.len-1].y;
        int xa = path.priv.pt[0].x & - Bitmap.PIXELINWORD;

        for (int k = 0; k < path.priv.len; k ++) {
            int x = path.priv.pt[k].x;
            int y = path.priv.pt[k].y;

            if (y != y1) {
                /* efficiently invert the rectangle [x,xa] x [y,y1] */
                invertBitsInWordsWhichAreInRangeFromXToXAInLine( x, Auxiliary.min(y,y1), xa);
                y1 = y;
            }
        }
    }

    public Point findNextFilledPixel(Point startPointforSearch) {
        int x0 = getBeginningIndexOfCurrentWord(startPointforSearch.x);

        for (int y=startPointforSearch.y; y>=0; y--) {
            for (int x = x0; x<width && x>=0; x+=PIXELINWORD) {

                if (getWordWherePixelIsContained(new Point(x, y)) != 0) {
                    while (!getPixelValue(new Point(x, y))) {
                        x++;
                    }
                    return new Point(x,y);
                }
            }
            x0 = 0;
        }
        return null;
    }

    private int getBeginningIndexOfCurrentWord(int index) {
        return (index) & ~(Bitmap.PIXELINWORD-1);
    }
}
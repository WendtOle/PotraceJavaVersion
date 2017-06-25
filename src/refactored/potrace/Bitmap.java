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

    boolean isPixelInRange(int x, int y) {
        return isCoordinateInRange(x, width) && isCoordinateInRange(y, height);
    }

    long getMaskForPosition(int position) {
        return ((1L) << (PIXELINWORD-1-(position)));
    }

    private boolean getPixelValueWithoutBoundChecking(int x, int y) {
        return(getWordWherePixelIsContained( x, y) & getMaskForPosition(x)) != 0;
    }

    boolean getPixelValue(int x, int y) {
        return isPixelInRange(x, y) ? getPixelValueWithoutBoundChecking(x, y) : false;
    }

    private long[] getLineWherePixelIsContained(int y) {
        long[] scanLine = new long[wordsPerScanLine];
        for (int i = 0; i < wordsPerScanLine; i ++) {
            scanLine[i] = words[(y * wordsPerScanLine) + i];
        }
        return scanLine;
    }

    long getWordWherePixelIsContained(int x, int y) {
        return getLineWherePixelIsContained(y)[x/PIXELINWORD];
    }

    private void clearPixel(int x, int y){
        words[y * wordsPerScanLine + (x / PIXELINWORD)] = words[y * wordsPerScanLine + (x / PIXELINWORD)] & ~getMaskForPosition(x);
    }

    private void fillPixel(int x, int y) {
        words[y * wordsPerScanLine + (x / PIXELINWORD)] = words[y * wordsPerScanLine + (x / PIXELINWORD)] | getMaskForPosition(x);
    }

    private void setPixelToValueWithoutBoundChecking(int x, int y, boolean shallPixelFilled) {
        if (shallPixelFilled)
            fillPixel(x, y);
        else
            clearPixel(x, y);
    }

    void setPixelToValue(int x, int y, boolean b) {
        if (isPixelInRange(x, y))
            setPixelToValueWithoutBoundChecking(x, y, b);
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

    void deleteExcessPixelsOfBitmap() {
        long mask;

        if (width % PIXELINWORD != 0) {
            mask = BM_ALLBITS << (PIXELINWORD - (width % PIXELINWORD));
            for (int y = 0; y < height; y ++) {
                words[y * wordsPerScanLine + wordsPerScanLine - 1] = getWordWherePixelIsContained(width, y) & mask;
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
                ct += getPixelValue(x+a, y+i-1) ? 1 : -1;
                ct += getPixelValue(x+i-1, y+a-1) ? 1 : -1;
                ct += getPixelValue(x+a-1, y-i) ? 1 : -1;
                ct += getPixelValue(x-i, y+a) ? 1 : -1;
            }
            if (ct>0) {
                return true;
            } else if (ct<0) {
                return false;
            }
        }
        return false;
    }

    /* efficiently invert bits [x,infty) and [xa,infty) in line y. Here xa
    must be a multiple of BM_WORDBITS. */

    void xor_to_ref(int x, int y, int xa) {
        int xhi = x & - Bitmap.PIXELINWORD;
        int xlo = x & (Bitmap.PIXELINWORD-1);  /* = x % BM_WORDBITS */

        if (xhi<xa) {
            for (int i = xhi; i < xa; i+= Bitmap.PIXELINWORD) {
                int accessIndex = (wordsPerScanLine * y) + (i / Bitmap.PIXELINWORD);
                words[accessIndex] = words[accessIndex]  ^ Bitmap.BM_ALLBITS; //Todo check
            }
        } else {
            for (int i = xa; i < xhi; i+= Bitmap.PIXELINWORD) {
                int accessIndex = (wordsPerScanLine * y) + (i / Bitmap.PIXELINWORD);
                words[accessIndex] = words[accessIndex]  ^ Bitmap.BM_ALLBITS; //Todo check
            }
        }

        // note: the following "if" is needed because x86 treats a<<b as
        //a<<(b&31). I spent hours looking for this bug.
        if (xlo > 0) {
            int accessIndex = (wordsPerScanLine * y) + (xhi / Bitmap.PIXELINWORD);
            words[accessIndex] = words[accessIndex]  ^ (Bitmap.BM_ALLBITS << (Bitmap.PIXELINWORD - xlo)); //Todo check
        }
    }

    /* xor the given pixmap with the interior of the given Path. Note: the
    Path must be within the dimensions of the pixmap. */

    public void xor_path(Path path) {
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
                xor_to_ref( x, Auxiliary.min(y,y1), xa);
                y1 = y;
            }
        }
    }

    /* find the next set pixel in a row <= y. Pixels are searched first
    left-to-right, then top-down. In other words, (x,y)<(x',y') if y>y'
    or y=y' and x<x'. If found, return 0 and store pixel in
    (*xp,*yp). Else return 1. Note that this function assumes that
    excess bytes have been cleared with deleteExcessPixelsOfBitmap. */

    public Point findNextPositionOfFilledPixel(Point startPointforSearch) {
        int x0 = (startPointforSearch.x) & ~(Bitmap.PIXELINWORD-1);

        for (int y=startPointforSearch.y; y>=0; y--) {
            for (int x = x0; x<width && x>=0; x+=PIXELINWORD) {

                if (getWordWherePixelIsContained(x, y) != 0) {
                    while (!getPixelValue(x, y)) {
                        x++;
                    }
                    return new Point(x,y);
                }
            }
            x0 = 0;
        }
        return null;
    }
}
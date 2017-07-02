package refactored.potrace;

/**
 * Created by andreydelany on 02.07.17.
 */
public class ClearBitmapWithBBox {

    Bitmap bitmap;

    public ClearBitmapWithBBox(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void clearBitmapWithBBox(BBox bbox) {
        int imin = (bbox.x0 / Bitmap.PIXELINWORD);
        int imax = ((bbox.x1 + Bitmap.PIXELINWORD-1) / Bitmap.PIXELINWORD);

        for (int y = bbox.y0; y < bbox.y1; y ++) {
            for (int i = imin; i<imax; i++) {
                bitmap.words[y * bitmap.wordsPerScanLine + i] = 0;
            }
        }
    }
}

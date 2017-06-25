package refactored.potrace;

public class BBox {
    int x0, x1, y0, y1;

    /* Find the bounding box of a given Path. Path is assumed to be of
    non-zero length. */

    public BBox(){};

    public BBox(Path p) {
        y0 = Integer.MAX_VALUE;
        y1 = 0;
        x0 = Integer.MAX_VALUE;
        x1 = 0;

        for (int k=0; k<p.priv.len; k++) {
            int x = p.priv.pt[k].x;
            int y = p.priv.pt[k].y;

            if (x < x0) {
                x0 = x;
            }
            if (x > x1) {
                x1 = x;
            }
            if (y < y0) {
                y0 = y;
            }
            if (y > y1) {
                y1 = y;
            }
        }
    }
}

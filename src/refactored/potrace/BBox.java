package refactored.potrace;

public class BBox {
    int x0, x1, y0, y1;

    /* Find the bounding box of a given Path. Path is assumed to be of
    non-zero length. */

    static void setbbox_path(BBox bbox, Path p) {
        int x, y;
        int k;

        bbox.y0 = Integer.MAX_VALUE;
        bbox.y1 = 0;
        bbox.x0 = Integer.MAX_VALUE;
        bbox.x1 = 0;

        for (k=0; k<p.priv.len; k++) {
            x = p.priv.pt[k].x;
            y = p.priv.pt[k].y;

            if (x < bbox.x0) {
                bbox.x0 = x;
            }
            if (x > bbox.x1) {
                bbox.x1 = x;
            }
            if (y < bbox.y0) {
                bbox.y0 = y;
            }
            if (y > bbox.y1) {
                bbox.y1 = y;
            }
        }
    }
}

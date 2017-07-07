package refactored.potrace.BitmapDecomposition;

import refactored.potrace.Path;

public class BoundingBox {
    int x0, x1, y0, y1;

    public BoundingBox(){};

    public BoundingBox(Path path){
        setToBoundingBoxOfPath(path);
    }

    public void setToBoundingBoxOfPath(Path path) {
        y0 = Integer.MAX_VALUE;
        y1 = 0;
        x0 = Integer.MAX_VALUE;
        x1 = 0;

        for (int k = 0; k < path.priv.len; k ++) {
            int x = path.priv.pt[k].x;
            int y = path.priv.pt[k].y;

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

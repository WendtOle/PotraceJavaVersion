package refactored;

import General.Path;

import java.awt.*;

public class BoundingBox {
    int y0 = Integer.MAX_VALUE;
    int y1 = 0;
    int x0 = Integer.MAX_VALUE;
    int x1 = 0;

    public BoundingBox(Path path){
        for (int i = 0; i < path.priv.len; i++) {
            Point currentPoint = path.priv.pt[i];
            updateBoundingBoxDimensions(currentPoint);
        }
    }

    private void updateBoundingBoxDimensions(Point currentPoint) {
        updateHorizontalDimensionsOfBoundingBox(currentPoint.x);
        updateVerticalDimensionsOfBoundingBox(currentPoint.y);
    }

    private void updateHorizontalDimensionsOfBoundingBox(int currentLocation) {
        if (currentLocation < x0)
            x0 = currentLocation;
        if (currentLocation > x1)
            x1 = currentLocation;
    }

    private void updateVerticalDimensionsOfBoundingBox(int currentLocation) {
        if (currentLocation < y0)
            y0 = currentLocation;
        if (currentLocation > y1)
            y1 = currentLocation;
    }
}
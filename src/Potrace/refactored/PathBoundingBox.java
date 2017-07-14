package Potrace.refactored;

import Potrace.General.Path;
import java.awt.*;

public class PathBoundingBox {
    int y0, y1, x0, x1;

    public PathBoundingBox(Path path){
        initializeDimensionsOfBoundingBox();
        matchDimensionsOfPathWithBoundingBox(path);
    }

    private void initializeDimensionsOfBoundingBox() {
        x0 = y0 = Integer.MAX_VALUE;
        x1 = y1 = 0;
    }

    private void matchDimensionsOfPathWithBoundingBox(Path path) {
        for (int currentPointIndex = 0; currentPointIndex < path.priv.len; currentPointIndex++) {
            Point currentPoint = path.priv.pt[currentPointIndex];
            updateBoundingBoxDimensionsWithPointOfPath(currentPoint);
        }
    }

    private void updateBoundingBoxDimensionsWithPointOfPath(Point point) {
        updateHorizontalDimensionsOfBoundingBox(point.x);
        updateVerticalDimensionsOfBoundingBox(point.y);
    }

    private void updateHorizontalDimensionsOfBoundingBox(int horizontalLocation) {
        if (horizontalLocation < x0)
            x0 = horizontalLocation;
        if (horizontalLocation > x1)
            x1 = horizontalLocation;
    }

    private void updateVerticalDimensionsOfBoundingBox(int verticalLocation) {
        if (verticalLocation < y0)
            y0 = verticalLocation;
        if (verticalLocation > y1)
            y1 = verticalLocation;
    }
}
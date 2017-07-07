package refactored.potrace.BitmapDecomposition;

import General.*;

/**
 * Created by andreydelany on 07.07.17.
 */
public class PathReStructrurer {

    public static Path saveOriginalNextPointerToSiblingComponent(Path pathList) {
        for (Path path = pathList; path != null; path = path.next) {
            path.sibling = path.next;
            path.childlist = null;
        }
        return pathList;
    }

    public static Path copySiblingStructurFromNextToSiblingComponent(Path pathList) {
        Path path = pathList;
        while (path != null) {
            Path p1 = path.sibling;
            path.sibling = path.next;
            path = p1;
        }
        return pathList;
    }

}

package Potrace.refactored;

import Potrace.General.*;

/**
 * Created by andreydelany on 07.07.17.
 */
public class PathReStructurer {

    public static Path saveOriginalNextPointerToSiblingComponent(Path pathList) {
        for (Path path = pathList; path != null; path = path.next) {
            path.sibling = path.next; //Todo to long
            path.childlist = null;
        }
        return pathList;
    }

    public static Path copySiblingStructurFromNextToSiblingComponent(Path pathList) {
        Path path = pathList; //Todo to long
        while (path != null) {
            Path p1 = path.sibling;
            path.sibling = path.next;
            path = p1;
        }
        return pathList;
    }
}
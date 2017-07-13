package Potrace.refactored;

import Potrace.General.Path;

/**
 * Created by andreydelany on 13.07.17.
 */
public class PathRestructerer {

    Path pathList;
    Path currentPath;

    public PathRestructerer(Path pathList){
        this.pathList = pathList;
        this.currentPath = pathList;
    }

    public Path saveOriginalNextPointerToSiblingComponent() {
        while(stillNeedToRestructurePaths()){
            saveNextComponentInSibling();
        }
        return pathList;
    }

    private boolean stillNeedToRestructurePaths() {
        return currentPath != null;
    }

    private void saveNextComponentInSibling() {
        currentPath.sibling = currentPath.next;
        currentPath.childlist = null;
        currentPath = currentPath.next;
    }

    public Path copySiblingStructurFromNextToSiblingComponent() {
        while (stillNeedToRestructurePaths())
            copyNextIntoSiblingAndProcessSiblingComponentLater();
        return pathList;
    }

    private void copyNextIntoSiblingAndProcessSiblingComponentLater() {
        Path temp = currentPath.sibling;
        currentPath.sibling = currentPath.next;
        currentPath = temp;
    }
}

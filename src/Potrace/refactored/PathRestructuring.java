package Potrace.refactored;

import Potrace.General.Path;

/**
 * Created by andreydelany on 13.07.17.
 */
public class PathRestructuring {

    Path pathList;
    Path currentPath;

    public PathRestructuring(Path pathList){
        this.pathList = pathList;
        this.currentPath = pathList;
    }

    public Path saveOriginalNextPointerToSiblingComponent() {
        while(stillNeedToRestructurePaths()){
            saveNextComponentInSibling();
        }
        return pathList;
    }

    public Path copySiblingStructureFromNextToSiblingComponent() {
        while (stillNeedToRestructurePaths())
            copyNextIntoSiblingAndProcessSiblingComponentLater();
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

    private void copyNextIntoSiblingAndProcessSiblingComponentLater() {
        Path temp = currentPath.sibling;
        currentPath.sibling = currentPath.next;
        currentPath = temp;
    }
}
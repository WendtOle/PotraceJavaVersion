package Tools;

import potrace.path;

public class PathIterator {
    path list;
    path currentPath;

    public PathIterator(path path) {
        this.list = path;
        this.currentPath = path;
    }

    private boolean hasNext(){
        return currentPath.next != null;
    }

    private  void goToNextPath() {
        currentPath = currentPath.next;
    }

    public int getAmountOfPathes() {
        int amount = 1;
        while (hasNext()){
            amount ++;
            goToNextPath();
        }
        currentPath = list;
        return amount;
    }

    public path getPathAtIndex(int index) {
        currentPath = list;
        int currentIndex = 0;
        while (index != currentIndex) {
            currentIndex ++;
            goToNextPath();
        }
        return currentPath;
    }
}

package Tools;

import potraceOriginal.Path;

public class PathIterator {
    Path list;
    Path currentPath;

    public PathIterator(Path path) {
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

    public Path getPathAtIndex(int index) {
        currentPath = list;
        int currentIndex = 0;
        while (index != currentIndex) {
            currentIndex ++;
            goToNextPath();
        }
        return currentPath;
    }
}

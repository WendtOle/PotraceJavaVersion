package Tools;

import potrace.path;

/**
 * Created by andreydelany on 11/04/2017.
 */
public class PathCounter {
    path list;
    path currentPath;

    public PathCounter(path path) {
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

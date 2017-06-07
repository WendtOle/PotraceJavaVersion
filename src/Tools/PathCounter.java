package Tools;

import potrace.path;

/**
 * Created by andreydelany on 11/04/2017.
 */
public class PathCounter {
    path currentPaht;

    public PathCounter(path path) {
        this.currentPaht = path;
    }

    public boolean hasNext(){
        return currentPaht.next != null;
    }

    public path getCurrentPath() {
        return currentPaht;
    }

    public boolean goToNextPath() {
        if(hasNext()) {
            currentPaht = currentPaht.next;
            return true;
        } else
            return false;

    }
}

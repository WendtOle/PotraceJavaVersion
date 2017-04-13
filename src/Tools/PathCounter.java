package Tools;

import potrace.potrace_path;

/**
 * Created by andreydelany on 11/04/2017.
 */
public class PathCounter {
    potrace_path currentPaht;

    public PathCounter(potrace_path path) {
        this.currentPaht = path;
    }

    public boolean hasNext(){
        return currentPaht.next != null;
    }

    public potrace_path getCurrentPaht() {
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

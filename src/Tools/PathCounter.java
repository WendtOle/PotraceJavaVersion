package Tools;

import potrace.potrace_path;

/**
 * Created by andreydelany on 11/04/2017.
 */
public class PathCounter {
    public static int count(potrace_path startPath){
        int counter = 1;
        potrace_path currentPath = startPath;
        while (currentPath.next != null) {
            counter ++;
            currentPath = currentPath.next;
        }
        return counter;
    }
}

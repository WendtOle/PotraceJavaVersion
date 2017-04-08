package Tools;

import potrace.*;

import java.awt.*;

/**
 * Created by andreydelany on 08/04/2017.
 */
public class PathFinder {
    public static potrace_path findOriginalBitmap(potrace_bitmap bitmap){
        potrace_param param = new potrace_param();
        int x;
        int y;
        potrace_path p;
        potrace_path plist = null;  // linked potrace.list of path objects
        //potrace.potrace_path plist_hook = null;  // used to speed up appending to linked potrace.list
        potrace_bitmap bm1 = bitmap.bm_dup();
        int sign;

        //be sure the byte padding on the right is set to 0, as the fast
        //pixel search below relies on it
        bm1.bm_clearexcess();

        // iterate through components
        x = 0;
        y = bm1.h - 1;
        Point xy = new Point(x,y);
        while ((xy = decompose.findnext(bm1,xy)) != null) {
            // calculate the sign by looking at the original bitmap, bm1 wird immer wieder invertiert nachdem ein pfad entfernt wurde.
            // mit dem nachgucken nach dem sign in der original bitmap bekommt einen eindruck darüber ob es ein wirklicher pfad ist oder nur der ausschnitt von einen pfad, also das innnere
            sign = bitmap.BM_GET(xy.x, xy.y) ? '+' : '-';

            // calculate the path
            p = decompose.findpath(bm1, xy.x, xy.y+1, sign, param.turnpolicy);

            // update buffered image
            bm1 = decompose.xor_path(bm1, p);

            // if it's a turd, eliminate it, else append it to the potrace.list
            if (p.area > param.turdsize) {

                //TODO Originally it was made with a plist_hook, with which it was easier and faster to append a element at the end of the linkedlist
                plist = list.unefficient_list_insert_beforehook(p,plist);
            }
        }
        return plist;
    }
}

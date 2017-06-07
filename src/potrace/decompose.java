package potrace;

import java.awt.*;

public class decompose {

    static boolean detrand(int x, int y) {
        int z; //TODO unsigned
        char t[] = {
        /* non-linear sequence: constant term of inverse in GF(8),
        mod x^8+x^4+x^3+x+1 */
            0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1,
                    0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0,
                    0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1,
                    1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1,
                    0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0,
                    0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0,
                    0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0,
                    0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1,
                    1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0,
                    0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1,
                    1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0,
        };

        /* 0x04b3e375 and 0x05a8ef93 are chosen to contain every possible
        5-bit sequence */
        z = ((0x04b3e375 * x) ^ y) * 0x05a8ef93;
        z = t[z & 0xff] ^ t[(z>>8) & 0xff] ^ t[(z>>16) & 0xff] ^ t[(z>>24) & 0xff];
        return z == 1 ? true : false;
    }


    /* ---------------------------------------------------------------------- */
    /* auxiliary bitmap manipulations */

    /* set the excess padding to 0 */
    public static void bm_clearexcess(bitmap bm) {
        long mask;
        int y;

        if (bm.w % bitmap.PIXELINWORD != 0) {
            mask = bitmap.BM_ALLBITS << (bitmap.PIXELINWORD - (bm.w % bitmap.PIXELINWORD));
            for (y=0; y<bm.h; y++) {
                bm.map[y * bm.dy + bm.dy - 1] = bm.bm_index(bm.w, y) & mask;
            }
        }
    }

    /* clear the bm, assuming the bounding box is set correctly (faster
    than clearing the whole bitmap) */

    static void clear_bm_with_bbox(bitmap bm, bbox bbox) {
        int imin = (bbox.x0 / bitmap.PIXELINWORD);
        int imax = ((bbox.x1 + bitmap.PIXELINWORD-1) / bitmap.PIXELINWORD);
        int i, y;

        for (y=bbox.y0; y<bbox.y1; y++) {
            for (i=imin; i<imax; i++) {
                bm.map[y * bm.dy + i] = 0;
            }
        }
    }

    /* ---------------------------------------------------------------------- */
    /* auxiliary functions */

    /* return the "majority" value of bitmap bm at intersection (x,y). We
    assume that the bitmap is balanced at "radius" 1.  */

    static boolean majority(bitmap bm, int x, int y) {
        int i, a, ct;

        for (i=2; i<5; i++) { /* check at "radius" i */
            ct = 0;
            for (a=-i+1; a<=i-1; a++) {
                ct += bm.BM_GET(x+a, y+i-1) ? 1 : -1;
                ct += bm.BM_GET(x+i-1, y+a-1) ? 1 : -1;
                ct += bm.BM_GET(x+a-1, y-i) ? 1 : -1;
                ct += bm.BM_GET(x-i, y+a) ? 1 : -1;
            }
            if (ct>0) {
                return true; //TODO is 1 true and 0 false? really?
            } else if (ct<0) {
                return false;
            }
        }
        return false; //TODO Find way to run this line for code coverage
    }

    /* ---------------------------------------------------------------------- */
    /* decompose image into paths */

    /* efficiently invert bits [x,infty) and [xa,infty) in line y. Here xa
    must be a multiple of BM_WORDBITS. */


    /*Ich musste hier den Code etwas verändern. In C wurde einfach ^= verwendet, was mir nicht möglich war,
    ich musste zuerst mir den Wert holen, ihn verunden und dann überschreiben, im wesendtlichen aber gleich */

    static void xor_to_ref(bitmap bm, int x, int y, int xa) {
        int xhi = x & - bitmap.PIXELINWORD;
        int xlo = x & (bitmap.PIXELINWORD-1);  /* = x % BM_WORDBITS */
        int i;

        if (xhi<xa) {           //Todo find case in which this line is run for testing
            for (i = xhi; i < xa; i+= bitmap.PIXELINWORD) {
                int accessIndex = (bm.dy * y) + (i / bitmap.PIXELINWORD);
                bm.map[accessIndex] = bm.map[accessIndex]  ^ bitmap.BM_ALLBITS; //Todo check
            }
        } else {
            for (i = xa; i < xhi; i+= bitmap.PIXELINWORD) {
                int accessIndex = (bm.dy * y) + (i / bitmap.PIXELINWORD);
                bm.map[accessIndex] = bm.map[accessIndex]  ^ bitmap.BM_ALLBITS; //Todo check
            }
        }

        // note: the following "if" is needed because x86 treats a<<b as
        //a<<(b&31). I spent hours looking for this bug.
        if (xlo > 0) {
            int accessIndex = (bm.dy * y) + (xhi / bitmap.PIXELINWORD);
            bm.map[accessIndex] = bm.map[accessIndex]  ^ (bitmap.BM_ALLBITS << (bitmap.PIXELINWORD - xlo)); //Todo check
        }
    }

    /* a path is represented as an array of points, which are thought to
    lie on the corners of pixels (not on their centers). The path point
    (x,y) is the lower left corner of the pixel (x,y). Paths are
    represented by the len/pt components of a path_t object (which
    also stores other information about the path) */

    /* xor the given pixmap with the interior of the given path. Note: the
    path must be within the dimensions of the pixmap. */

    public static void xor_path(bitmap bm, path p) {
        int xa, x, y, k, y1;

        if (p.priv.len <= 0) {  /* a path of length 0 is silly, but legal */
            return; //TODO Find way to run this line for Code Coverage
        }

        y1 = p.priv.pt[p.priv.len-1].y;
        xa = p.priv.pt[0].x & - bitmap.PIXELINWORD;

        for (k=0; k<p.priv.len; k++) {
            x = p.priv.pt[k].x;
            y = p.priv.pt[k].y;

            if (y != y1) {
                /* efficiently invert the rectangle [x,xa] x [y,y1] */
                xor_to_ref(bm, x, auxiliary.min(y,y1), xa);
                y1 = y;
            }
        }
    }

    /* Find the bounding box of a given path. Path is assumed to be of
    non-zero length. */

    static void setbbox_path(bbox bbox, path p) {
        int x, y;
        int k;

        bbox.y0 = Integer.MAX_VALUE;
        bbox.y1 = 0;
        bbox.x0 = Integer.MAX_VALUE;
        bbox.x1 = 0;

        for (k=0; k<p.priv.len; k++) {
            x = p.priv.pt[k].x;
            y = p.priv.pt[k].y;

            if (x < bbox.x0) {
                bbox.x0 = x;
            }
            if (x > bbox.x1) {
                bbox.x1 = x;
            }
            if (y < bbox.y0) {
                bbox.y0 = y;
            }
            if (y > bbox.y1) {
                bbox.y1 = y;
            }
        }
    }

    /* compute a path in the given pixmap, separating black from white.
    Start path at the point (x0,x1), which must be an upper left corner
    of the path. Also compute the area enclosed by the path. Return a
    new path_t object, or NULL on error (note that a legitimate path
    cannot have length 0). Sign is required for correct interpretation
    of turnpolicies. */

    public static path findpath(bitmap bm, int x0, int y0, int sign, int turnpolicy) {
        int x, y, dirx, diry, len, size, area;
        int tmp;
        boolean c,d;
        Point[] pt = new Point[1];
        Point[] pt1 = new Point[1];
        path p = null;

        x = x0;
        y = y0;
        dirx = 0;
        diry = -1;

        len = size = 0;
        area = 0;

        while (true) {
            /* add point to path */
            if (len>=size) {
                size += 100;
                size = (int)(1.3 * size);
                Point[] newSizedPointArray = new Point[size];
                System.arraycopy(pt,0,newSizedPointArray,0,pt.length);
                pt1 = newSizedPointArray;
                pt = pt1;
            }
            pt[len] = new Point(x,y);
            len++;

            /* move to next point */
            x += dirx;
            y += diry;
            area += x*diry;

            /* path complete? */
            if (x==x0 && y==y0) {
                break;
            }

            /* determine next direction */
            c = bm.BM_GET(x + (dirx+diry-1)/2, y + (diry-dirx-1)/2);
            d = bm.BM_GET(x + (dirx-diry-1)/2, y + (diry+dirx-1)/2);

            if (c && !d) {               /* ambiguous turn */
                if (turnpolicy == potraceLib.POTRACE_TURNPOLICY_RIGHT
                        || (turnpolicy == potraceLib.POTRACE_TURNPOLICY_BLACK && sign == '+')
                        || (turnpolicy == potraceLib.POTRACE_TURNPOLICY_WHITE && sign == '-')
                        || (turnpolicy == potraceLib.POTRACE_TURNPOLICY_RANDOM && detrand(x,y))
                        || (turnpolicy == potraceLib.POTRACE_TURNPOLICY_MAJORITY && majority(bm, x, y))
                        || (turnpolicy == potraceLib.POTRACE_TURNPOLICY_MINORITY && !majority(bm, x, y))) {
                    tmp = dirx;              /* right turn */
                    dirx = diry;
                    diry = -tmp;
                } else {
                    tmp = dirx;              /* left turn */
                    dirx = -diry;
                    diry = tmp;
                }
            } else if (c) {              /* right turn */
                tmp = dirx;
                dirx = diry;
                diry = -tmp;
            } else if (!d) {             /* left turn */
                tmp = dirx;
                dirx = -diry;
                diry = tmp;
            }
        } /* while this path */

        /* allocate new path object */
        p = new path();

        p.priv.pt = pt;
        p.priv.len = len;
        p.area = area;
        p.sign = sign;

        return p;
    }

    /* Give a tree structure to the given path potrace.list, based on "insideness"
    testing. I.e., path A is considered "below" path B if it is inside
    path B. The input pathlist is assumed to be ordered so that "outer"
    paths occur before "inner" paths. The tree structure is stored in
    the "childlist" and "sibling" components of the path_t
    structure. The linked potrace.list structure is also changed so that
    negative path components are listed immediately after their
    positive parent.  Note: some backends may ignore the tree
    structure, others may use it e.g. to group path components. We
    assume that in the input, point 0 of each path is an "upper left"
    corner of the path, as returned by bm_to_pathlist. This makes it
    easy to find an "interior" point. The bm argument should be a
    bitmap of the correct size (large enough to hold all the paths),
    and will be used as scratch space. Return 0 on success or -1 on
    error with errno set. */

    static void pathlist_to_tree(path plist, bitmap bm) {
        path p = new path();
        path p1;
        path heap = new path();
        path heap1;
        path cur = new path();
        path head = new path();
        path plist_hook;                // for fast appending to linked potrace.list
        path hook_in, hook_out;         // for fast appending to linked potrace.list
        bbox bbox = new bbox();

        bm = bitmap.bm_clear(bm, 0);

        // save original "next" pointers

        for (p = plist; p != null; p = p.next) {
            p.sibling = p.next;
            p.childlist = null;
        }
        p = null;

        heap = plist;

        /* the heap holds a potrace.list of lists of paths. Use "childlist" field
        for outer potrace.list, "next" field for inner potrace.list. Each of the sublists
        is to be turned into a tree. This code is messy, but it is
        actually fast. Each path is rendered exactly once. We use the
        heap to get a tail recursive algorithm: the heap holds a potrace.list of
        pathlists which still need to be transformed. */

        while (heap != null) {
            // unlink first sublist
            cur = heap;
            heap = heap.childlist;
            cur.childlist = null;

            // unlink first path
            head = cur;
            cur = cur.next;
            head.next = null;

            // render path
            xor_path(bm, head);
            setbbox_path(bbox, head);

            /* now do insideness test for each element of cur; append it to
            head->childlist if it's inside head, else append it to
            head->next. */

            hook_in = head.childlist;
            hook_out = head.next;

            for (p=cur; (p != null); p=cur) {
                cur=p.next;
                p.next=null;

                if (p.priv.pt[0].y <= bbox.y0) {
                //Todo find out what that if condition is for
                    head.next = list.unefficient_list_insert_beforehook(p,head.next);
	                // append the remainder of the potrace.list to hook_out
                    head.next = list.putElementWhereNextIsNull(cur,head.next);

                    break;

                }
                if (bm.BM_GET(p.priv.pt[0].x, p.priv.pt[0].y-1)) {
                    head.childlist = list.unefficient_list_insert_beforehook(p,head.childlist);

                } else {

                    head.next = list.unefficient_list_insert_beforehook(p,head.next);

                }
            }

            // clear bm
            clear_bm_with_bbox(bm, bbox);

            // now schedule head->childlist and head->next for further
           // processing
            if (head.next != null) { //Ole: es gab pfade die außerhalb des aller ersten pfades waren
                head.next.childlist = heap;
                heap = head.next;

            }
            if (head.childlist != null) {
                head.childlist.childlist = heap;
                heap = head.childlist;
            }

        }

        // copy sibling structure from "next" to "sibling" component
        p = plist;
        while (p != null) {
            p1 = p.sibling;
            p.sibling = p.next;
            p = p1;
        }

        // reconstruct a new linked potrace.list ("next") structure from tree
        // ("childlist", "sibling") structure. This code is slightly messy,
        // because we use a heap to make it tail recursive: the heap
        // contains a potrace.list of childlists which still need to be
        // processed.
        heap = plist;
        if (heap != null) {
            heap.next = null;  // heap is a linked potrace.list of childlists
        }
        plist = null;
        while (heap != null) {
            heap1 = heap.next;
            for (p=heap; p != null; p=p.sibling) {
                // p is a positive path
                // append to linked potrace.list
                plist = list.unefficient_list_insert_beforehook(p, plist);

                // go through its children
                for (p1=p.childlist; p1 != null; p1=p1.sibling) {
	                // append to linked potrace.list
                    plist = list.unefficient_list_insert_beforehook(p1, plist);
	                // append its childlist to heap, if non-empty

                    if (p1.childlist != null) {
                        heap1 = list.unefficient_list_insert_beforehook(p1.childlist,heap1);
                    }
                }
            }
            heap = heap1;
        }
    }

    /* find the next set pixel in a row <= y. Pixels are searched first
    left-to-right, then top-down. In other words, (x,y)<(x',y') if y>y'
    or y=y' and x<x'. If found, return 0 and store pixel in
    (*xp,*yp). Else return 1. Note that this function assumes that
    excess bytes have been cleared with bm_clearexcess. */

    public static boolean findnext(bitmap bm, Point XY) { //TODO check it its working correct
        int x0;

        x0 = (XY.x) & ~(bitmap.PIXELINWORD-1); //TODO versteh ich nicht! Meiner meinung nach kommt da immer null raus, warum dann erst errechnen lassen?

        for (int y=XY.y; y>=0; y--) {
            for (int x=x0; x<bm.w && x>=0; x+=bm.PIXELINWORD) {

                if (bm.bm_index(x, y) != 0) {
                    while (!bm.BM_GET(x, y)) {
                        x++;
                    }
	                /* found */
	                XY.x = x;
                    XY.y = y;
                    return true;
                }
            }
            x0 = 0;
        }
        /* not found */
        return false;
    }

    //Entwickler:
    /* Decompose the given bitmap into paths. Returns a linked list of
    path_t objects with the fields len, pt, area, sign filled
    in. Returns 0 on success with plistp set, or -1 on error with errno
    set. */

    static path bm_to_pathlist(bitmap bm, param param) {
        int x;
        int y;
        path p;
        path plist = null;
        //potrace.path plist_hook = null;  // used to speed up appending to linked potrace.list
        bitmap bm1 = bm.bm_dup();
        int sign;

        bm1 = bm.bm_dup();
        if (bm1 == null)
            return null;

        //be sure the byte padding on the right is set to 0, as the fast
        //pixel search below relies on it
        bm_clearexcess(bm1);

        // iterate through components
        x = 0;
        y = bm1.h - 1;
        Point xy = new Point(x,y);
        while ((findnext(bm1,xy))) {
            // calculate the sign by looking at the original bitmap, bm1 wird immer wieder invertiert nachdem ein pfad entfernt wurde.
            // mit dem nachgucken nach dem sign in der original bitmap bekommt einen eindruck darüber ob es ein wirklicher pfad ist oder nur der ausschnitt von einen pfad, also das innnere
            sign = bm.BM_GET(xy.x, xy.y) ? '+' : '-';

            // calculate the path
            p = findpath(bm1, xy.x, xy.y+1, sign, param.turnpolicy);
            if (p==null)
                return null;

            // update buffered image
            xor_path(bm1, p);

            // if it' a turd, eliminate it, else append it to the potrace.list
            if (p.area > param.turdsize) {

                //TODO Originally it was made with a plist_hook, with which it was easier and faster to append a element at the end of the linkedlist
                plist = list.unefficient_list_insert_beforehook(p,plist);
            }
        }

        pathlist_to_tree(plist, bm1);
        return plist;
    }
}

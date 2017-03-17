import java.awt.*;
import java.util.LinkedList;

/**
 * Created by andreydelany on 05/03/2017.
 */
public class decompose {

    static Point findnext(potrace_bitmap bm, Point XY) { //TODO check it its working correct
        int x0;

        x0 = (XY.x) & ~(potrace_bitmap.PIXELINWORD-1); //TODO versteh ich nicht! Meiner meinung nach kommt da immer null raus, warum dann erst errechnen lassen?

        for (int y=XY.y; y>=0; y--) {
            for (int x=x0; x<bm.w && x>=0; x+=bm.PIXELINWORD) {

                if (potrace_bitmap.bm_index(bm, x, y) != 0) {
                    while (!potrace_bitmap.BM_GET(bm, x, y)) {
                        x++;
                    }
	                /* found */
                    return new Point(x,y);
                }
            }
            x0 = 0;
        }
        /* not found */
        return null;
    }

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

    static boolean majority(potrace_bitmap bm, int x, int y) {
        int i, a, ct;

        for (i=2; i<5; i++) { /* check at "radius" i */
            ct = 0;
            for (a=-i+1; a<=i-1; a++) {
                ct += potrace_bitmap.BM_GET(bm,x+a, y+i-1) ? 1 : -1;
                ct += potrace_bitmap.BM_GET(bm,x+i-1, y+a-1) ? 1 : -1;
                ct += potrace_bitmap.BM_GET(bm,x+a-1, y-i) ? 1 : -1;
                ct += potrace_bitmap.BM_GET(bm,x-i, y+a) ? 1 : -1;
            }
            if (ct>0) {
                return true; //TODO is 1 true and 0 false? really?
            } else if (ct<0) {
                return false;
            }
        }
        return false;
    }

    static potrace_path findpath(potrace_bitmap bm, int x0, int y0, int sign, int turnpolicy) {
        int x, y, dirx, diry, len, size, area;
        int tmp;
        boolean c,d;
        Point[] pt = new Point[1];
        Point[] pt1 = new Point[1];
        potrace_path p = null;

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
                pt1 = new Point[size];
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
            c = potrace_bitmap.BM_GET(bm,x + (dirx+diry-1)/2, y + (diry-dirx-1)/2);
            d = potrace_bitmap.BM_GET(bm,x + (dirx-diry-1)/2, y + (diry+dirx-1)/2);

            if (c && !d) {               /* ambiguous turn */
                if (turnpolicy == potrace_param.POTRACE_TURNPOLICY_RIGHT
                        || (turnpolicy == potrace_param.POTRACE_TURNPOLICY_BLACK && sign == '+')
                        || (turnpolicy == potrace_param.POTRACE_TURNPOLICY_WHITE && sign == '-')
                        || (turnpolicy == potrace_param.POTRACE_TURNPOLICY_RANDOM && detrand(x,y))
                        || (turnpolicy == potrace_param.POTRACE_TURNPOLICY_MAJORITY && majority(bm, x, y))
                        || (turnpolicy == potrace_param.POTRACE_TURNPOLICY_MINORITY && !majority(bm, x, y))) {
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
        p = new potrace_path();

        p.priv.pt = pt;
        p.priv.len = len;
        p.area = area;
        p.sign = sign;

        return p;
    }

    static int min(int a, int b) {
        return ((a) < (b) ? (a) : (b));
    }

    static potrace_bitmap xor_to_ref(potrace_bitmap bm, int x, int y, int xa) {
        int xhi = x & - potrace_bitmap.PIXELINWORD;
        int xlo = x & (potrace_bitmap.PIXELINWORD-1);  /* = x % BM_WORDBITS */
        int i;

        if (xhi<xa) {
            for (i = xhi; i < xa; i+=potrace_bitmap.PIXELINWORD) {
                bm = potrace_bitmap.bm_setPotraceWord_WithX(bm,i,y,potrace_bitmap.bm_index(bm, i, y) ^ potrace_bitmap.BM_ALLBITS); //Todo check
            }
        } else {
            for (i = xa; i < xhi; i+=potrace_bitmap.PIXELINWORD) {
                bm = potrace_bitmap.bm_setPotraceWord_WithX(bm,i,y,potrace_bitmap.bm_index(bm, i, y) ^ potrace_bitmap.BM_ALLBITS); //Todo check
            }
        }

        // note: the following "if" is needed because x86 treats a<<b as
        //a<<(b&31). I spent hours looking for this bug.
        if (xlo > 0) {
            bm = potrace_bitmap.bm_setPotraceWord_WithX(bm,xhi,y,potrace_bitmap.bm_index(bm, xhi, y) ^ (potrace_bitmap.BM_ALLBITS << (potrace_bitmap.PIXELINWORD - xlo)));
        }

        return bm;
    }

    static potrace_bitmap xor_path(potrace_bitmap bm, potrace_path p) {
        int xa, x, y, k, y1;

        if (p.priv.len <= 0) {  /* a path of length 0 is silly, but legal */
            return null;
        }

        y1 = p.priv.pt[p.priv.len-1].y;
        xa = p.priv.pt[0].x & - potrace_bitmap.PIXELINWORD; //TODO what da fuck //xa = p.priv.pt[0].x & - BM_WORDBITS;

        for (k=0; k<p.priv.len; k++) {
            x = p.priv.pt[k].x;
            y = p.priv.pt[k].y;

            if (y != y1) {
                /* efficiently invert the rectangle [x,xa] x [y,y1] */
                bm = xor_to_ref(bm, x, min(y,y1), xa);
                y1 = y;
            }
        }

        return bm;
    }

    /* Find the bounding box of a given path. Path is assumed to be of
   non-zero length. */
    static bbox setbbox_path(bbox bbox, potrace_path p) {
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
        return bbox;
    }

    static potrace_bitmap clear_bm_with_bbox(potrace_bitmap bm, bbox bbox) {
        int imin = (bbox.x0 / potrace_bitmap.PIXELINWORD);
        int imax = ((bbox.x1 + potrace_bitmap.PIXELINWORD-1) / potrace_bitmap.PIXELINWORD);
        int i, y;

        for (y=bbox.y0; y<bbox.y1; y++) {
            for (i=imin; i<imax; i++) {
                bm = potrace_bitmap.bm_setPotraceWord_WithI(bm, i, y, 0);
            }
        }

        return bm;
    }

    /* Give a tree structure to the given path list, based on "insideness"
    testing. I.e., path A is considered "below" path B if it is inside
    path B. The input pathlist is assumed to be ordered so that "outer"
    paths occur before "inner" paths. The tree structure is stored in
    the "childlist" and "sibling" components of the path_t
    structure. The linked list structure is also changed so that
    negative path components are listed immediately after their
    positive parent.  Note: some backends may ignore the tree
    structure, others may use it e.g. to group path components. We
    assume that in the input, point 0 of each path is an "upper left"
    corner of the path, as returned by bm_to_pathlist. This makes it
    easy to find an "interior" point. The bm argument should be a
    bitmap of the correct size (large enough to hold all the paths),
    and will be used as scratch space. Return 0 on success or -1 on
    error with errno set. */

    static potrace_path pathlist_to_tree(potrace_path plist, potrace_bitmap bm) {
        potrace_path p = new potrace_path();
        potrace_path p1;
        potrace_path heap = new potrace_path();
        potrace_path heap1;
        potrace_path cur = new potrace_path();
        potrace_path head = new potrace_path();
        potrace_path plist_hook;          // for fast appending to linked list
        potrace_path hook_in, hook_out; // for fast appending to linked list
        bbox bbox = new bbox();

        bm = potrace_bitmap.bm_clear(bm, 0);
        bm = potrace_bitmap.bm_clearexcess(bm);


        // save original "next" pointers
        for (p = plist; p != null; p = p.next) {
            p.sibling = p.next;
            p.childlist = null;
        }

        heap = plist;

        // the heap holds a list of lists of paths. Use "childlist" field
        //for outer list, "next" field for inner list. Each of the sublists
        //is to be turned into a tree. This code is messy, but it is
        //actually fast. Each path is rendered exactly once. We use the
        //heap to get a tail recursive algorithm: the heap holds a list of
        //pathlists which still need to be transformed.

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
            bm = xor_path(bm, head);
            bbox = setbbox_path(bbox, head);

            // now do insideness test for each element of cur; append it to
            //head->childlist if it's inside head, else append it to
            //head->next.
            hook_in = head.childlist;
            hook_out = head.next;

            for (p=cur; p != null; p=cur) {
                cur=p.next;
                p.next=null;

                if (p.priv.pt[0].y <= bbox.y0) {
                    head.next = list.unefficient_list_insert_beforehook(p,head.next);
	                // append the remainder of the list to hook_out
                    //TODO not sure what i should do here
	                //hook_out = cur;
                    head.next = list.unefficient_list_insert_beforehook(cur,head.next);
                    break;
                }
                if (potrace_bitmap.BM_GET(bm, p.priv.pt[0].x, p.priv.pt[0].y-1)) {
                    head.childlist = list.unefficient_list_insert_beforehook(p,head.childlist);
                } else {
                    //hook_out = list.list_insert_beforehook(p, hook_out);
                    head.next = list.unefficient_list_insert_beforehook(p,head.next);
                }
            }

            // clear bm
            bm = clear_bm_with_bbox(bm, bbox);

            // now schedule head->childlist and head->next for further
           // processing
            if (head.next != null) {
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

        // reconstruct a new linked list ("next") structure from tree
        // ("childlist", "sibling") structure. This code is slightly messy,
        // because we use a heap to make it tail recursive: the heap
        // contains a list of childlists which still need to be
        // processed.
        heap = plist;
        if (heap != null) {
            heap.next = null;  // heap is a linked list of childlists
        }
        plist = null;
        while (heap != null) {
            heap1 = heap.next;
            for (p=heap; p != null; p=p.sibling) {
                // p is a positive path
                // append to linked list
                plist = list.unefficient_list_insert_beforehook(p, plist);

                // go through its children
                for (p1=p.childlist; p1 != null; p1=p1.sibling) {
	                // append to linked list
                    plist = list.unefficient_list_insert_beforehook(p1, plist);
	                // append its childlist to heap, if non-empty
                    if (p1.childlist != null) {
                        //TODO produce the error <- ??? What does that mean ???

                        potrace_path current = heap1;
                        while (current != null)
                            current = current.next;
                        p1.childlist .next = current;
                        heap1 = p1.childlist;
                    }
                }
            }
            heap = heap1;
        }
        return plist;
    }

    static potrace_path bm_to_pathlist(potrace_bitmap bm, potrace_param param) {
        int x;
        int y;
        potrace_path p;
        potrace_path plist = null;  // linked list of path objects
        //potrace_path plist_hook = null;  // used to speed up appending to linked list
        potrace_bitmap bm1 = potrace_bitmap.bm_dup(bm);
        int sign;


        //be sure the byte padding on the right is set to 0, as the fast
        //pixel search below relies on it
        bm1 = potrace_bitmap.bm_clearexcess(bm1);


        // iterate through components
        x = 0;
        y = bm1.h - 1;
        Point xy = new Point(x,y);
        while ((xy = findnext(bm1,xy)) != null) {
            // calculate the sign by looking at the original bitmap, bm1 wird immer wieder invertiert nachdem ein pfad entfernt wurde.
            // mit dem nachgucken nach dem sign in der original bitmap bekommt einen eindruck darüber ob es ein wirklicher pfad ist oder nur der ausschnitt von einen pfad, also das innnere
            sign = potrace_bitmap.BM_GET(bm, xy.x, xy.y) ? '+' : '-';

            // calculate the path
            p = findpath(bm1, xy.x, xy.y+1, sign, param.turnpolicy);
            //TODO here we catch a maybe error if p = null

            // update buffered image
            bm1 = xor_path(bm1, p);

            // if it's a turd, eliminate it, else append it to the list
            if (p.area > param.turdsize) {

                //TODO Originally it was made with a plist_hook, with which it was easier and faster to append a element at the end of the linkedlist
                plist = list.unefficient_list_insert_beforehook(p,plist);
            }
            /* TODO massive problem with the callback functions of progress
            if (bm1.h > 0) { // to be sure
                progress_update(1-y/(double)bm1->h, progress);
            }
            */
        }

        plist = pathlist_to_tree(plist, bm1);
        //bm_free(bm1);                     //TODO simply commented because of errer
        //plistp = plist;

        //progress_update(1.0, progress);   //TODO massive problem with the callback functions of progress

        return plist;
    }
}

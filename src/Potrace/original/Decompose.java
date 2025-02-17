package Potrace.original;

import Potrace.General.*;
import Potrace.General.List;

import java.awt.*;

public class Decompose implements DecompositionInterface{

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
    /* Auxiliary Bitmap manipulations */

    /* set the excess padding to 0 */
    static void bm_clearexcess(Bitmap bm) {
        long mask;
        int y;

        if (bm.w % Bitmap.PIXELINWORD != 0) {
            mask = Bitmap.BM_ALLBITS << (Bitmap.PIXELINWORD - (bm.w % Bitmap.PIXELINWORD));
            for (y=0; y<bm.h; y++) {
                bm.map[y * bm.dy + bm.dy - 1] = BitmapManipulator.bm_index(bm,bm.w, y) & mask;
            }
        }
    }

    /* clear the bm, assuming the bounding box is set correctly (faster
    than clearing the whole Bitmap) */

    static void clear_bm_with_bbox(Bitmap bm, BBox bbox) {
        int imin = (bbox.x0 / Bitmap.PIXELINWORD);
        int imax = ((bbox.x1 + Bitmap.PIXELINWORD-1) / Bitmap.PIXELINWORD);
        int i, y;

        for (y=bbox.y0; y<bbox.y1; y++) {
            for (i=imin; i<imax; i++) {
                bm.map[y * bm.dy + i] = 0;
            }
        }
    }

    /* ---------------------------------------------------------------------- */
    /* Auxiliary functions */

    /* return the "majority" value of Bitmap bm at intersection (x,y). We
    assume that the Bitmap is balanced at "radius" 1.  */

    static boolean majority(Bitmap bm, int x, int y) {
        int i, a, ct;

        for (i=2; i<5; i++) { /* check at "radius" i */
            ct = 0;
            for (a=-i+1; a<=i-1; a++) {
                ct += BitmapManipulator.BM_GET(bm,x+a, y+i-1) ? 1 : -1;
                ct += BitmapManipulator.BM_GET(bm,x+i-1, y+a-1) ? 1 : -1;
                ct += BitmapManipulator.BM_GET(bm,x+a-1, y-i) ? 1 : -1;
                ct += BitmapManipulator.BM_GET(bm,x-i, y+a) ? 1 : -1;
            }
            if (ct>0) {
                return true;
            } else if (ct<0) {
                return false;
            }
        }
        return false;
    }

    /* ---------------------------------------------------------------------- */
    /* Decompose image into paths */

    /* efficiently invert bits [x,infty) and [xa,infty) in line y. Here xa
    must be a multiple of BM_WORDBITS. */

    static void xor_to_ref(Bitmap bm, int x, int y, int xa) {
        int xhi = x & - Bitmap.PIXELINWORD;
        int xlo = x & (Bitmap.PIXELINWORD-1);  /* = x % BM_WORDBITS */
        int i;

        if (xhi<xa) {
            for (i = xhi; i < xa; i+= Bitmap.PIXELINWORD) {
                int accessIndex = (bm.dy * y) + (i / Bitmap.PIXELINWORD);
                bm.map[accessIndex] = bm.map[accessIndex]  ^ Bitmap.BM_ALLBITS;
            }
        } else {
            for (i = xa; i < xhi; i+= Bitmap.PIXELINWORD) {
                int accessIndex = (bm.dy * y) + (i / Bitmap.PIXELINWORD);
                bm.map[accessIndex] = bm.map[accessIndex]  ^ Bitmap.BM_ALLBITS;
            }
        }

        // note: the following "if" is needed because x86 treats a<<b as
        //a<<(b&31). I spent hours looking for this bug.
        if (xlo > 0) {
            int accessIndex = (bm.dy * y) + (xhi / Bitmap.PIXELINWORD);
            bm.map[accessIndex] = bm.map[accessIndex]  ^ (Bitmap.BM_ALLBITS << (Bitmap.PIXELINWORD - xlo));
        }
    }

    /* a Path is represented as an array of points, which are thought to
    lie on the corners of pixels (not on their centers). The Path point
    (x,y) is the lower left corner of the pixel (x,y). Paths are
    represented by the len/pt components of a path_t object (which
    also stores other information about the Path) */

    /* xor the given pixmap with the interior of the given Path. Note: the
    Path must be within the dimensions of the pixmap. */

    public static void xor_path(Bitmap bm, Path p) {
        int xa, x, y, k, y1;

        if (p.priv.len <= 0) {  /* a Path of length 0 is silly, but legal */
            return;
        }

        y1 = p.priv.pt[p.priv.len-1].y;
        xa = p.priv.pt[0].x & - Bitmap.PIXELINWORD;

        for (k=0; k<p.priv.len; k++) {
            x = p.priv.pt[k].x;
            y = p.priv.pt[k].y;

            if (y != y1) {
                /* efficiently invert the rectangle [x,xa] x [y,y1] */
                xor_to_ref(bm, x, Auxiliary.min(y,y1), xa);
                y1 = y;
            }
        }
    }

    /* Find the bounding box of a given Path. Path is assumed to be of
    non-zero length. */

    public static void setbbox_path(BBox bbox, Path p) {
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

    /* compute a Path in the given pixmap, separating black from white.
    Start Path at the point (x0,x1), which must be an upper left corner
    of the Path. Also compute the area enclosed by the Path. Return a
    new path_t object, or NULL on error (note that a legitimate Path
    cannot have length 0). Sign is required for correct interpretation
    of turnpolicies. */

    public static Path findpath(Bitmap bm, int x0, int y0, int sign, int turnpolicy) {
        int x, y, dirx, diry, len, size, area;
        int tmp;
        boolean c,d;
        Point[] pt = new Point[1];
        Point[] pt1 = new Point[1];
        Path p = null;

        x = x0;
        y = y0;
        dirx = 0;
        diry = -1;

        len = size = 0;
        area = 0;

        while (true) {
            /* add point to Path */
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

            /* Path complete? */
            if (x==x0 && y==y0) {
                break;
            }

            /* determine next direction */
            c = BitmapManipulator.BM_GET(bm,x + (dirx+diry-1)/2, y + (diry-dirx-1)/2);
            d = BitmapManipulator.BM_GET(bm,x + (dirx-diry-1)/2, y + (diry+dirx-1)/2);

            if (c && !d) {               /* ambiguous turn */
                if (turnpolicy == PotraceLibrary.POTRACE_TURNPOLICY_RIGHT
                        || (turnpolicy == PotraceLibrary.POTRACE_TURNPOLICY_BLACK && sign == '+')
                        || (turnpolicy == PotraceLibrary.POTRACE_TURNPOLICY_WHITE && sign == '-')
                        || (turnpolicy == PotraceLibrary.POTRACE_TURNPOLICY_RANDOM && detrand(x,y))
                        || (turnpolicy == PotraceLibrary.POTRACE_TURNPOLICY_MAJORITY && majority(bm, x, y))
                        || (turnpolicy == PotraceLibrary.POTRACE_TURNPOLICY_MINORITY && !majority(bm, x, y))) {
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
        } /* while this Path */

        /* allocate new Path object */
        p = new Path();

        p.priv.pt = pt;
        p.priv.len = len;
        p.area = area;
        p.sign = sign;

        return p;
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

    public static void pathlist_to_tree(Path plist, Bitmap bm) {
        Path p = new Path();
        Path p1;
        Path heap = new Path();
        Path heap1;
        Path cur = new Path();
        Path head = new Path();
        Path plist_hook;                // for fast appending to linked Potrace.General.List
        Path hook_in, hook_out;         // for fast appending to linked Potrace.General.List
        BBox bbox = new BBox();

        bm = BitmapManipulator.bm_clear(bm, 0);

        // save original "next" pointers

        for (p = plist; p != null; p = p.next) {
            p.sibling = p.next;
            p.childlist = null;
        }
        p = null;

        heap = plist;

        /* the heap holds a list of lists of paths. Use "childlist" field
        for outer list, "next" field for inner list. Each of the sublists
        is to be turned into a tree. This code is messy, but it is
        actually fast. Each path is rendered exactly once. We use the
        heap to get a tail recursive algorithm: the heap holds a list of
        pathlists which still need to be transformed. */

        while (heap != null) {
            // unlink first sublist
            cur = heap;
            heap = heap.childlist;
            cur.childlist = null;

            // unlink first Path
            head = cur;
            cur = cur.next;
            head.next = null;

            // render Path
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
                    head.next = List.elementInsertAtTheLastNextOfList(p,head.next);
                    head.next = List.listInsertAtTheLastNextOfList(cur,head.next);

                    break;

                }
                if (BitmapManipulator.BM_GET(bm,p.priv.pt[0].x, p.priv.pt[0].y-1)) {
                    head.childlist = List.elementInsertAtTheLastNextOfList(p,head.childlist);

                } else {

                    head.next = List.elementInsertAtTheLastNextOfList(p,head.next);

                }
            }

            // clear bm
            clear_bm_with_bbox(bm, bbox);

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

        /* reconstruct a new linked list ("next") structure from tree
        ("childlist", "sibling") structure. This code is slightly messy,
        because we use a heap to make it tail recursive: the heap
        contains a list of childlists which still need to be
        processed. */
        heap = plist;
        if (heap != null) {
            heap.next = null;  /* heap is a linked list of childlists */
        }
        plist = null;
        while (heap != null) {
            heap1 = heap.next;
            for (p=heap; p != null; p=p.sibling) {
                /* p is a positive path */
                /* append to linked list */
                plist = List.elementInsertAtTheLastNextOfList(p, plist);

                /* go through its children */
                for (p1=p.childlist; p1 != null; p1=p1.sibling) {
	                /* append to linked list */
                    plist = List.elementInsertAtTheLastNextOfList(p1, plist);
	                /* append its childlist to heap, if non-empty */
                    if (p1.childlist != null) {
                        heap1 = List.elementInsertAtTheLastNextOfList(p1.childlist,heap1);
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

    public static boolean findnext(Bitmap bm, Point XY) {
        int x0;

        x0 = (XY.x) & ~(Bitmap.PIXELINWORD-1);

        for (int y=XY.y; y>=0; y--) {
            for (int x=x0; x<bm.w && x>=0; x+=bm.PIXELINWORD) {

                if (BitmapManipulator.bm_index(bm,x, y) != 0) {
                    while (!BitmapManipulator.BM_GET(bm,x, y)) {
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

    /* Decompose the given bitmap into paths. Returns a linked list of
   path_t objects with the fields len, pt, area, sign filled
   in. Returns 0 on success with plistp set, or -1 on error with errno
   set. */

    public static Path bm_to_pathlist(Bitmap bm, Param param) {
        int x;
        int y;
        Path p;
        Path plist = null;
        Bitmap bm1;
        int sign;

        bm1 = bm.bm_dup();

        //be sure the byte padding on the right is set to 0, as the fast
        //pixel search below relies on it
        bm_clearexcess(bm1);

        // iterate through components
        x = 0;
        y = bm1.h - 1;
        Point xy = new Point(x,y);
        while ((findnext(bm1,xy))) {
            /* calculate the sign by looking at the original */
            sign = BitmapManipulator.BM_GET(bm,xy.x, xy.y) ? '+' : '-';

            // calculate the Path
            p = findpath(bm1, xy.x, xy.y+1, sign, param.turnpolicy);

            // update buffered image
            xor_path(bm1, p);

            /* if it's a turd, eliminate it, else append it to the list */
            if (p.area > param.turdsize) {
                plist = List.elementInsertAtTheLastNextOfList(p,plist);
            }
        }

        pathlist_to_tree(plist, bm1);
        return plist;
    }

    @Override
    public Path getPathList(Bitmap generalBitmap, Param param) {
        return bm_to_pathlist(generalBitmap,param);
    }
}
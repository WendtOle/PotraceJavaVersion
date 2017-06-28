package refactored.potrace;

/**
 * Created by andreydelany on 28.06.17.
 */
public class PathListToTree {

    /* Give a tree structure to the given Path original.potrace.List, based on "insideness"
    testing. I.e., Path A is considered "below" Path B if it is inside
    Path B. The input pathlist is assumed to be ordered so that "outer"
    paths occur before "inner" paths. The tree structure is stored in
    the "childlist" and "sibling" components of the path_t
    structure. The linked original.potrace.List structure is also changed so that
    negative Path components are listed immediately after their
    positive parent.  Note: some backends may ignore the tree
    structure, others may use it e.g. to group Path components. We
    assume that in the input, point 0 of each Path is an "upper left"
    corner of the Path, as returned by bm_to_pathlist. This makes it
    easy to find an "interior" point. The bm argument should be a
    Bitmap of the correct size (large enough to hold all the paths),
    and will be used as scratch space. Return 0 on success or -1 on
    error with errno set. */

    static void pathlist_to_tree(Path plist, Bitmap bm) {
        Path p = new Path();
        Path p1;
        Path heap = new Path();
        Path heap1;
        Path cur = new Path();
        Path head = new Path();
        Path plist_hook;                // for fast appending to linked original.potrace.List
        Path hook_in, hook_out;         // for fast appending to linked original.potrace.List
        BBox bbox = new BBox();

        bm.setWholeBitmapToSpecificValue(0);

        // save original "next" pointers

        for (p = plist; p != null; p = p.next) {
            p.sibling = p.next;
            p.childlist = null;
        }
        p = null;

        heap = plist;

        /* the heap holds a original.potrace.List of lists of paths. Use "childlist" field
        for outer original.potrace.List, "next" field for inner original.potrace.List. Each of the sublists
        is to be turned into a tree. This code is messy, but it is
        actually fast. Each Path is rendered exactly once. We use the
        heap to get a tail recursive algorithm: the heap holds a original.potrace.List of
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
            bm.removePathFromBitmap(head);
            bbox.setToBoundingBoxOfPath(head);

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
                    head.next = Path.insertElementAtTheEndOfList(p,head.next);
                    // append the remainder of the original.potrace.List to hook_out
                    head.next = Path.insertListAtTheEndOfList(cur,head.next);
                    //head.next = Path.insertListAtTheEndOfList(cur,head.next);

                    break;

                }
                if (bm.getPixelValue(p.priv.pt[0].x, p.priv.pt[0].y-1)) {
                    head.childlist = Path.insertElementAtTheEndOfList(p,head.childlist);

                } else {

                    head.next = Path.insertElementAtTheEndOfList(p,head.next);

                }
            }

            // clear bm
            bm.clearBitmapWithBBox(bbox);

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

        // reconstruct a new linked original.potrace.List ("next") structure from tree
        // ("childlist", "sibling") structure. This code is slightly messy,
        // because we use a heap to make it tail recursive: the heap
        // contains a original.potrace.List of childlists which still need to be
        // processed.
        heap = plist;
        if (heap != null) {
            heap.next = null;  // heap is a linked original.potrace.List of childlists
        }
        plist = null;
        while (heap != null) {
            heap1 = heap.next;
            for (p=heap; p != null; p=p.sibling) {
                // p is a positive Path
                // append to linked original.potrace.List
                plist = Path.insertElementAtTheEndOfList(p, plist);

                // go through its children
                for (p1=p.childlist; p1 != null; p1=p1.sibling) {
                    // append to linked original.potrace.List
                    plist = Path.insertElementAtTheEndOfList(p1, plist);
                    // append its childlist to heap, if non-empty

                    if (p1.childlist != null) {
                        heap1 = Path.insertElementAtTheEndOfList(p1.childlist,heap1);
                    }
                }
            }
            heap = heap1;
        }
    }

}

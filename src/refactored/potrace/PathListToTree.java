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

    

    void pathlist_to_tree(Path pathList, Bitmap bm) {
        bm.setWholeBitmapToSpecificValue(0);

        // save original "next" pointers

        for (Path path = pathList; path != null; path = path.next) {
            path.sibling = path.next;
            path.childlist = null;
        }

        Path heap = pathList;

        /* the heap holds a original.potrace.List of lists of paths. Use "childlist" field
        for outer original.potrace.List, "next" field for inner original.potrace.List. Each of the sublists
        is to be turned into a tree. This code is messy, but it is
        actually fast. Each Path is rendered exactly once. We use the
        heap to get a tail recursive algorithm: the heap holds a original.potrace.List of
        pathlists which still need to be transformed. */

        while (heap != null) {
            // unlink first sublist
            Path cur = heap;
            heap = heap.childlist;
            cur.childlist = null;

            // unlink first Path
            Path head = cur;
            cur = cur.next;
            head.next = null;

            // render Path
            bm.removePathFromBitmap(head);

            BBox bbox = new BBox();
            bbox.setToBoundingBoxOfPath(head);

            /* now do insideness test for each element of cur; append it to
            head->childlist if it's inside head, else append it to
            head->next. */

            Path hook_in = head.childlist;
            Path hook_out = head.next;

            for (Path path=cur; (path != null); path=cur) {
                cur=path.next;
                path.next=null;

                if (path.priv.pt[0].y <= bbox.y0) {
                    head.next = Path.insertElementAtTheEndOfList(path,head.next);
                    head.next = Path.insertListAtTheEndOfList(cur,head.next);
                    break;
                }
                if (bm.getPixelValue(path.priv.pt[0].x, path.priv.pt[0].y-1)) {
                    head.childlist = Path.insertElementAtTheEndOfList(path,head.childlist);

                } else {

                    head.next = Path.insertElementAtTheEndOfList(path,head.next);

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
        Path path = pathList;
        while (path != null) {
            Path p1 = path.sibling;
            path.sibling = path.next;
            path = p1;
        }

        // reconstruct a new linked original.potrace.List ("next") structure from tree
        // ("childlist", "sibling") structure. This code is slightly messy,
        // because we use a heap to make it tail recursive: the heap
        // contains a original.potrace.List of childlists which still need to be
        // processed.
        heap = pathList;
        if (heap != null) {
            heap.next = null;  // heap is a linked original.potrace.List of childlists
        }
        pathList = null;
        while (heap != null) {
            Path heap1 = heap.next;
            for (path=heap; path != null; path=path.sibling) {
                // p is a positive Path
                // append to linked original.potrace.List
                pathList = Path.insertElementAtTheEndOfList(path, pathList);

                // go through its children
                for (Path p1=path.childlist; p1 != null; p1=p1.sibling) {
                    // append to linked original.potrace.List
                    pathList = Path.insertElementAtTheEndOfList(p1, pathList);
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

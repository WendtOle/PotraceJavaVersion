package refactored.potrace;

/**
 * Created by andreydelany on 28.06.17.
 */
public class PathListToTree {

    /* Give a tree structure to the given Path original.potrace.List, based on "insideness"
    testing. I.e., Path A is considered "below" Path B if it is inside
    Path B. The input pathList is assumed to be ordered so that "outer"
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

    Path pathList;
    Bitmap bitmap;

    void pathlist_to_tree(Path pathList, Bitmap bm) {
        this.pathList = pathList;
        this.bitmap = bm;
        bitmap.setWholeBitmapToSpecificValue(0);

        transformIntTree();
    }

    private void transformIntTree() {
        saveOriginalNextPointerToSiblingComponent();

        transformIntoTreeStructure();

        copySiblingStructurFromNextToSiblingComponent();

        reconstructNextComponentFromChildAndSiblingComponent();
    }

    private void saveOriginalNextPointerToSiblingComponent() {
        for (Path path = pathList; path != null; path = path.next) {
            path.sibling = path.next;
            path.childlist = null;
        }
    }

    private void transformIntoTreeStructure() {
        Path pathesThatNeedToProcess = pathList;
        while (pathesThatNeedToProcess != null) {

            // unlink first sublist
            Path pathesToOrder = pathesThatNeedToProcess;
            pathesThatNeedToProcess = pathesThatNeedToProcess.childlist;
            pathesToOrder.childlist = null;

            // unlink first Path
            Path currentPath = pathesToOrder;
            pathesToOrder = pathesToOrder.next;
            currentPath.next = null;

            bitmap.invertPathOnBitmap(currentPath);

            determineChildrenAndSiblings(pathesToOrder, currentPath);

            pathesThatNeedToProcess = scheduleOrderdPathesForFurtherProcessing(pathesThatNeedToProcess, currentPath);

        }
    }

    private Path scheduleOrderdPathesForFurtherProcessing(Path pathesThatNeedToProcess, Path currentPath) {
        boolean hasCurrentPathOtherPathesOutside = currentPath.next != null;
        if (hasCurrentPathOtherPathesOutside) {
            currentPath.next.childlist = pathesThatNeedToProcess;
            pathesThatNeedToProcess = currentPath.next;
        }
        boolean hasCurrentPathOtherPathesInside = currentPath.childlist != null;
        if (hasCurrentPathOtherPathesInside) {
            currentPath.childlist.childlist = pathesThatNeedToProcess;
            pathesThatNeedToProcess = currentPath.childlist;
        }
        return pathesThatNeedToProcess;
    }
    /* now do insideness test for each element of cur; append it to
            head->childlist if it's inside head, else append it to
            head->next. */


    //TODO it is not only a insidenessTest, it also does something
    private void determineChildrenAndSiblings(Path pathListToTest, Path outerPath) {
        BBox bbox = new BBox();
        bbox.setToBoundingBoxOfPath(outerPath);

        for (Path currentPathToTest=pathListToTest; (currentPathToTest != null); currentPathToTest=pathListToTest) {
            pathListToTest=currentPathToTest.next;
            currentPathToTest.next=null;

            boolean isCurrentPathBelowOutherPath = currentPathToTest.priv.pt[0].y <= bbox.y0;
            if (isCurrentPathBelowOutherPath) {
                outerPath.next = Path.insertElementAtTheEndOfList(currentPathToTest,outerPath.next);
                outerPath.next = Path.insertListAtTheEndOfList(pathListToTest,outerPath.next);
                break;
            }
            boolean isCurrentPathInsideOfOuterPath = bitmap.getPixelValue(currentPathToTest.priv.pt[0].x, currentPathToTest.priv.pt[0].y - 1);
            if (isCurrentPathInsideOfOuterPath) {
                outerPath.childlist = Path.insertElementAtTheEndOfList(currentPathToTest,outerPath.childlist);
            } else {
                outerPath.next = Path.insertElementAtTheEndOfList(currentPathToTest,outerPath.next);
            }
        }
        bitmap.clearBitmapWithBBox(bbox);
    }

    private void copySiblingStructurFromNextToSiblingComponent() {
        Path path = pathList;
        while (path != null) {
            Path p1 = path.sibling;
            path.sibling = path.next;
            path = p1;
        }
    }

    private void reconstructNextComponentFromChildAndSiblingComponent() {
        Path heap;
        heap = pathList;
        if (heap != null) {
            heap.next = null;  // heap is a linked original.potrace.List of childlists
        }
        pathList = null;
        while (heap != null) {
            Path heap1 = heap.next;
            for (Path path=heap; path != null; path=path.sibling) {

                pathList = Path.insertElementAtTheEndOfList(path, pathList);

                for (Path p1=path.childlist; p1 != null; p1=p1.sibling) {
                    pathList = Path.insertElementAtTheEndOfList(p1, pathList);

                    if (p1.childlist != null) {
                        heap1 = Path.insertElementAtTheEndOfList(p1.childlist,heap1);
                    }
                }
            }
        heap = heap1;
        }
    }
}

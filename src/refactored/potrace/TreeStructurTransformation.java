package refactored.potrace;

/**
 * Created by andreydelany on 28.06.17.
 */
public class TreeStructurTransformation {
    Path pathList;
    Bitmap bitmap;

    public TreeStructurTransformation(Path pathList, Bitmap bitmap) {
        this.pathList = pathList;
        this.bitmap = bitmap;
        BitmapPixelHandler manipulator = new BitmapPixelHandler(this.bitmap);
        manipulator.setWholeBitmapToSpecificValue(0);

        transformIntoTree();
    }

    private void transformIntoTree() {
        saveOriginalNextPointerToSiblingComponent();
        findChildrenAndSiblings();
        copySiblingStructurFromNextToSiblingComponent();
        reconstructNextComponentFromChildrenAndSiblingComponent();
    }

    private void saveOriginalNextPointerToSiblingComponent() {
        for (Path path = pathList; path != null; path = path.next) {
            path.sibling = path.next;
            path.childlist = null;
        }
    }

    private void findChildrenAndSiblings() {
        ChildrenAndSiblingFinder childrenAndSiblingFinder = new ChildrenAndSiblingFinder(pathList,bitmap);
        pathList = childrenAndSiblingFinder.getPath();
        bitmap = childrenAndSiblingFinder.getBitmap();
    }

    private void copySiblingStructurFromNextToSiblingComponent() {
        Path path = pathList;
        while (path != null) {
            Path p1 = path.sibling;
            path.sibling = path.next;
            path = p1;
        }
    }

    private void reconstructNextComponentFromChildrenAndSiblingComponent() {
        NextComponentReconstruction reconstruction = new NextComponentReconstruction(pathList);
        pathList = reconstruction.getPathWithReconstructedNext();
    }

    public Path getTreeStructure() {
        return pathList;
    }
}
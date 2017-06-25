package refactored.potrace;

    /* Linked List of signed Curve segments. Also carries a tree structure. */

public class Path {
    public int area;                //area of the Bitmap Path
    public int sign;                //+ or -, depending on orientation
    public Curve curve;     // this Path vector data
    public Path next;       //linked original.potrace.List structure
    public Path childlist;  //tree structure
    public Path sibling;    //tree structure
    public PrivePath priv;  /* private state */

    public Path() {
        this.priv = new PrivePath();
    }

    public static Path insertElementAtTheEndOfList(Path element, Path list) {
        if (element != null) {
            if (list != null) {
                Path currentElement = list;
                while (currentElement.next != null) {
                    currentElement = currentElement.next;
                }
                element.next = null;
                currentElement.next = element;
                return list;
            } else {
                element.next = null;
                return element;
            }
        }
        return list;
    }

    public static Path insertListAtTheEndOfList(Path list, Path unmodifiedList) {
        if (list != null) {
            if (unmodifiedList != null) {
                Path currentElement = unmodifiedList;
                while (currentElement.next != null) {
                    currentElement = currentElement.next;
                }
                currentElement.next = list;
                return unmodifiedList;
            } else {
                return list;
            }
        }
        return unmodifiedList;
    }
}

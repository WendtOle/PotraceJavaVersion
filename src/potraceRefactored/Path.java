package potraceRefactored;

    /* Linked List of signed Curve segments. Also carries a tree structure. */

public class Path {
    public int area;                //area of the Bitmap Path
    public int sign;                //+ or -, depending on orientation
    public Curve curve;     // this Path vector data
    public Path next;       //linked potraceOriginal.List structure
    public Path childlist;  //tree structure
    public Path sibling;    //tree structure
    public PrivePath priv;  /* private state */

    public Path() {
        this.priv = new PrivePath();
    }
}

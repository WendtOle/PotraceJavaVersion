package potrace;

    /* Linked list of signed curve segments. Also carries a tree structure. */

public class path {
    public int area;                //area of the bitmap path
    public int sign;                //+ or -, depending on orientation
    public potrace.curve curve;     // this path vector data
    public path next;       //linked potrace.list structure
    public path childlist;  //tree structure
    public path sibling;    //tree structure
    public privepath priv;  /* private state */

    public path() {
        this.priv = new privepath();
    }
}

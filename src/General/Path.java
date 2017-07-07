package General;

    /* Linked List of signed Curve segments. Also carries a tree structure. */


import java.awt.*;

public class Path {
    public int area;                //area of the Bitmap Path
    public int sign;                //+ or -, depending on orientation
    public Curve curve;     // this Path vector data
    public Path next;       //linked original.potrace.List structure
    public Path childlist;  //tree structure
    public Path sibling;    //tree structure
    public PrivePath priv;  /* private state */

    public Path(){
        priv = new PrivePath();
    };

    public Path(int area, int sign, int len, Point[] pointsOfPath) {
        this.area = area;
        this.sign = sign;
        this.priv = new PrivePath();
        this.priv.len = len;
        this.priv.pt = pointsOfPath;
    }
}
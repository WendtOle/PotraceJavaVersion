package AdditionalCode;

import java.awt.*;

/**
 * Created by andreydelany on 19.06.17.
 */
public class Path {
    public int area, sign, length;
    public boolean hasChild, hasSibling;
    public Point[] pt;
    public Path next;
    public Curve curve;

    public Path(int area, int sign, int length, boolean hasChild, boolean hasSibling, Point[] pt, Path next){
        this(area,sign,length,hasChild,hasSibling,pt,null,next);
    }

    public Path(int area, int sign, int length, boolean hasChild, boolean hasSibling, Point[] pt, Curve curve,Path next){
        this.area = area;
        this.sign = sign;
        this.length = length;
        this.hasChild = hasChild;
        this.hasSibling = hasSibling;
        this.pt = pt;
        this.next = next;
        this.curve = curve;
    }
}

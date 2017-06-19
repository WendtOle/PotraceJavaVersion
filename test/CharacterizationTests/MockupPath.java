package CharacterizationTests;

import java.awt.*;

/**
 * Created by andreydelany on 19.06.17.
 */
public class MockupPath{
    int area, sign, length;
    boolean hasChild, hasSibling;
    Point[] pt;

    public MockupPath(int area, int sign, int length,boolean hasChild,boolean hasSibling,Point[] pt){
        this.area = area;
        this.sign = sign;
        this.length = length;
        this.hasChild = hasChild;
        this.hasSibling = hasSibling;
        this.pt = pt;
    }
}

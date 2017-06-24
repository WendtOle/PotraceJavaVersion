package AdditionalCode;

import java.awt.*;

/**
 * Created by andreydelany on 24.06.17.
 */
public class PathTranslator {

    public static Path originalPathToGeneralPath(potraceOriginal.Path path) {
        int area = path.area;
        int sign = path.sign;
        int length = path.priv.len;
        boolean hasChild = (path.childlist != null);
        boolean hasSibling = (path.sibling != null);
        Point[] pt = path.priv.pt;
        Curve curve = new Curve(path.curve.n,path.curve.tag,path.curve.c);
        Path next = null;
        if (path.next != null) {
            next = originalPathToGeneralPath(path.next);
        }
        Path translatedPath = new Path(area,sign,length,hasChild,hasSibling,pt,curve,next);
        return translatedPath;
    }
}

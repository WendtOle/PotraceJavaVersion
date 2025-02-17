package Potrace.refactored;

import Potrace.General.Path;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class BoundingBoxTest {

    Path dummyPath = new Path();

    @Before
    public void setPath(){
        dummyPath.priv.pt = new Point[]{new Point(1,2),new Point(1,1),new Point(2,1),new Point(2,2)};
        dummyPath.priv.len = 4;
    }

    @Test
    public void testInitializingBoundingBoxWithPath() {
        PathBoundingBox box = new PathBoundingBox(dummyPath);

        assertEquals("min X",1,box.x0);
        assertEquals("max X",2,box.x1);
        assertEquals("min Y",1,box.y0);
        assertEquals("max Y",2,box.y1);
    }
}
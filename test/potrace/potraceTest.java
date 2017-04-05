package potrace;

import org.junit.Before;
import org.junit.Test;
import BitmapLibrary.*;

/**
 * Created by andreydelany on 21/03/2017.
 */
public class potraceTest {

    potrace_bitmap[] bitmaps = new potrace_bitmap[5];

    @Before
    public void before() {
        bitmaps[0] = new DefaultBimapSmallBox();
        bitmaps[1] = new DefaultBitmap();
        bitmaps[2] = new DefaultBitmapSimpleChildList();
        bitmaps[3] = new DefaultBitmapWithChildrenAndSiblings();
        bitmaps[4] = new DefaultBitmapSimpleBoxOverPotraceWordBoundaries();
    }

    @Test
    public void testAllBitmaps() throws Exception {
        potrace_param param = new potrace_param();
        potrace_path result;
        for (int i = 0; i < bitmaps.length; i++) {
            System.out.println("currently testing bitmap: " + i);
            result = PotraceLib.potrace_trace(param,bitmaps[i]);
            System.out.println("tested bitmap without errors");
        }


    }


}

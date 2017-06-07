package potrace;

import org.junit.Before;
import org.junit.Test;
import BitmapLibrary.*;

/**
 * Created by andreydelany on 21/03/2017.
 */
public class potraceTest {

    bitmap[] bitmaps = new bitmap[4];

    @Before
    public void before() {
        bitmaps[0] = new DefaultBimapSmallBox();
        bitmaps[1] = new DefaultBitmap();
        bitmaps[2] = new DefaultBitmapSimpleChildList();
        bitmaps[3] = new DefaultBitmapWithChildrenAndSiblings();
    }

    @Test
    public void testAllBitmaps() throws Exception {
        param param = new param();
        path result;
        for (int i = 0; i < bitmaps.length; i++) {
            System.out.println("currently testing bitmap: " + i);
            result = potraceLib.potrace_trace(param,bitmaps[i]);
            System.out.println("tested bitmap without errors");
        }


    }


}

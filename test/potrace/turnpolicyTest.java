package potrace;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by andreydelany on 05/04/2017.
 */
public class turnpolicyTest {

    bitmap simpleDiagonalBitmap, biggerDiagonalBitmap;

    @Before
    public void prepareBitmaps() {
        simpleDiagonalBitmap = new bitmap(2,2);
        bitmap.BM_PUT(simpleDiagonalBitmap,0,0,true);
        bitmap.BM_PUT(simpleDiagonalBitmap,1,1,true);

        biggerDiagonalBitmap = new bitmap(3,3);
        bitmap.BM_PUT(biggerDiagonalBitmap,0,0,true);
        bitmap.BM_PUT(biggerDiagonalBitmap,1,1,true);
        bitmap.BM_PUT(biggerDiagonalBitmap,2,2,true);

    }

    @Test
    public void testTurnpolicyMinority() throws Exception {
        path pathWithMinorityTurnpolicy = decompose.findpath(biggerDiagonalBitmap,2,3,43,4);
        assertEquals(3,pathWithMinorityTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyMajority() throws Exception {
        path pathWithMajorityTurnpolicy = decompose.findpath(biggerDiagonalBitmap,2,3,43,5);
        assertEquals(1,pathWithMajorityTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyBlack() {
        path pathWithBlackTurnpolicy = decompose.findpath(simpleDiagonalBitmap,1,2,43,0);
        assertEquals(2,pathWithBlackTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyWhite() {
        path pathWithWhiteTurnpolicy = decompose.findpath(simpleDiagonalBitmap,1,2,43,1);
        assertEquals(1,pathWithWhiteTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyLeft() {
        path pathWithLeftTurnpolicy = decompose.findpath(simpleDiagonalBitmap,1,2,43,2);
        assertEquals(1,pathWithLeftTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyRight() {
        path pathWithRightTurnpolicy = decompose.findpath(simpleDiagonalBitmap,1,2,43,3);
        assertEquals(2,pathWithRightTurnpolicy.area);
    }
}
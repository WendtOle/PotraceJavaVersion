package potraceRefactored;

import org.junit.Before;
import org.junit.Test;
import potraceOriginal.Bitmap;
import potraceOriginal.Decompose;
import potraceOriginal.Path;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 05/04/2017.
 */
public class turnpolicyTest {

    potraceOriginal.Bitmap simpleDiagonalBitmap, biggerDiagonalBitmap;

    @Before
    public void prepareBitmaps() {
        simpleDiagonalBitmap = new potraceOriginal.Bitmap(2,2);
        potraceOriginal.Bitmap.BM_PUT(simpleDiagonalBitmap,0,0,true);
        potraceOriginal.Bitmap.BM_PUT(simpleDiagonalBitmap,1,1,true);

        biggerDiagonalBitmap = new potraceOriginal.Bitmap(3,3);
        potraceOriginal.Bitmap.BM_PUT(biggerDiagonalBitmap,0,0,true);
        potraceOriginal.Bitmap.BM_PUT(biggerDiagonalBitmap,1,1,true);
        Bitmap.BM_PUT(biggerDiagonalBitmap,2,2,true);

    }

    @Test
    public void testTurnpolicyMinority() throws Exception {
        Path pathWithMinorityTurnpolicy = Decompose.findpath(biggerDiagonalBitmap,2,3,43,4);
        assertEquals(3,pathWithMinorityTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyMajority() throws Exception {
        Path pathWithMajorityTurnpolicy = Decompose.findpath(biggerDiagonalBitmap,2,3,43,5);
        assertEquals(1,pathWithMajorityTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyBlack() {
        Path pathWithBlackTurnpolicy = Decompose.findpath(simpleDiagonalBitmap,1,2,43,0);
        assertEquals(2,pathWithBlackTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyWhite() {
        Path pathWithWhiteTurnpolicy = Decompose.findpath(simpleDiagonalBitmap,1,2,43,1);
        assertEquals(1,pathWithWhiteTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyLeft() {
        Path pathWithLeftTurnpolicy = Decompose.findpath(simpleDiagonalBitmap,1,2,43,2);
        assertEquals(1,pathWithLeftTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyRight() {
        Path pathWithRightTurnpolicy = Decompose.findpath(simpleDiagonalBitmap,1,2,43,3);
        assertEquals(2,pathWithRightTurnpolicy.area);
    }
}
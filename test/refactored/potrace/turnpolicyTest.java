package refactored.potrace;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 05/04/2017.
 */
public class turnpolicyTest {

    Bitmap simpleDiagonalBitmap, biggerDiagonalBitmap;

    @Before
    public void prepareBitmaps() {
        simpleDiagonalBitmap = new Bitmap(2,2);
        simpleDiagonalBitmap.setPixelToValue(0,0,true);
        simpleDiagonalBitmap.setPixelToValue(1,1,true);

        biggerDiagonalBitmap = new Bitmap(3,3);
        biggerDiagonalBitmap.setPixelToValue(0,0,true);
        biggerDiagonalBitmap.setPixelToValue(1,1,true);
        biggerDiagonalBitmap.setPixelToValue(2,2,true);

    }

    @Test
    public void testTurnpolicyMinority() throws Exception {
        FindPath pathFinder = new FindPath(biggerDiagonalBitmap,new Point(2,3),43,4);
        Path pathWithMinorityTurnpolicy = pathFinder.path;
        assertEquals(3,pathWithMinorityTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyMajority() throws Exception {

        FindPath findPath = new FindPath(biggerDiagonalBitmap,new Point(2,3),43,5);
        Path pathWithMajorityTurnpolicy = findPath.path;
        assertEquals(1,pathWithMajorityTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyBlack() {
        FindPath findPath = new FindPath(simpleDiagonalBitmap,new Point(1,2),43,0);
        Path pathWithBlackTurnpolicy = findPath.path;
        assertEquals(2,pathWithBlackTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyWhite() {
        FindPath findPath = new FindPath(simpleDiagonalBitmap,new Point(1,2),43,1);
        Path pathWithWhiteTurnpolicy = findPath.path;
        assertEquals(1,pathWithWhiteTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyLeft() {
        FindPath findPath = new FindPath(simpleDiagonalBitmap,new Point(1,2),43,2);
        Path pathWithLeftTurnpolicy = findPath.path;
        assertEquals(1,pathWithLeftTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyRight() {
        FindPath findPath = new FindPath(simpleDiagonalBitmap,new Point(1,2),43,3);
        Path pathWithRightTurnpolicy = findPath.path;
        assertEquals(2,pathWithRightTurnpolicy.area);
    }
}
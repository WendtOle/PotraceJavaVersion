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
        simpleDiagonalBitmap.setPixelToValue(new Point(0,0),true);
        simpleDiagonalBitmap.setPixelToValue(new Point(1,1),true);

        biggerDiagonalBitmap = new Bitmap(3,3);
        biggerDiagonalBitmap.setPixelToValue(new Point(0,0),true);
        biggerDiagonalBitmap.setPixelToValue(new Point(1,1),true);
        biggerDiagonalBitmap.setPixelToValue(new Point(2,2),true);

    }

    @Test
    public void testTurnpolicyMinority() throws Exception {
        FindPath pathFinder = new FindPath(biggerDiagonalBitmap,new Point(2,3),43,4);
        Path pathWithMinorityTurnpolicy = pathFinder.getPath();
        assertEquals(3,pathWithMinorityTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyMajority() throws Exception {

        FindPath findPath = new FindPath(biggerDiagonalBitmap,new Point(2,3),43,5);
        Path pathWithMajorityTurnpolicy = findPath.getPath();
        assertEquals(1,pathWithMajorityTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyBlack() {
        FindPath findPath = new FindPath(simpleDiagonalBitmap,new Point(1,2),43,0);
        Path pathWithBlackTurnpolicy = findPath.getPath();
        assertEquals(2,pathWithBlackTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyWhite() {
        FindPath findPath = new FindPath(simpleDiagonalBitmap,new Point(1,2),43,1);
        Path pathWithWhiteTurnpolicy = findPath.getPath();
        assertEquals(1,pathWithWhiteTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyLeft() {
        FindPath findPath = new FindPath(simpleDiagonalBitmap,new Point(1,2),43,2);
        Path pathWithLeftTurnpolicy = findPath.getPath();
        assertEquals(1,pathWithLeftTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyRight() {
        FindPath findPath = new FindPath(simpleDiagonalBitmap,new Point(1,2),43,3);
        Path pathWithRightTurnpolicy = findPath.getPath();
        assertEquals(2,pathWithRightTurnpolicy.area);
    }
}
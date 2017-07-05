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
        BitmapHandlerInterface manipulator = new BitmapHandler(simpleDiagonalBitmap);
        manipulator.setPixel(new Point(0,0));
        manipulator.setPixel(new Point(1,1));

        biggerDiagonalBitmap = new Bitmap(3,3);
        manipulator = new BitmapHandler(biggerDiagonalBitmap);
        manipulator.setPixel(new Point(0,0));
        manipulator.setPixel(new Point(1,1));
        manipulator.setPixel(new Point(2,2));

    }

    @Test
    public void testTurnpolicyMinority() throws Exception {
        FindPath pathFinder = new FindPath(biggerDiagonalBitmap,new Point(2,3),43,TurnPolicyEnum.MINORITY);
        Path pathWithMinorityTurnpolicy = pathFinder.getPath();
        assertEquals(3,pathWithMinorityTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyMajority() throws Exception {

        FindPath findPath = new FindPath(biggerDiagonalBitmap,new Point(2,3),43,TurnPolicyEnum.MAJORITY);
        Path pathWithMajorityTurnpolicy = findPath.getPath();
        assertEquals(1,pathWithMajorityTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyBlack() {
        FindPath findPath = new FindPath(simpleDiagonalBitmap,new Point(1,2),43,TurnPolicyEnum.BLACK);
        Path pathWithBlackTurnpolicy = findPath.getPath();
        assertEquals(2,pathWithBlackTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyWhite() {
        FindPath findPath = new FindPath(simpleDiagonalBitmap,new Point(1,2),43,TurnPolicyEnum.WHITE);
        Path pathWithWhiteTurnpolicy = findPath.getPath();
        assertEquals(1,pathWithWhiteTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyLeft() {
        FindPath findPath = new FindPath(simpleDiagonalBitmap,new Point(1,2),43,TurnPolicyEnum.LEFT);
        Path pathWithLeftTurnpolicy = findPath.getPath();
        assertEquals(1,pathWithLeftTurnpolicy.area);
    }

    @Test
    public void testTurnpolicyRight() {
        FindPath findPath = new FindPath(simpleDiagonalBitmap,new Point(1,2),43,TurnPolicyEnum.RIGHT);
        Path pathWithRightTurnpolicy = findPath.getPath();
        assertEquals(2,pathWithRightTurnpolicy.area);
    }
}
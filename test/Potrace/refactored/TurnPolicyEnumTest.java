package Potrace.refactored;

import Potrace.General.Bitmap;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 04.07.17.
 */
public class TurnPolicyEnumTest {
    BitmapHandlerInterface bitmapHandlerMinorityIsBlack = new BitmapHandler(new Bitmap(3,3));
    BitmapHandlerInterface bitmapHandlerMajorityIsBlack = new BitmapHandler(new Bitmap(4,4));

    @Before
    public void prepareBitmap() {
        bitmapHandlerMinorityIsBlack.setPixel(new Point(0,0));
        bitmapHandlerMinorityIsBlack.setPixel(new Point(1,1));

        bitmapHandlerMajorityIsBlack.setPixel(new Point(0,0));
        bitmapHandlerMajorityIsBlack.setPixel(new Point(1,0));
        bitmapHandlerMajorityIsBlack.setPixel(new Point(2,0));
        bitmapHandlerMajorityIsBlack.setPixel(new Point(3,0));
        bitmapHandlerMajorityIsBlack.setPixel(new Point(0,1));

        bitmapHandlerMajorityIsBlack.setPixel(new Point(2,1));
        bitmapHandlerMajorityIsBlack.setPixel(new Point(3,1));
        bitmapHandlerMajorityIsBlack.setPixel(new Point(0,2));
        bitmapHandlerMajorityIsBlack.setPixel(new Point(1,2));

        bitmapHandlerMajorityIsBlack.setPixel(new Point(3,2));
        bitmapHandlerMajorityIsBlack.setPixel(new Point(0,3));
        bitmapHandlerMajorityIsBlack.setPixel(new Point(1,3));
        bitmapHandlerMajorityIsBlack.setPixel(new Point(2,3));
        bitmapHandlerMajorityIsBlack.setPixel(new Point(3,3));
    }

    @Test
    public void testTurnpolicyMinority() throws Exception {
        assertTrue(TurnPolicyEnum.MINORITY.isTurnPolicySatisfied(43, bitmapHandlerMinorityIsBlack,new Point(1,1)));
    }

    @Test
    public void testTurnpolicyMajority() throws Exception {
        assertFalse(TurnPolicyEnum.MAJORITY.isTurnPolicySatisfied(43, bitmapHandlerMinorityIsBlack,new Point(1,1)));
    }

    @Test
    public void testTurnpolicyBlack() throws Exception {
        assertTrue(TurnPolicyEnum.BLACK.isTurnPolicySatisfied(43, bitmapHandlerMinorityIsBlack,new Point(1,1)));
    }

    @Test
    public void testTurnpolicyWhite() throws Exception {
        assertFalse(TurnPolicyEnum.WHITE.isTurnPolicySatisfied(43, bitmapHandlerMinorityIsBlack,new Point(1,1)));
    }

    @Test
    public void testTurnpolicyLeft() throws Exception {
        assertFalse(TurnPolicyEnum.LEFT.isTurnPolicySatisfied(43, bitmapHandlerMinorityIsBlack,new Point(1,1)));
    }

    @Test
    public void testTurnpolicyRight() throws Exception {
        assertTrue(TurnPolicyEnum.RIGHT.isTurnPolicySatisfied(43, bitmapHandlerMinorityIsBlack,new Point(1,1)));
    }

    @Test
    public void testTurnpolicyMajorityWithMajorityIsBlack() throws Exception {
        assertTrue(TurnPolicyEnum.MAJORITY.isTurnPolicySatisfied(43, bitmapHandlerMajorityIsBlack,new Point(2,2)));
    }
}

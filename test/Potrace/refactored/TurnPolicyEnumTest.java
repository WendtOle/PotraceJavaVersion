package Potrace.refactored;

import Potrace.General.Bitmap;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TurnPolicyEnumTest {
    BitmapHandlerInterface bitmapHandlerMinorityIsBlack = new BitmapHandler(new Bitmap(3,3));
    BitmapHandlerInterface bitmapHandlerMajorityIsBlack = new BitmapHandler(new Bitmap(4,4));

    @Before
    public void setBitmapWhereMinorityAreFilledPixel() {
        bitmapHandlerMinorityIsBlack.setPixel(new Point(0,0));
        bitmapHandlerMinorityIsBlack.setPixel(new Point(1,1));
    }

    @Before
    public void setBitmapWhereMajorityOfPixelAreFilled() {
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
    public void testTurnPolicyMinority() throws Exception {
        assertTrue(TurnPolicyEnum.MINORITY.isTurnPolicySatisfied(PathKindEnum.POSITIV, bitmapHandlerMinorityIsBlack,new Point(1,1)));
    }

    @Test
    public void testTurnPolicyMajority() throws Exception {
        assertFalse(TurnPolicyEnum.MAJORITY.isTurnPolicySatisfied(PathKindEnum.POSITIV, bitmapHandlerMinorityIsBlack,new Point(1,1)));
    }

    @Test
    public void testTurnPolicyBlack() throws Exception {
        assertTrue(TurnPolicyEnum.BLACK.isTurnPolicySatisfied(PathKindEnum.POSITIV, bitmapHandlerMinorityIsBlack,new Point(1,1)));
    }

    @Test
    public void testTurnPolicyWhite() throws Exception {
        assertFalse(TurnPolicyEnum.WHITE.isTurnPolicySatisfied(PathKindEnum.POSITIV, bitmapHandlerMinorityIsBlack,new Point(1,1)));
    }

    @Test
    public void testTurnPolicyLeft() throws Exception {
        assertFalse(TurnPolicyEnum.LEFT.isTurnPolicySatisfied(PathKindEnum.POSITIV, bitmapHandlerMinorityIsBlack,new Point(1,1)));
    }

    @Test
    public void testTurnPolicyRight() throws Exception {
        assertTrue(TurnPolicyEnum.RIGHT.isTurnPolicySatisfied(PathKindEnum.POSITIV, bitmapHandlerMinorityIsBlack,new Point(1,1)));
    }

    @Test
    public void testTurnPolicyMajorityWithMajorityIsBlack() throws Exception {
        assertTrue(TurnPolicyEnum.MAJORITY.isTurnPolicySatisfied(PathKindEnum.POSITIV, bitmapHandlerMajorityIsBlack,new Point(2,2)));
    }
}
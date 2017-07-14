package Potrace.refactored;

import java.awt.*;

/**
 * Created by andreydelany on 14.07.17.
 */
public class Random {
    char[] nonLinearSequence = {
            0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1,
            0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0,
            0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1,
            1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1,
            0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0,
            0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0,
            0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0,
            0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1,
            1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0,
            0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1,
            1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0,
    };

    public boolean getRandomBoolean(Point pixel) {
        int modifier = getModifier(pixel);
        return getDifferentCombinedPartsOfNonLinearSequence(modifier) == 1;
    }

    private int getDifferentCombinedPartsOfNonLinearSequence(int modifier) {
        int firstByte = nonLinearSequence[modifier & 0xff];
        int secondByte = nonLinearSequence[(modifier>>8) & 0xff];
        int thirdByte = nonLinearSequence[(modifier>>16) & 0xff];
        int fourthByte = nonLinearSequence[(modifier>>16) & 0xff];
        return firstByte ^ secondByte ^ thirdByte ^ fourthByte;
    }

    private int getModifier(Point pixel) {
        return ((0x04b3e375 * pixel.x) ^ pixel.y) * 0x05a8ef93;
    }
}

package CharacterizationTests;

import potraceOriginal.Bitmap;

import java.awt.*;

/**
 * Created by andreydelany on 19.06.17.
 */
public class Example02 extends AbstractTestData{

    Bitmap getBitmap() {
        Bitmap bitmap = new Bitmap(128,3);
        bitmap.map[0] = 0xFFFFl;
        bitmap.map[1] = 0xFFFC000000000000l;
        bitmap.map[2] = 0x8000l;
        bitmap.map[3] = 0x4000000000000000l;
        bitmap.map[4] = 0xFFFF;
        bitmap.map[5] = 0xFFFC000000000000l;

        return bitmap;
    }

    MockupPath[] getArrayOfAllPathes() {

        MockupPath[] path = new MockupPath[]{
                new MockupPath(78,43,90,
                        true,
                        false,
                        new Point[]{new Point(48,3),
                                new Point(48,2),new Point(48,1),new Point(48,0),new Point(49,0),new Point(50,0),
                                new Point(51,0),new Point(52,0),new Point(53,0),new Point(54,0),new Point(55,0),
                                new Point(56,0),new Point(57,0),new Point(58,0),new Point(59,0),new Point(60,0),
                                new Point(61,0),new Point(62,0),new Point(63,0),new Point(64,0),new Point(65,0),
                                new Point(66,0),new Point(67,0),new Point(68,0),new Point(69,0),new Point(70,0),
                                new Point(71,0),new Point(72,0),new Point(73,0),new Point(74,0),new Point(75,0),
                                new Point(76,0),new Point(77,0),new Point(78,0),new Point(78,1),new Point(77,1),
                                new Point(76,1),new Point(75,1),new Point(74,1),new Point(73,1),new Point(72,1),
                                new Point(71,1),new Point(70,1),new Point(69,1),new Point(68,1),new Point(67,1),
                                new Point(66,1),new Point(66,2),new Point(67,2),new Point(68,2),new Point(69,2),
                                new Point(70,2),new Point(71,2),new Point(72,2),new Point(73,2),new Point(74,2),
                                new Point(75,2),new Point(76,2),new Point(77,2),new Point(78,2),new Point(78,3),
                                new Point(77,3),new Point(76,3),new Point(75,3),new Point(74,3),new Point(73,3),
                                new Point(72,3),new Point(71,3),new Point(70,3),new Point(69,3),new Point(68,3),
                                new Point(67,3),new Point(66,3),new Point(65,3),new Point(64,3),new Point(63,3),
                                new Point(62,3),new Point(61,3),new Point(60,3),new Point(59,3),new Point(58,3),
                                new Point(57,3),new Point(56,3),new Point(55,3),new Point(54,3),new Point(53,3),
                                new Point(52,3),new Point(51,3),new Point(50,3),new Point(49,3),}),
                new MockupPath(16,45,34,
                        false,
                        false,
                        new Point[]{new Point(49,2),
                                new Point(49,1),new Point(50,1),new Point(51,1),new Point(52,1),new Point(53,1),
                                new Point(54,1),new Point(55,1),new Point(56,1),new Point(57,1),new Point(58,1),
                                new Point(59,1),new Point(60,1),new Point(61,1),new Point(62,1),new Point(63,1),
                                new Point(64,1),new Point(65,1),new Point(65,2),new Point(64,2),new Point(63,2),
                                new Point(62,2),new Point(61,2),new Point(60,2),new Point(59,2),new Point(58,2),
                                new Point(57,2),new Point(56,2),new Point(55,2),new Point(54,2),new Point(53,2),
                                new Point(52,2),new Point(51,2),new Point(50,2),})
        };
        return path;
    }
}

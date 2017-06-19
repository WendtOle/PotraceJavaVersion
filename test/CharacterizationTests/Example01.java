package CharacterizationTests;

import potraceOriginal.Bitmap;

import java.awt.*;

/**
 * Created by andreydelany on 19.06.17.
 */
public class Example01 extends AbstractTestData{

    Bitmap getBitmap() {
        Bitmap bitmap = new Bitmap(15,14);
        bitmap.map[0] = 0xEF92000000000000l;
        bitmap.map[1] = 0x63EA000000000000l;
        bitmap.map[2] = 0xE96E000000000000l;
        bitmap.map[3] = 0x3F56000000000000l;
        bitmap.map[4] = 0xD3D6000000000000l;
        bitmap.map[5] = 0xF356000000000000l;
        bitmap.map[6] = 0xFE6C000000000000l;
        bitmap.map[7] = 0xEBBA000000000000l;
        bitmap.map[8] = 0x5948000000000000l;
        bitmap.map[9] = 0x64DE000000000000l;
        bitmap.map[10] = 0xE56C000000000000l;
        bitmap.map[11] = 0xBD7A000000000000l;
        bitmap.map[12] = 0x2CE4000000000000l;
        bitmap.map[13] = 0xFF3C000000000000l;

        return bitmap;
    }

    MockupPath[] getArrayOfAllPathes() {

        MockupPath[] path = new MockupPath[]{
                new MockupPath(180,43,108,
                        true,
                        false,
                        new Point[]{new Point(0,14),
                                new Point(0,13),new Point(1,13),new Point(2,13),new Point(2,12),new Point(2,11),
                                new Point(1,11),new Point(1,12),new Point(0,12),new Point(0,11),new Point(0,10),
                                new Point(1,10),new Point(1,9),new Point(1,8),new Point(0,8),new Point(0,7),
                                new Point(0,6),new Point(0,5),new Point(0,4),new Point(1,4),new Point(2,4),
                                new Point(2,5),new Point(3,5),new Point(3,4),new Point(2,4),new Point(2,3),
                                new Point(1,3),new Point(0,3),new Point(0,2),new Point(1,2),new Point(1,1),
                                new Point(0,1),new Point(0,0),new Point(1,0),new Point(2,0),new Point(3,0),
                                new Point(3,1),new Point(3,2),new Point(3,3),new Point(4,3),new Point(4,2),
                                new Point(5,2),new Point(5,3),new Point(6,3),new Point(7,3),new Point(7,2),
                                new Point(6,2),new Point(6,1),new Point(5,1),new Point(4,1),new Point(4,0),
                                new Point(5,0),new Point(6,0),new Point(7,0),new Point(8,0),new Point(9,0),
                                new Point(9,1),new Point(10,1),new Point(11,1),new Point(11,0),new Point(12,0),
                                new Point(12,1),new Point(13,1),new Point(13,2),new Point(14,2),new Point(14,1),
                                new Point(14,0),new Point(15,0),new Point(15,1),new Point(15,2),new Point(15,3),
                                new Point(15,4),new Point(15,5),new Point(15,6),new Point(14,6),new Point(14,7),
                                new Point(15,7),new Point(15,8),new Point(14,8),new Point(14,7),new Point(13,7),
                                new Point(13,8),new Point(13,9),new Point(14,9),new Point(15,9),new Point(15,10),
                                new Point(14,10),new Point(14,11),new Point(15,11),new Point(15,12),new Point(14,12),
                                new Point(14,13),new Point(14,14),new Point(13,14),new Point(12,14),new Point(11,14),
                                new Point(10,14),new Point(10,13),new Point(9,13),new Point(8,13),new Point(8,14),
                                new Point(7,14),new Point(6,14),new Point(5,14),new Point(4,14),new Point(3,14),
                                new Point(2,14),new Point(1,14),}),
                new MockupPath(22,45,48,
                        false,
                        true,
                        new Point[]{new Point(6,13),
                                new Point(6,12),new Point(6,11),new Point(6,10),new Point(6,9),new Point(5,9),
                                new Point(5,8),new Point(5,7),new Point(6,7),new Point(6,8),new Point(7,8),
                                new Point(7,9),new Point(8,9),new Point(8,8),new Point(9,8),new Point(9,7),
                                new Point(8,7),new Point(7,7),new Point(7,6),new Point(8,6),new Point(8,5),
                                new Point(9,5),new Point(9,6),new Point(9,7),new Point(10,7),new Point(10,8),
                                new Point(11,8),new Point(12,8),new Point(12,9),new Point(11,9),new Point(11,10),
                                new Point(12,10),new Point(12,11),new Point(11,11),new Point(11,10),new Point(10,10),
                                new Point(10,9),new Point(10,8),new Point(9,8),new Point(9,9),new Point(8,9),
                                new Point(8,10),new Point(9,10),new Point(9,11),new Point(9,12),new Point(8,12),
                                new Point(8,13),new Point(7,13),}),
                new MockupPath(3,45,10,
                        false,
                        true,
                        new Point[]{new Point(11,13),
                                new Point(11,12),new Point(12,12),new Point(13,12),new Point(13,11),new Point(14,11),
                                new Point(14,12),new Point(13,12),new Point(13,13),new Point(12,13),}),
                new MockupPath(6,45,16,
                        false,
                        true,
                        new Point[]{new Point(3,11),
                                new Point(3,10),new Point(3,9),new Point(2,9),new Point(2,8),new Point(3,8),
                                new Point(3,7),new Point(4,7),new Point(4,8),new Point(3,8),new Point(3,9),
                                new Point(4,9),new Point(5,9),new Point(5,10),new Point(5,11),new Point(4,11),
                        }),
                new MockupPath(12,45,18,
                        true,
                        true,
                        new Point[]{new Point(11,7),
                                new Point(11,6),new Point(10,6),new Point(10,5),new Point(10,4),new Point(10,3),
                                new Point(11,3),new Point(11,2),new Point(11,1),new Point(12,1),new Point(12,2),
                                new Point(12,3),new Point(13,3),new Point(13,4),new Point(13,5),new Point(13,6),
                                new Point(12,6),new Point(12,7),}),
                new MockupPath(4,45,8,
                        false,
                        false,
                        new Point[]{new Point(4,6),
                                new Point(4,5),new Point(4,4),new Point(5,4),new Point(6,4),new Point(6,5),
                                new Point(6,6),new Point(5,6),}),
                new MockupPath(3,43,8,
                        false,
                        false,
                        new Point[]{new Point(11,6),
                                new Point(11,5),new Point(11,4),new Point(11,3),new Point(12,3),new Point(12,4),
                                new Point(12,5),new Point(12,6),})
        };
        return path;
    }
}

package potrace;

import Tools.BitmapPrinter;
import Tools.CurvePrinter;
import Tools.PolygonArchitecturePrinter;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by andreydelany on 21/03/2017.
 */
public class potraceTest {

    potrace_bitmap[] bitmaps = new potrace_bitmap[8];

    private potrace_bitmap default_bitmap_second() {
        potrace_bitmap newBitmap = new potrace_bitmap(4,4);
        newBitmap.map[3]= 0x90000000;            // X o o X
        newBitmap.map[2]= 0xE0000000;            // X X X o
        newBitmap.map[1]= 0x30000000;            // o o X X
        newBitmap.map[0]= 0x90000000;            // X o o X
        return newBitmap;
    }

    private potrace_bitmap default_bitmap_first() {
        potrace_bitmap newBitmap = new potrace_bitmap(4,4);
        newBitmap.map[3]= 0x00000000;            // o o o o
        newBitmap.map[2]= 0x60000000;            // o X X o
        newBitmap.map[1]= 0x60000000;            // o X X o
        newBitmap.map[0]= 0x00000000;            // o o o o
        return newBitmap;
    }

    private potrace_bitmap default_bitmap_Fourth() {
        potrace_bitmap newBitmap = new potrace_bitmap(7,7);
        newBitmap.map[6]= 0xfe000000;            //  X X X X X X X
        newBitmap.map[5]= 0x82000000;            //  X o o o o o X
        newBitmap.map[4]= 0xba000000;            //  X o X X X o X
        newBitmap.map[3]= 0xaa000000;            //  X o X o X o X
        newBitmap.map[2]= 0xba000000;            //  X o X X X o X
        newBitmap.map[1]= 0x82000000;            //  X o o o o o X
        newBitmap.map[0]= 0xfe000000;            //  X X X X X X X
        return newBitmap;
    }

    private potrace_bitmap default_bitmap_Fifth() {
        potrace_bitmap newBitmap = new potrace_bitmap(8,8);
        newBitmap.map[7]= 0xfb000000;            //  X X X X X o X X
        newBitmap.map[6]= 0x88000000;            //  X o o o X o o o
        newBitmap.map[5]= 0xae000000;            //  X o X o X X X o
        newBitmap.map[4]= 0xa2000000;            //  X o X o o o X o
        newBitmap.map[3]= 0x8a000000;            //  X o o o X o X o
        newBitmap.map[2]= 0xba000000;            //  X o X X X o X o
        newBitmap.map[1]= 0x82000000;            //  X o o o o o X o
        newBitmap.map[0]= 0xfe000000;            //  X X X X X X X o
        return newBitmap;
    }

    private potrace_bitmap default_bitmap_Sixth() {
        potrace_bitmap newBitmap = new potrace_bitmap(32,32);
        for(int i = 0; i < 32; i ++) {
            newBitmap.map[i]= 0xfedcba9;
        }
        return newBitmap;
    }

    private potrace_bitmap default_bitmap_Seventh() {
        potrace_bitmap newBitmap = new potrace_bitmap(32,3);
        for(int i = 0; i < 3; i ++) {
            newBitmap.map[i]= 0x1;
        }
        return newBitmap;
    }

    private potrace_bitmap default_bitmap_Eigth() {
        potrace_bitmap newBitmap = new potrace_bitmap(128,3);
        newBitmap.map[0]= 0xffffffff;
        newBitmap.map[1]= 0xffffffff;
        newBitmap.map[2]= 0xffffffff;
        newBitmap.map[3]= 0xffffffff;
        newBitmap.map[4]= 0x80000000;
        newBitmap.map[5]= 0x0;
        newBitmap.map[6]= 0x0;
        newBitmap.map[7]= 0x1;
        newBitmap.map[8]= 0xffffffff;
        newBitmap.map[9]= 0xffffffff;
        newBitmap.map[10]= 0xffffffff;
        newBitmap.map[11]= 0xffffffff;
        return newBitmap;
    }

    private potrace_bitmap default_bitmap_Ten() {
        potrace_bitmap newBitmap = new potrace_bitmap(128,4);
        newBitmap.map[15]= 0x344b90a0;
        newBitmap.map[14]= 0xb25cd19d;
        newBitmap.map[13]= 0xf3182ad3;
        newBitmap.map[12]= 0x5200f5c7;

        newBitmap.map[11]= 0x5f69b290;
        newBitmap.map[10]= 0xa137ca74;
        newBitmap.map[9]= 0x8255f352;
        newBitmap.map[8]= 0x7e2b6daa;

        newBitmap.map[7]= 0x15aac2b8;
        newBitmap.map[6]= 0x8dd23a93;
        newBitmap.map[5]= 0x2dd8e813;
        newBitmap.map[4]= 0x4882ff30;

        newBitmap.map[3]= 0xf488b3a8;
        newBitmap.map[2]= 0x5bbd4559;
        newBitmap.map[1]= 0xe7bbcb0e;
        newBitmap.map[0]= 0x4ef247da;
        return newBitmap;
    }


    @Before
    public void before() {
        bitmaps[0] = default_bitmap_first();
        bitmaps[1] = default_bitmap_second();
        bitmaps[2] = default_bitmap_Fourth();
        bitmaps[3] = default_bitmap_Fifth();
        bitmaps[4] = default_bitmap_Sixth();
        bitmaps[5] = default_bitmap_Seventh();
        bitmaps[6] = default_bitmap_Eigth();
        bitmaps[7] = default_bitmap_Ten();
    }

    @Test
    public void testAllBitmaps() throws Exception {
        potrace_param param = new potrace_param();
        potrace_path result;
        for (int i = 0; i < bitmaps.length; i++)
            result = PotraceLib.potrace_trace(param,bitmaps[i]);
    }


}

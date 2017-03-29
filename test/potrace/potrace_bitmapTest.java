package potrace;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import potrace.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by andreydelany on 07/03/2017.
 */
public class potrace_bitmapTest {

    potrace_bitmap simpleDefaultPicture = new potrace_bitmap(4,4);
    potrace_bitmap dificultDefaultPicture = new potrace_bitmap(40,4);


    @Before
    public void setUP() {
        int [] simpleMap = {0x80000000,0x40000000,0x20000000,0x10000000};
        simpleDefaultPicture.map = simpleMap;
        int[] dificultMap = {0x00ffffff,0xff00ffff,0xffff00ff,0xffffff00,0xf0000000,0xf0000000,0xf0000000,0xf0000000};
        dificultDefaultPicture.map = dificultMap;
    }

    @org.junit.Test
    public void checkDYOfSimplePicture() throws Exception {
        System.out.println("Checking Initialisation of DY:");
        System.out.println("- should be: 1");
        System.out.println("- was: " + simpleDefaultPicture.dy);
        Assert.assertEquals(1,simpleDefaultPicture.dy);
    }

    @org.junit.Test
    public void checkDYOfDificultPicture() throws Exception {
        System.out.println("Checking Initialisation of DY:");
        System.out.println("- should be: 2");
        System.out.println("- was: " + dificultDefaultPicture.dy);
        Assert.assertEquals(2,dificultDefaultPicture.dy);
    }

    @org.junit.Test
    public void checkLengthOfMap() throws Exception {
        System.out.println("Checking Length of Map:");
        System.out.println("- should be: 4");
        System.out.println("- was: " + simpleDefaultPicture.map.length);
        Assert.assertEquals(4,simpleDefaultPicture.map.length);
    }

    @org.junit.Test
    public void checkWetherYouGetTheCorrectScanLineWithSimplePicture() throws Exception {
        System.out.println("Checking ScanLine:");
        for (int j = 0; simpleDefaultPicture.h > j ; j ++) {
            int[] scanLine = potrace_bitmap.bm_scanline(simpleDefaultPicture,j);
            System.out.println("ScanLine " + j + " :");
            for (int i = 0; scanLine.length > i; i ++) {
                int should = simpleDefaultPicture.map[j];
                int actual = potrace_bitmap.bm_scanline(simpleDefaultPicture,j)[i];
                System.out.println("should: " + Integer.toHexString(should) + " - actual: " + Integer.toHexString(actual));
                assertEquals(Integer.toHexString(should),Integer.toHexString(actual));
            }
        }
    }

    @org.junit.Test
    public void checkWetherYouGetTheCorrectScanLineOfDificultPicture() throws Exception {
        System.out.println("Checking ScanLine:");
        for (int j = 0; dificultDefaultPicture.h > j ; j ++) {
            int[] scanLine = potrace_bitmap.bm_scanline(dificultDefaultPicture,j);
            System.out.println("ScanLine " + j + " :");
            for (int i = 0; scanLine.length > i; i ++) {
                int should = dificultDefaultPicture.map[j * dificultDefaultPicture.dy + i];
                int actual = potrace_bitmap.bm_scanline(dificultDefaultPicture,j)[i];
                System.out.println("should: " + Integer.toHexString(should) + " - actual: " + Integer.toHexString(actual));
                assertEquals(Integer.toHexString(should),Integer.toHexString(actual));
            }
        }
    }

    @org.junit.Test
    public void checkWetherMethodbm_indexWorksCorrectSmallPicture() throws Exception {
        System.out.println("Checking bm_index() with small Picture");
        for (int y = 0; y < simpleDefaultPicture.h; y ++) {
            for (int x = 0; x < simpleDefaultPicture.w; x ++) {
                int should = simpleDefaultPicture.map[y * simpleDefaultPicture.dy + (x/ potrace_bitmap.PIXELINWORD)];
                int actual = potrace_bitmap.bm_index(simpleDefaultPicture,x,y);
                assertEquals(should,actual);
            }
        }
    }

    @org.junit.Test
    public void checkWetherMethodbm_indexWorksCorrectDifficultPicture() throws Exception {
        System.out.println("Checking bm_index() with difficult Picture");
        for (int y = 0; y < dificultDefaultPicture.h; y ++) {
            for (int x = 0; x < dificultDefaultPicture.w; x ++) {
                int should = dificultDefaultPicture.map[y * dificultDefaultPicture.dy + (x/ potrace_bitmap.PIXELINWORD)];
                int actual = potrace_bitmap.bm_index(dificultDefaultPicture,x,y);
                assertEquals(should,actual);
            }
        }
    }

    @org.junit.Test
    public void checkBitMapMaskFunction() throws Exception {
        int should = 0x40000000;
        int actual = potrace_bitmap.bm_mask(1);
        System.out.println(Integer.toBinaryString(should));
        System.out.println(Integer.toBinaryString(actual));
        assertEquals(should, actual);
    }

    @org.junit.Test
    public void checkBitMapMaskFunctionExtended() throws Exception {
        int should = 0x40000000;
        int actual = potrace_bitmap.bm_mask(33);
        System.out.println(Integer.toBinaryString(should));
        System.out.println(Integer.toBinaryString(actual));
        assertEquals(should, actual);
    }

    @org.junit.Test
    public void checkWhatHappensWhenPictureIsWiderThen32Bit() throws Exception {
        potrace_bitmap testBitMap = new potrace_bitmap(40,2);
        testBitMap.map[3] = 0x90000000;
        testBitMap.map[2] = 0x5;
        testBitMap.map[1] = 0x80000000;
        testBitMap.map[0] = 0x5;
        Assert.assertEquals(true, potrace_bitmap.BM_GET(testBitMap,32,0));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,33,0));
        Assert.assertEquals(true, potrace_bitmap.BM_GET(testBitMap,31,0));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,30,0));
        Assert.assertEquals(true, potrace_bitmap.BM_GET(testBitMap,32,1));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,33,1));
        Assert.assertEquals(true, potrace_bitmap.BM_GET(testBitMap,31,1));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,30,1));

        Assert.assertEquals(true, potrace_bitmap.bm_safe(testBitMap,39,0));
        Assert.assertEquals(false, potrace_bitmap.bm_safe(testBitMap,40,0));
    }

    @org.junit.Test
    public void test_bm_range() throws Exception {
        Assert.assertEquals(true, potrace_bitmap.bm_range(1,2));
        Assert.assertEquals(false, potrace_bitmap.bm_range(3,2));
        Assert.assertEquals(true, potrace_bitmap.bm_range(1.0,2.0));
        Assert.assertEquals(false, potrace_bitmap.bm_range(3.0,2.0));
    }

    @org.junit.Test
    public void test_bm_safe() throws Exception {
        potrace_bitmap testBitMap = new potrace_bitmap(2,2);
        Assert.assertEquals(true, potrace_bitmap.bm_safe(testBitMap,1,1));
        Assert.assertEquals(false, potrace_bitmap.bm_safe(testBitMap,2,1));
        Assert.assertEquals(false, potrace_bitmap.bm_safe(testBitMap,1,2));
        Assert.assertEquals(false, potrace_bitmap.bm_safe(testBitMap,3,3));
    }

    @org.junit.Test
    public void test_bm_uget() throws Exception {
        potrace_bitmap testBitMap = new potrace_bitmap(2,2);
        testBitMap.map[0] = 0x80000000;
        testBitMap.map[1] = 0x0;
        Assert.assertEquals(false, potrace_bitmap.BM_UGET(testBitMap,1,0));
        Assert.assertEquals(true, potrace_bitmap.BM_UGET(testBitMap,0,0));
        Assert.assertEquals(false, potrace_bitmap.BM_UGET(testBitMap,0,1));
        Assert.assertEquals(false, potrace_bitmap.BM_UGET(testBitMap,1,1));
    }

    @org.junit.Test
    public void test_bm_get() throws Exception {
        potrace_bitmap testBitMap = new potrace_bitmap(2,2);
        testBitMap.map[1] = 0x40000000;
        testBitMap.map[0] = 0x0;
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,1,0));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,0,0));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,0,1));
        Assert.assertEquals(true, potrace_bitmap.BM_GET(testBitMap,1,1));

        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,-1,0));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,0,-1));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,2,0));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,0,2));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,2,2));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,-1,-1));
    }

    @org.junit.Test
    //TODO wie kann denn eine negatives dy entstehen?
    public void test_bm_size() throws Exception {
        potrace_bitmap testBitMap = new potrace_bitmap(-33,1);
        System.out.println(potrace_bitmap.bm_size(testBitMap));
        Assert.assertEquals(32, potrace_bitmap.bm_size(new potrace_bitmap(1,1)));
        Assert.assertEquals(64, potrace_bitmap.bm_size(new potrace_bitmap(33,1)));
        Assert.assertEquals(64, potrace_bitmap.bm_size(new potrace_bitmap(4,2)));
    }

    @org.junit.Test
    public void test_bm_clearexcess() throws Exception {
        potrace_bitmap testBitMap = new potrace_bitmap(40,2);
        testBitMap.map[3] = 0xf8ffffff;
        testBitMap.map[2] = 0xffffffff;
        testBitMap.map[1] = 0xffffffff;
        testBitMap.map[0] = 0xffffffff;
        decompose.bm_clearexcess(testBitMap);
        Assert.assertEquals(-1,testBitMap.map[0]);
        Assert.assertEquals(0xff000000,testBitMap.map[1]);
        Assert.assertEquals(-1,testBitMap.map[2]);
        Assert.assertEquals(0xf8000000,testBitMap.map[3]);
    }

    @org.junit.Test
    public void test_bm_clear() throws Exception {
        potrace_bitmap testBitMap = new potrace_bitmap(40,2);
        testBitMap.map[3] = 0xf0000000;
        testBitMap.map[2] = 0xffffffff;
        testBitMap.map[1] = 0xf0000000;
        testBitMap.map[0] = 0xffffffff;
        potrace_bitmap.bm_clear(testBitMap,0);
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,0,0));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,40,2));
        potrace_bitmap.bm_clear(testBitMap,1);
        Assert.assertEquals(true, potrace_bitmap.BM_GET(testBitMap,0,0));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,40,2));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,40,1));
    }

    @org.junit.Test
    public void test_bm_setPotraceWort_WithI() throws Exception {
        potrace_bitmap testBitMap = new potrace_bitmap(40,2);
        testBitMap.map[3] = 0xf8ffffff;
        testBitMap.map[2] = 0xffffffff;
        testBitMap.map[1] = 0xffffffff;
        testBitMap.map[0] = 0xffffffff;
        potrace_bitmap.bm_clear(testBitMap,0);
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,0,0));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,40,2));
        potrace_bitmap.bm_clear(testBitMap,1);
        Assert.assertEquals(true, potrace_bitmap.BM_GET(testBitMap,0,0));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,40,2));
        Assert.assertEquals(false, potrace_bitmap.BM_GET(testBitMap,40,1));
    }

    @org.junit.Test
    public void test_bm_dup() throws Exception {
        potrace_bitmap originBitMap = new potrace_bitmap(40,2);
        originBitMap.map[3] = 0xf8000000;
        originBitMap.map[2] = 0xffffffff;
        originBitMap.map[1] = 0xff000000;
        originBitMap.map[0] = 0xffffffff;

        potrace_bitmap copiedBitmap = potrace_bitmap.bm_dup(originBitMap);

        //check wether reference is difference
        Assert.assertFalse(originBitMap == copiedBitmap);
        Assert.assertArrayEquals(originBitMap.map,copiedBitmap.map);
        Assert.assertEquals(originBitMap.dy, copiedBitmap.dy);
        Assert.assertEquals(originBitMap.h, copiedBitmap.h);
        Assert.assertEquals(originBitMap.w, copiedBitmap.w);
    }

}


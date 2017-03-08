import org.junit.Before;
import org.junit.Test;

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
        assertEquals(1,simpleDefaultPicture.dy);
    }

    @org.junit.Test
    public void checkDYOfDificultPicture() throws Exception {
        System.out.println("Checking Initialisation of DY:");
        System.out.println("- should be: 2");
        System.out.println("- was: " + dificultDefaultPicture.dy);
        assertEquals(2,dificultDefaultPicture.dy);
    }

    @org.junit.Test
    public void checkLengthOfMap() throws Exception {
        System.out.println("Checking Length of Map:");
        System.out.println("- should be: 4");
        System.out.println("- was: " + simpleDefaultPicture.map.length);
        assertEquals(4,simpleDefaultPicture.map.length);
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
                int should = simpleDefaultPicture.map[y * simpleDefaultPicture.dy + (x/potrace_bitmap.PIXELINWORD)];
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
                int should = dificultDefaultPicture.map[y * dificultDefaultPicture.dy + (x/potrace_bitmap.PIXELINWORD)];
                int actual = potrace_bitmap.bm_index(dificultDefaultPicture,x,y);
                assertEquals(should,actual);
            }
        }
    }

    @org.junit.Test
    public void checkWetherMethodbm_setPotraceWordWorksCorrectSimplePicture() throws Exception {
        System.out.println("Checking bm_setPotraceWord() with simple Picture");
        int should = 0x20000000;
        potrace_bitmap bm_new = potrace_bitmap.bm_setPotraceWord(simpleDefaultPicture, 0, 0, should);
        int actual = potrace_bitmap.bm_index(bm_new, 0, 0);
        assertEquals(should, actual);
    }

    @org.junit.Test
    public void checkBitMapMaskFunction() throws Exception {
        int should = 0x40000000;
        int actual = potrace_bitmap.bm_mask(1);
        System.out.println(Integer.toBinaryString(should));
        System.out.println(Integer.toBinaryString(actual));
        assertEquals(should, actual);
    }


}

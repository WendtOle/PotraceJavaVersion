package refactored;

import General.Bitmap;

/**
 * Created by andreydelany on 03.07.17.
 */
public class BitMask {

    static long getMultiplePixelMaskUntilPosition(int position){
        return getMask(Bitmap.BM_ALLBITS, position );
    }

    static long getOnePixelMaskForPosition(int position) {
        return getMask(1l, position + 1 );
    }

    private static long getMask(long pattern,int position) {
        return (pattern) << (Bitmap.PIXELINWORD  - (position));
    }
}
package Potrace.refactored;

import Potrace.General.Bitmap;

public class BitMask {

    static long getMultiplePixelMaskFromStartUntilPosition(int position){
        return getMask(Bitmap.BM_ALLBITS, position);
    }

    static long getOnePixelMaskForPosition(int position) {
        return getMask(0x1L, position + 1 );
    }

    private static long getMask(long pattern, int position) {
        return (pattern) << (Bitmap.PIXELINWORD  - (position));
    }
}
package Potrace.General;

/**
 * Created by andreydelany on 07.07.17.
 */
public interface BitmapInterface {

    int PIXELINWORD = 64;
    long BM_ALLBITS = (~0);
    long BM_HIBIT = 1 << PIXELINWORD -1;

    int getWidth();
    int getHeight();
    long[] getWords();
}

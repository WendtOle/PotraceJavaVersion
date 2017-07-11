package Potrace.General;

/**
 * Created by andreydelany on 07.07.17.
 */
public interface DecompositionInterface {
    Path getPathList(Bitmap generalBitmap, Param param);

    Bitmap getWorkCopy();
}

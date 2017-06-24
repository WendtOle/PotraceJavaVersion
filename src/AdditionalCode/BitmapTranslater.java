package AdditionalCode;

/**
 * Created by andreydelany on 24.06.17.
 */
public class BitmapTranslater {

    public static potraceOriginal.Bitmap translateBitmapForOriginalCode(Bitmap bitmap){
        potraceOriginal.Bitmap translatedBitmap = new potraceOriginal.Bitmap(bitmap.width,bitmap.height);
        translatedBitmap.map = bitmap.potraceWords;
        return translatedBitmap;
    }
}

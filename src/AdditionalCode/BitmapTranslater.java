package AdditionalCode;

/**
 * Created by andreydelany on 24.06.17.
 */
public class BitmapTranslater {

    public static potraceOriginal.Bitmap translatBitmapForOriginalCode(Bitmap bitmap){
        potraceOriginal.Bitmap translatedBitmap = new potraceOriginal.Bitmap(bitmap.width,bitmap.height);
        translatedBitmap.map = bitmap.potraceWords;
        return translatedBitmap;
    }
}

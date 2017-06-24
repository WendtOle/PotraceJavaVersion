package AdditionalCode;

/**
 * Created by andreydelany on 24.06.17.
 */
public class BitmapTranslater {

    public static original.potrace.Bitmap translateBitmapForOriginalCode(Bitmap bitmap){
        original.potrace.Bitmap translatedBitmap = new original.potrace.Bitmap(bitmap.width,bitmap.height);
        translatedBitmap.map = bitmap.potraceWords;
        return translatedBitmap;
    }
}

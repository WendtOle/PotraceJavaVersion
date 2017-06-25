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

    public static refactored.potrace.Bitmap translateBitmapForRefactoredCode(Bitmap bitmap){
        refactored.potrace.Bitmap translatedBitmap = new refactored.potrace.Bitmap(bitmap.width,bitmap.height);
        translatedBitmap.words = bitmap.potraceWords;
        return translatedBitmap;
    }
}

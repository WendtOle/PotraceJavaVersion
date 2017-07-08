package AdditionalCode;

/**
 * Created by andreydelany on 24.06.17.
 */
public class BitmapTranslater {

    public static original.Bitmap translateBitmapForOriginalCode(Bitmap bitmap){
        original.Bitmap translatedBitmap = new original.Bitmap(bitmap.width,bitmap.height);
        translatedBitmap.map = bitmap.potraceWords;
        return translatedBitmap;
    }

    public static refactored.Bitmap translateBitmapForRefactoredCode(Bitmap bitmap){
        refactored.Bitmap translatedBitmap = new refactored.Bitmap(bitmap.width,bitmap.height);
        translatedBitmap.words = bitmap.potraceWords;
        return translatedBitmap;
    }
}

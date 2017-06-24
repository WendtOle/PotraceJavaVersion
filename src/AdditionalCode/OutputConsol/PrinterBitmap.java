package AdditionalCode.OutputConsol;

import AdditionalCode.Bitmap;

/**
 * Created by andreydelany on 21/03/2017.
 */

public class PrinterBitmap {

    Bitmap bitmap;

    public PrinterBitmap(Bitmap bm) {
        this.bitmap = bm;
    }

    public void print(){
        printBoundary();
        printLinesOfBitmap();
    }

    private void printBoundary() {
        System.out.println();
    }

    private void printLinesOfBitmap() {
        for(int y = bitmap.height-1; y >=0; y --) {
            printPotraceWordsInCurrentLine(y);
            printBoundary();
        }
    }

    private void printPotraceWordsInCurrentLine(int currentLine) {
        for(int dy = 0; dy < bitmap.potraceWordsInOneLine; dy ++) {
            String currentPotraceWord = getCurrentPotraceWord(currentLine,dy);
            fillUpPotraceWordWithZeros(currentPotraceWord);
            printPotraceWord(currentPotraceWord);
        }
    }

    private String getCurrentPotraceWord(int currentLine, int currentPotraceWord) {
        return Long.toBinaryString(bitmap.potraceWords[currentLine*bitmap.potraceWordsInOneLine + currentPotraceWord]);
    }

    private void fillUpPotraceWordWithZeros(String currentPotraceWord) {
        int amountOfZeros = Bitmap.PIXELINWORD - currentPotraceWord.length();
        printCertainAmountOfZeros(amountOfZeros);
    }

    private void printCertainAmountOfZeros(int amountOfZeros) {
        for (int i = 0; i < amountOfZeros; i++)
            System.out.print("0");
    }

    private void printPotraceWord(String currentPotraceWord){
        System.out.print(currentPotraceWord);
    }

    public void printBitmapArchitectureForCUnderstandable() {
        System.out.println("bm = bm_newCopied("+bitmap.width+","+bitmap.height+");");

        for(int i = 0; i < bitmap.potraceWords.length; i++) {
            System.out.println("bm->potraceWords["+i+"] = 0x"+ Long.toHexString(bitmap.potraceWords[i]) + "l;");
        }
    }

    public void printBitmapArchitectureForJavaUnderstandable() {
        System.out.println("Bitmap bitmap = new Bitmap("+bitmap.width+","+bitmap.height+");");

        for(int i = 0; i < bitmap.potraceWords.length; i++) {
            System.out.println("bitmap.potraceWords["+i+"] = 0x"+ Long.toHexString(bitmap.potraceWords[i]) + "l;");
        }
    }

}

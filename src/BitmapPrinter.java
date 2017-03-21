/**
 * Created by andreydelany on 21/03/2017.
 */
public class BitmapPrinter {

    potrace_bitmap bitmap;

    public BitmapPrinter(potrace_bitmap bm) {
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
        for(int y = bitmap.h-1; y >=0; y --) {
            printPotraceWordsInCurrentLine(y);
            printBoundary();
        }
    }

    private void printPotraceWordsInCurrentLine(int currentLine) {
        for(int dy = 0; dy < bitmap.dy; dy ++) {
            String currentPotraceWord = getCurrentPotraceWord(currentLine,dy);
            fillUpPotraceWordWithZeros(currentPotraceWord);
            printPotraceWord(currentPotraceWord);
        }
    }

    private String getCurrentPotraceWord(int currentLine, int currentPotraceWord) {
        return Integer.toBinaryString(bitmap.map[currentLine*bitmap.dy + currentPotraceWord]);
    }

    private void fillUpPotraceWordWithZeros(String currentPotraceWord) {
        int amountOfZeros = potrace_bitmap.PIXELINWORD - currentPotraceWord.length();
        printCertainAmountOfZeros(amountOfZeros);
    }

    private void printCertainAmountOfZeros(int amountOfZeros) {
        for (int i = 0; i < amountOfZeros; i++)
            System.out.print("0");
    }

    private void printPotraceWord(String currentPotraceWord){
        System.out.print(currentPotraceWord);
    }

}

package Potrace.refactored;

import Potrace.General.Bitmap;

import java.awt.*;

public class ClearPathWithBoundingBox {
    private BitmapHandlerInterface bitmapHandler;
    private int indexOfWordWhereBoundingBoxStarts;
    private int indexOfWordWhereBoundingBoxEnds;
    private int indexOfCurrentWord;
    private int currentLine;
    private PathBoundingBox boundingBox;

    public ClearPathWithBoundingBox(Bitmap bitmap) {
        this.bitmapHandler = new BitmapHandler(bitmap);
    }

    public void clear(PathBoundingBox boundingBox) {
        this.boundingBox = boundingBox;
        setHorizontalRange();
        clearBitmapInHorizontalRange();
    }

    private void setHorizontalRange() {
        indexOfWordWhereBoundingBoxStarts = (boundingBox.x0 / Bitmap.PIXELINWORD);
        indexOfWordWhereBoundingBoxEnds = ((boundingBox.x1 + Bitmap.PIXELINWORD-1) / Bitmap.PIXELINWORD);
    }

    private void clearBitmapInHorizontalRange() {
        currentLine = boundingBox.y0;
        while(isStillVerticallyInBoundingBox())
            clearLineAndGoToNextLine();
    }

    private boolean isStillVerticallyInBoundingBox() {
        return currentLine < boundingBox.y1;
    }

    private void clearLineAndGoToNextLine() {
        clearAllWordsInLineThatOverlapWithBoundingBox();
        currentLine ++;
    }

    private void clearAllWordsInLineThatOverlapWithBoundingBox() {
        indexOfCurrentWord = indexOfWordWhereBoundingBoxStarts;
        while (isStillHorizontallyInBoundingBox())
            clearWordAnGoToNextWord();
    }

    private boolean isStillHorizontallyInBoundingBox() {
        return indexOfCurrentWord < indexOfWordWhereBoundingBoxEnds;
    }

    private void clearWordAnGoToNextWord() {
        clearWord(currentLine, indexOfCurrentWord);
        indexOfCurrentWord ++;
    }

    private void clearWord(int line, int indexOfWord) {
        Point potraceWordIdentificationPixel = new Point(indexOfWord * Bitmap.PIXELINWORD, line);
        bitmapHandler.clearPotraceWord(potraceWordIdentificationPixel);
    }
}
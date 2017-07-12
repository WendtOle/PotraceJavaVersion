package Potrace.refactored;

import Potrace.General.Bitmap;

import java.awt.*;

/**
 * Created by andreydelany on 02.07.17.
 */
public class ClearPathWithBoundingBox {
    BitmapHandlerInterface bitmapHandler;
    int indexOfWordWhereBoundingBoxStarts, indexOfwordWhereBoundingBoxEnds;

    public ClearPathWithBoundingBox(Bitmap bitmap) {
        this.bitmapHandler = new BitmapHandler(bitmap);
    }

    public void clearBitmapWithBoundingBox(BoundingBox boundingBox) {
        setHorizontalRange(boundingBox);
        clearBitmapInHorizontalRange(boundingBox);
    }

    private void setHorizontalRange(BoundingBox boundingBox) {
        indexOfWordWhereBoundingBoxStarts = (boundingBox.x0 / Bitmap.PIXELINWORD);
        indexOfwordWhereBoundingBoxEnds = ((boundingBox.x1 + Bitmap.PIXELINWORD-1) / Bitmap.PIXELINWORD);
    }

    private void clearBitmapInHorizontalRange(BoundingBox boundingBox) {
        int currentLine = boundingBox.y0;
        while(currentLine < boundingBox.y1) {
            clearAllWordsInLineThatOverlapWithBoundingBox(currentLine); //TODO auslagern
            currentLine ++;
        }
    }

    private void clearAllWordsInLineThatOverlapWithBoundingBox(int line) {
        for (int indexOfCurrentWord = indexOfWordWhereBoundingBoxStarts; indexOfCurrentWord < indexOfwordWhereBoundingBoxEnds; indexOfCurrentWord++) { //ToDO viel zu mächtiger kopf
            clearWord(line, indexOfCurrentWord);
        }
    }

    private void clearWord(int line, int indexOfWord) {
        Point positionOfWord = new Point(indexOfWord * Bitmap.PIXELINWORD, line);
        bitmapHandler.setWordToNull(positionOfWord);
    }
}
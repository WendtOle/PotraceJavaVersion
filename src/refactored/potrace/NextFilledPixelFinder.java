package refactored.potrace;

import java.awt.*;

/**
 * Created by andreydelany on 02.07.17.
 */
public class NextFilledPixelFinder extends BitmapPixelHandler {

        Point currentPixel;

        public NextFilledPixelFinder(Bitmap bitmap){
            super(bitmap);
            currentPixel = new Point(0,bitmap.height-1);
        }

        public boolean isThereAFilledPixel() {
            currentPixel = findNextFilledPixel();
            return currentPixel != null;
        }

        public Point getPositionOfNextFilledPixel() {
            return currentPixel;
        }

        private Point findNextFilledPixel() {
            int x0 = getBeginningIndexOfWord(currentPixel.x);

            for (int y=currentPixel.y; y>=0; y--) {
                for (int x = x0; x<bitmap.width && x>=0; x+=Bitmap.PIXELINWORD) {

                    if (bitmapHandler.areThereFilledPixelInWord(new Point(x,y))) {
                        while (!getPixelValue(new Point(x, y))) {
                            x++;
                        }
                        return new Point(x,y);
                    }
                }
                x0 = 0;
            }
            return null;
        }
}
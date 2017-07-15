package Bencharking;

import AdditionalCode.FileInputOutput.BitmapImporter;
import Potrace.General.Bitmap;
import Potrace.original.Decompose;
import Potrace.refactored.NextFilledPixelFinder;
import Potrace.refactored.PathFindingCharacteristics;
import Potrace.refactored.PathKindEnum;
import Potrace.refactored.TurnPolicyEnum;
import org.openjdk.jmh.annotations.*;

import java.awt.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class FindPath {

    @State(Scope.Thread)
    public static class TestData {

        Bitmap[] bitmaps;

        TurnPolicyEnum turnPolicy = TurnPolicyEnum.MINORITY;
        int turnPolicyAsInt = turnPolicy.ordinal();
        PathKindEnum kindOfPath = PathKindEnum.POSITIV;
        int sign = kindOfPath.getIntRepresentation();

        Point[] firstFilledPixelsAsPoint;
        int[][] firstFilledPixelsAsInt;

        @Setup
        public void setUp(){
            BitmapImporter importer = new BitmapImporter("benchmarkingPictures");
            bitmaps = importer.getAllBitmaps();

            setFirstFilledPixel();
        }

        public void setFirstFilledPixel(){
            firstFilledPixelsAsPoint = new Point[bitmaps.length];
            firstFilledPixelsAsInt = new int[bitmaps.length][2];
            for (int bitmapIndex = 0; bitmapIndex < bitmaps.length; bitmapIndex++) {
                NextFilledPixelFinder filledPixelFinder = new NextFilledPixelFinder(bitmaps[bitmapIndex]);
                Point firstFilledPixel = filledPixelFinder.getPositionOfNextFilledPixel();
                firstFilledPixelsAsPoint[bitmapIndex] = firstFilledPixel;
                firstFilledPixelsAsInt[bitmapIndex][0] = firstFilledPixel.x;
                firstFilledPixelsAsInt[bitmapIndex][1] = firstFilledPixel.y + 1;
            }
        }
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(2)
    @Threads(1)
    public void mesureRefactored(TestData data) throws InterruptedException {
        for (int bitmapIndex = 0; bitmapIndex < data.bitmaps.length; bitmapIndex++) {
            Bitmap bitmap = data.bitmaps[bitmapIndex];
            Point point = data.firstFilledPixelsAsPoint[bitmapIndex];
            Potrace.refactored.FindPath findPath = new Potrace.refactored.FindPath(bitmap, point, new PathFindingCharacteristics(data.turnPolicy, data.kindOfPath));
            findPath.getPath();
        }
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(2)
    @Threads(1)
    public void mesureOriginal(TestData data) throws InterruptedException {
        for (int bitmapIndex = 0; bitmapIndex < data.bitmaps.length; bitmapIndex++) {
            Bitmap bitmap = data.bitmaps[bitmapIndex];
            int x0 = data.firstFilledPixelsAsInt[bitmapIndex][0];
            int y0 = data.firstFilledPixelsAsInt[bitmapIndex][1];
            Decompose.findpath(bitmap,x0,y0,data.sign,data.turnPolicyAsInt);
        }
    }
}

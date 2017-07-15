package BenchMarking;

import AdditionalCode.FileInputOutput.BitmapImporter;
import Potrace.General.Bitmap;
import Potrace.original.Decompose;
import Potrace.refactored.NextFilledPixelFinder;
import org.openjdk.jmh.annotations.*;

import java.awt.*;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class NextFilledPixelFinderBenchmark {
    @State(Scope.Thread)
    public static class TestData {

        Bitmap[] bitmaps;

        @Setup
        public void setUp(){
            BitmapImporter importer = new BitmapImporter("benchmarkingPictures");
            bitmaps = importer.getAllBitmaps();
        }
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MICROSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(2)
    @Threads(1)
    public void mesureRefactored(TestData data) throws InterruptedException {
        for (int bitmapIndex = 0; bitmapIndex < data.bitmaps.length; bitmapIndex++) {
            NextFilledPixelFinder nextFilledPixelFinder = new NextFilledPixelFinder(data.bitmaps[bitmapIndex]);
            nextFilledPixelFinder.getPositionOfNextFilledPixel();
        }
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(MICROSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(2)
    @Threads(1)
    public void mesureOriginal(TestData data) throws InterruptedException {
        for (int bitmapIndex = 0; bitmapIndex < data.bitmaps.length; bitmapIndex++) {
            Bitmap bitmap = data.bitmaps[bitmapIndex];
            Decompose.findnext(bitmap,new Point(0,bitmap.h-1));
        }
    }
}
